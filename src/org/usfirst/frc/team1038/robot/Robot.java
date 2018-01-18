/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1038.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
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
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();
	DriveStraightCommand driveStraight = new DriveStraightCommand();
	TurnCommand turnDegrees = new TurnCommand();
	public static DriveTrain robotDrive = DriveTrain.getInstance();
	public enum driveModes {tankDrive, singleArcadeDrive, dualArcadeDrive};
	private driveModes currentDriveMode = driveModes.dualArcadeDrive;
	Joystick1038 driverJoystick = new Joystick1038(0);
	Joystick1038 operatorJoystick = new Joystick1038(1);
	private int stepNum = 1;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
		I2CGyro.getInstance();
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
//		driveStraight.initialize();
		stepNum = 1;
		m_autoSelected = m_chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + m_autoSelected);
		robotDrive.resetEncoders();
		driveStraight.initialize();
	}
	
	@Override
	public void teleopInit() {
		robotDrive.resetEncoders();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		switch (m_autoSelected) {
			case kCustomAuto:
				// Put custom auto code here
				break;
			case kDefaultAuto:
			default:
				// Put default auto code here
				switch(stepNum) {
					case 1:
						SmartDashboard.putNumber("Autonomous Drive Distance", driveStraight.getDriveDistance());
						if(!driveStraight.isFinished()) {
							driveStraight.execute();
						} else {
							driveStraight.end();
							stepNum = 2;
							turnDegrees.initialize();
						}
						break;
					case 2:
						//System.out.println(stepNum);
						if(!turnDegrees.isFinished()) {
							//turnDegrees.turn(90);
							turnDegrees.execute();
						}else{
							turnDegrees.end();
							stepNum = 3;
						}
						break;
				}
				break; 
			}
		
		//System.out.println("Step " + stepNum);
		System.out.println(I2CGyro.getInstance().readGyro());
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		driver();
		//operator();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
	
	public void driver() {
	
		double driveDivider;
		System.out.println(I2CGyro.getInstance().readGyro());
		
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
		
		else
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
	}
	
	public void operator() {
		
	}
}
