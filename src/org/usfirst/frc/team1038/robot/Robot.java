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
	//Subsystems
		//Climb
	private Climb robotClimb = new Climb();
	private boolean autoClimbing = false;
	private boolean lowering = false;
	
		//Pneumatics
	private Compressor c = new Compressor();
	
		//Drive
	public static DriveTrain robotDrive = DriveTrain.getInstance();
	public enum driveModes {tankDrive, singleArcadeDrive, dualArcadeDrive};
	private driveModes currentDriveMode = driveModes.dualArcadeDrive;
	
		//Acquisition Scoring
	private AcquisitionScoring acqSco = new AcquisitionScoring();
	
		//Elevator
	private Elevator elevator = new Elevator();
	
	//Teleop
	Joystick1038 driverJoystick = new Joystick1038(0);
	Joystick1038 operatorJoystick = new Joystick1038(1);
	LaserRangeFinder rangeF;
	
	//Auton
	Scheduler schedule;
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();
	//private PathfinderTest pathTest;
	//private Vision vision = new Vision();
	TurnCommandVision visionCommand = new TurnCommandVision();
	TurnCommandVisionTest visionCommandTest = null;
	TurnCommand testCommand = null;
	private boolean XButtonLastPressed;
	private boolean AButtonLastPressed;
	private boolean BButtonLastPressed;
	private int desiredAngle;
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
		Dashboard.update();
		schedule.run();
		//pathTest.excecute();
		//System.out.println(I2CGyro.getInstance().getAngle());
		//System.out.println(vision.getAngle());
		//visionCommand.execute();
	}
	
	@Override
	public void teleopInit() {
		robotDrive.resetEncoders();
		rangeF = new LaserRangeFinder();
		//visionCommandTest = null;
		desiredAngle = (int) gyroSensor.getAngle();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
//		System.out.println(PressureSensor.getInstance().getPressure());
		driver();
		operator();
		Dashboard.update();
		//System.out.println(vision.getAngle());
	}

	public void driver() {
	
		double driveDivider;
		//System.out.println(I2CGyro.getInstance().getAngle());
		//I2CGyro.getInstance().getAngle();
		
		if(driverJoystick.getBackButton())
			I2CGyro.getInstance().recalibrateGyro();
		
		if(!driverJoystick.getRightButton() && !robotDrive.isHighGear()) {
			driveDivider = .65;
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
	
		if(driverJoystick.getRightTrigger())
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
		}
		
		if(driverJoystick.getLeftTrigger())
		{
			robotDrive.PTOoff();
		}
		
		if((driverJoystick.getAButton() == true) && (AButtonLastPressed == false)) {
			System.out.println("A button just pressed");
			desiredAngle += 45;
			if(desiredAngle >= 360) {
				desiredAngle = 0;
			}
			System.out.println("New desired angle is: " + desiredAngle);
		}
		AButtonLastPressed = driverJoystick.getAButton();
		if((driverJoystick.getBButton() == true) && (BButtonLastPressed == false)) {
			System.out.println("B button just pressed");
			desiredAngle -= 45;
			if(desiredAngle < 0) {
				desiredAngle = 315;
			}
			System.out.println("New desired angle is: " + desiredAngle);
		}
		BButtonLastPressed = driverJoystick.getBButton();
		if(driverJoystick.getXButton()) 
		{
//			vision.turnToAngle();
			if(testCommand == null) {
				testCommand = new TurnCommand(desiredAngle);
				testCommand.initialize();
				XButtonLastPressed = true;
			}
			testCommand.execute();
			if(testCommand.isFinished()) {
				testCommand.end();
				testCommand = null;
				XButtonLastPressed = false;
			}
			
		}else if(!driverJoystick.getXButton()) {
//			if(!(testCommand == null)) {
//				testCommand.end();
//			}
//			testCommand = null;
		}
		if((XButtonLastPressed = true) && !(testCommand == null)) {
			if(!(testCommand == null)) {
				if( testCommand.isFinished()) {
					testCommand.end();
					XButtonLastPressed = false;
				}else {
					testCommand.execute();
				}
			}
		}
	}
	
	public void operator() {
		if(operatorJoystick.getRightTrigger())
		{
			autoClimbing = true;
			robotClimb.autoArmRaise();
		}
		
		if(autoClimbing)
		{
			autoClimbing = robotClimb.autoArmRaise();
		}
		
		robotClimb.manualArmRaise(operatorJoystick.getLeftJoystickVertical());
		
		if(operatorJoystick.getRightButton())
		{
			lowering = true;
			robotClimb.armLower();
		}
		
		if(lowering)
		{
			lowering = robotClimb.armLower();
		}
		
		if (operatorJoystick.getRightButton())
		{
			System.out.println(rangeF.read());
		}
		
		if (operatorJoystick.getLeftButton())
		{
			rangeF.write();
		}
	}
	
	@Override
	public void disabledInit() {
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
		
	}
}
