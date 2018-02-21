/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1038.robot;

import edu.wpi.first.wpilibj.Compressor;

import org.usfirst.frc.team1038.auton.PathfinderTest;
import org.usfirst.frc.team1038.auton.TurnCommand;
import org.usfirst.frc.team1038.auton.TurnCommandVision;
import org.usfirst.frc.team1038.auton.TurnCommandVisionTest;
import org.usfirst.frc.team1038.auton.Vision;
import org.usfirst.frc.team1038.subsystem.AcquisitionScoring;
import org.usfirst.frc.team1038.subsystem.Climb;
import org.usfirst.frc.team1038.subsystem.DriveTrain;
import org.usfirst.frc.team1038.subsystem.Elevator;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
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
	private final int HIGH_PRESSURE_SENSOR_PORT = 0;
	private final int LOW_PRESSURE_SENSOR_PORT = 1;
	private PressureSensor lowPressureSensor = new PressureSensor(LOW_PRESSURE_SENSOR_PORT);
	private PressureSensor highPressureSensor = new PressureSensor(HIGH_PRESSURE_SENSOR_PORT);
	
	//Subsystems
		//Climb
	private Climb robotClimb = Climb.getInstance();
	
		//Pneumatics
	private Compressor c = new Compressor();
	
		//Drive
	public static DriveTrain robotDrive = DriveTrain.getInstance();
	public enum driveModes { tankDrive, singleArcadeDrive, dualArcadeDrive };
	private driveModes currentDriveMode = driveModes.dualArcadeDrive;
	
		//Acquisition Scoring
	private AcquisitionScoring acqSco = AcquisitionScoring.getInstance();
	private boolean povUpLastPressed = false;
	private boolean povDownLastPressed = false;
	
		//Elevator
	private Elevator elevator = Elevator.getInstance();
	
	//Teleop
	Joystick1038 driverJoystick = new Joystick1038(0);
	Joystick1038 operatorJoystick = new Joystick1038(1);
	
	//Auton
	Scheduler schedule;
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();
	private I2CGyro gyroSensor = I2CGyro.getInstance();
    
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		//c.stop();
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
		I2CGyro.getInstance();
		CameraServer.getInstance().addServer("raspberrypi.local:1180/?action=stream");
		//pathTest = new PathfinderTest();
		//pathTest.initialize();
	}
	
	
	@Override
	public void robotPeriodic() {
		Dashboard.update(lowPressureSensor.getPressure(), highPressureSensor.getPressure());
		elevator.elevatorPeriodic();
		acqSco.AcquisitionPeriodic();
		if (elevator.getLowProx())
			elevator.resetEncoder();
		if (robotClimb.getProx())
			robotClimb.resetEncoder();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		m_autoSelected = m_chooser.getSelected();
		schedule = Scheduler.getInstance();
		//TurnCommand turn = new TurnCommand(45);
		//turn.start();
		//PathfinderTest pathfinder = new PathfinderTest();
		//schedule.add(pathfinder);
		//schedule.add(visionCommand);
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		schedule.run();
		//pathTest.excecute();
		//System.out.println(I2CGyro.getInstance().getAngle());
		//System.out.println(vision.getAngle());
		//visionCommand.execute();
	}
	
	@Override
	public void teleopInit() {
		robotDrive.resetEncoders();
		//visionCommandTest = null;
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		driver();
		operator();
	}

	public void driver() {
	
		double driveDivider;
		
		if(!driverJoystick.getRightButton() && !robotDrive.isHighGear()) {
			driveDivider = .75;
		}
		else if (elevator.getEncoderCount() > 10)
		{
			driveDivider = .5;
		}
		else	 {
			driveDivider = 1;
		}
		
		switch (currentDriveMode) {
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
			switch (currentDriveMode) {
			case tankDrive:
				currentDriveMode = driveModes.dualArcadeDrive;
				break;
			case dualArcadeDrive:
				currentDriveMode = driveModes.singleArcadeDrive;
				break;
			case singleArcadeDrive:
				currentDriveMode = driveModes.tankDrive;
				break;
			}	
		}
	
		if(driverJoystick.getRightTrigger() && elevator.getLowProx())
		{
			robotDrive.highGear();
		}
		else if (robotDrive.isHighGear())
		{
			robotDrive.lowGear();
		}
		
		if(driverJoystick.getLeftButton())
		{
			robotDrive.PTOon();
		} else if(driverJoystick.getLeftTrigger())
		{
			robotDrive.PTOoff();
		}
		
		if (driverJoystick.getYButton())
		{
			elevator.moveToScaleHigh();
		} else if (driverJoystick.getXButton())
		{
			elevator.moveToSwitch();
		} else if (driverJoystick.getAButton())
		{
			elevator.moveToFloor();
		} else if (driverJoystick.getBButton())
		{
			elevator.moveToScaleLow();
		}
	}
	
	public void operator() {
		
		robotClimb.move(operatorJoystick.getLeftJoystickVertical());
		elevator.move(operatorJoystick.getRightJoystickVertical());
		
		if (operatorJoystick.getPOV() == 0 && !povUpLastPressed) {
			acqSco.setAcqSpeed(true);
			povUpLastPressed = true;
		} else if (operatorJoystick.getPOV() == 180 && !povDownLastPressed) {
			acqSco.setAcqSpeed(false);
			povDownLastPressed = true;
		} else if (operatorJoystick.getPOV() == -1) {
			povUpLastPressed = false;
			povDownLastPressed = false;
		}
		
		if (operatorJoystick.getLeftButton())
		{
			acqSco.openArms();
		}
		
		if (operatorJoystick.getLeftTrigger()) 
		{
			acqSco.closeArms();
		}
		
		if (operatorJoystick.getRightButton()) {
			acqSco.aquire();
		} else if (operatorJoystick.getRightTrigger()) {
			acqSco.dispose();
		} else {
			acqSco.stop();
		}
		
		if (operatorJoystick.getAButton())
		{
			acqSco.armsToZero();
		} else if (operatorJoystick.getBButton())
		{
			acqSco.armsTo45();
		} else if (operatorJoystick.getYButton())
		{
			acqSco.armsTo90();
		}
	}
	
	@Override
	public void disabledInit() {
		acqSco.disable();
		elevator.disable();
		System.out.println("Robot Disabled");
	}
	
	@Override
	public void disabledPeriodic() {
		
	}
	
	@Override
	public void testInit() {
		
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		System.out.println(operatorJoystick.getRightJoystickVertical());
		driver();
		operator();
	}
}
