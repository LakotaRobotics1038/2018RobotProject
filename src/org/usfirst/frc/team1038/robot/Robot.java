/*-----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST and Lakota Robotics 1038. All Rights Reserved.*/
/* Open Source Software - may be modified and shared by FRC teams. The code    */
/* must be accompanied by the FIRST BSD license file in the root directory of  */
/* the project.                                                                */
/*-----------------------------------------------------------------------------*/

package org.usfirst.frc.team1038.robot;

import edu.wpi.first.wpilibj.Compressor;
import org.usfirst.frc.team1038.auton.AutonSelector;
import org.usfirst.frc.team1038.auton.commands.TurnCommand;
import org.usfirst.frc.team1038.subsystem.AcquisitionScoring;
import org.usfirst.frc.team1038.subsystem.Climb;
import org.usfirst.frc.team1038.subsystem.Dashboard;
import org.usfirst.frc.team1038.subsystem.DriveTrain;
import org.usfirst.frc.team1038.subsystem.Elevator;
import org.usfirst.frc.team1038.subsystem.SwagLights;
import org.usfirst.frc.team1038.subsystem.AcquisitionScoring.SpeedModes;
import org.usfirst.frc.team1038.subsystem.SwagLights.WheelWellStates;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.hal.ControlWord;
import edu.wpi.first.wpilibj.hal.HAL;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	//Robot
	private Dashboard dashboard = Dashboard.getInstance();
	private final int HIGH_PRESSURE_SENSOR_PORT = 0;
	private final int LOW_PRESSURE_SENSOR_PORT = 1;
	private PressureSensor lowPressureSensor = new PressureSensor(LOW_PRESSURE_SENSOR_PORT);
	private PressureSensor highPressureSensor = new PressureSensor(HIGH_PRESSURE_SENSOR_PORT);
	private ControlWord controlWordCache = new ControlWord();
	
	//Subsystems
		//Climb
	private Climb robotClimb = Climb.getInstance();
	
		//Pneumatics
	private Compressor c = new Compressor();
	
		//Drive
	public static DriveTrain robotDrive = DriveTrain.getInstance();
	
		//Acquisition Scoring
	private AcquisitionScoring acqSco = AcquisitionScoring.getInstance();
	private boolean povUpLastPressed = false;
	private boolean povDownLastPressed = false;
	private final double ACQ_START_SPEED = .8;
	
		//Elevator
	public static Elevator elevator = Elevator.getInstance();
	private enum DriverLastPressed { none, xButton, aButton, bButton, yButton };
	private DriverLastPressed driverLastPressed = DriverLastPressed.none;
	
		//SwagLights
	private SwagLights swag = SwagLights.getInstance();
	public static boolean eStopped = false;
	public static boolean disabled = true;
	
	//Teleop
	private Joystick1038 driverJoystick = new Joystick1038(0);
	private Joystick1038 operatorJoystick = new Joystick1038(1);
	
	//Auton
	private Scheduler schedule = Scheduler.getInstance();
	public static SendableChooser<String> autoChooser = new SendableChooser<>();
	public static SendableChooser<String> startPosition = new SendableChooser<>();
	private I2CGyro gyroSensor = I2CGyro.getInstance();
	private AutonSelector autonSelector = AutonSelector.getInstance();
	private CommandGroup autonPath;
	public static Spark emptySpark = new Spark(9);
    
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		//c.stop();
		
		//Auton Choices to Dashboard
		autoChooser.addDefault("Forward Auton", AutonSelector.kForwardAuto);
		autoChooser.addObject("Single Scale Auto", AutonSelector.kSingleScaleAuto);
		autoChooser.addObject("Dual Scale Auto", AutonSelector.kDualScaleAuto);
		autoChooser.addObject("Single Switch Auto", AutonSelector.kSingleSwitchAuto);
		autoChooser.addObject("Dual Switch Auto", AutonSelector.kDualSwitchAuto);
		startPosition.addDefault("Center", AutonSelector.kCenterPosition);
		startPosition.addObject("Left", AutonSelector.kLeftPosition);
		startPosition.addObject("Right", AutonSelector.kRightPosition);
		SmartDashboard.putData("Drivers/Start Position", startPosition);
		SmartDashboard.putData("Drivers/Auton choices", autoChooser);
		
		//Camera to Dashboard
		NetworkTableInstance piCamTable = NetworkTableInstance.getDefault();
		String[] serverAddress = { "mjpeg:http://raspberrypi.local:1180/?action=stream" };
		piCamTable.getEntry("/CameraPublisher/PiCamera/streams").setStringArray(serverAddress);
	}
	
	/**
	 * Periodic code for all robot modes should go here.
	 */
	@Override
	public void robotPeriodic() {
		gyroSensor.readGyro();
		dashboard.update(lowPressureSensor.getPressure(), highPressureSensor.getPressure());
		swag.swagPeriodic();
		
		if (eStopped) {
			swag.setWheelWell(WheelWellStates.EStop);
		} else if (disabled) {
			swag.setWheelWell(WheelWellStates.Disabled);
		}

		elevator.elevatorPeriodic();
		acqSco.AcquisitionPeriodic();
		
		if (elevator.getLowProx()) {
			//elevator.resetEncoder();
			if (elevator.getSetpoint() == 0)
				elevator.disable();
		}
			
//		if (robotClimb.getProx())
//			robotClimb.resetEncoder();
	}

	/**
	 * Initialization code for autonomous mode should go here.
	 */
	@Override
	public void autonomousInit() {
		swag.enable();
		robotDrive.setBrakeMode();
		autonPath = autonSelector.chooseAuton();
		gyroSensor.reset();
		schedule.add(autonPath);
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		swag.swagEnabledPeriodic();
		if(schedule != null) {
			schedule.run();
		}
	}
	
	/**
	 * Initialization code for teleop mode should go here.
	 */
	@Override
	public void teleopInit() {
		schedule.removeAll();
		SmartDashboard.putBoolean("Drivers/Reset Arm Encoder", true);
		swag.enable();
		robotDrive.setCoastMode();
		gyroSensor.reset();
		robotDrive.resetEncoders();
		acqSco.setAcqSpeed(ACQ_START_SPEED);
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		swag.swagEnabledPeriodic();
		driver();
		operator();
	}

	/**
	 * Process driver controller input
	 */
	public void driver() {
	
		double driveDivider;
		
		if(!driverJoystick.getRightButton() && !robotDrive.isHighGear()) {
			driveDivider = .8;
		}
//		else if (elevator.getEncoderCount() > 20) {
//			driveDivider = .3;
//		}
		else	 {
			driveDivider = 1;
		}
		
		switch (robotDrive.currentDriveMode) {
		case tankDrive:
			robotDrive.tankDrive(driverJoystick.getLeftJoystickVertical() * driveDivider, driverJoystick.getRightJoystickVertical() * driveDivider);			
			break;
		case dualArcadeDrive:
			robotDrive.dualArcadeDrive(driverJoystick.getLeftJoystickVertical() * driveDivider, driverJoystick.getRightJoystickHorizontal() * driveDivider);
			break;
		case singleArcadeDrive:
			robotDrive.singleArcadeDrive(driverJoystick.getLeftJoystickVertical() * driveDivider, driverJoystick.getLeftJoystickHorizontal() * driveDivider);
			break;
		}	
	
		if(driverJoystick.getStartButton()) {
			robotDrive.toggleDriveMode();
		}
	
		if(driverJoystick.getRightTrigger() && elevator.getEncoderCount() < 20) {
			robotDrive.highGear();
		}
		else if (robotDrive.isHighGear()) {
			robotDrive.lowGear();
		}
		
		if(driverJoystick.getLeftButton()) {
			robotDrive.PTOon();
		} else if(driverJoystick.getLeftTrigger()) {
			robotDrive.PTOoff();
		}
		
		if (driverJoystick.getAButton() && driverLastPressed != DriverLastPressed.aButton) {
			elevator.moveToFloor();
			driverLastPressed = DriverLastPressed.aButton;
		} else if (driverJoystick.getXButton() && driverLastPressed != DriverLastPressed.xButton) {
			elevator.moveToSwitch();
			driverLastPressed = DriverLastPressed.xButton;
		} else if (driverJoystick.getYButton() && driverLastPressed != DriverLastPressed.yButton) {
			elevator.moveToScaleHigh();
			driverLastPressed = DriverLastPressed.yButton;
		} else if (driverJoystick.getBButton() && driverLastPressed != DriverLastPressed.bButton) {
			elevator.moveToScaleLow();
			driverLastPressed = DriverLastPressed.bButton;
		}
	}
	
	/**
	 * Process operator controller input
	 */
	public void operator() {
		
		robotClimb.move(operatorJoystick.getRightJoystickVertical());
		elevator.move(operatorJoystick.getLeftJoystickVertical());
		
		if (operatorJoystick.getPOV() == 0 && !povUpLastPressed) {
			acqSco.setAcqSpeed(SpeedModes.Up);
			povUpLastPressed = true;
		} else if (operatorJoystick.getPOV() == 180 && !povDownLastPressed) {
			acqSco.setAcqSpeed(SpeedModes.Down);
			povDownLastPressed = true;
		} else if (operatorJoystick.getPOV() == -1) {
			povUpLastPressed = false;
			povDownLastPressed = false;
		}
		
		if (operatorJoystick.getLeftButton()) {
			acqSco.openArms();
		} else if (operatorJoystick.getLeftTrigger())  {
			acqSco.closeArms();
		}
		
		if (operatorJoystick.getRightButton()) {
			acqSco.aquire();
		} else if (operatorJoystick.getRightTrigger()) {
			acqSco.dispose();
		} else {
			acqSco.stop();
		}
		
		if (operatorJoystick.getAButton()) {
			acqSco.armsToZero();
		} else if (operatorJoystick.getBButton()) {
			acqSco.armsTo60();
		} else if (operatorJoystick.getYButton()) {
			acqSco.armsTo90();
		}
	}
	
	/**
	 * Initialization code for disabled mode should go here.
	 */
	@Override
	public void disabledInit() {
		acqSco.disable();
		elevator.disable();
		robotDrive.PTOoff();
		System.out.println("Robot Disabled");
		HAL.getControlWord(controlWordCache);
		if(controlWordCache.getEStop()) {
			eStopped = true;
		} else {
			disabled = true;
		}
	}
	
	/**
	 * Periodic code for disabled mode should go here.
	 */
	@Override
	public void disabledPeriodic() {
		
	}
	
	/**
	 * Initialization code for test mode should go here.
	 */
	@Override
	public void testInit() {
		schedule.add(new TurnCommand(90));
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		schedule.run();
	}
}