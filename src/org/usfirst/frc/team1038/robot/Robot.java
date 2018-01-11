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
	
	Drive robotDrive = new Drive();
	public enum driveModes {tankDrive, singleArcadeDrive, dualArcadeDrive};
	private driveModes currentDriveMode = driveModes.dualArcadeDrive;
	Joystick1038 driverJoystick = new Joystick1038(0);
	Joystick1038 operatorJoystick = new Joystick1038(1);
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
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
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + m_autoSelected);
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
				break;
		}
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
	
	if(!driverJoystick.getRightButton()) {
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
		
	if(driverJoystick.getXButton()) {
			
	}
	
	if(driverJoystick.getBButton()) {
			
	}
	
	if(driverJoystick.getAButton()) {
		
	}
	
	if(driverJoystick.getYButton()) {
			
	}
	
	if(driverJoystick.getLeftButton()) {
			
	}
		
	if(driverJoystick.getLeftTrigger()) {
			
	}
	
	if(driverJoystick.getRightTrigger()) {
			
	}
	
	if(driverJoystick.getBackButton()) {
			
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
	
	if(driverJoystick.getLeftJoystickClick()) {
			
		}
	if(driverJoystick.getRightJoystickClick()) {
			
	}
}
	
	public void operator() {
		if(operatorJoystick.getXButton()) {
			
		}
		if(operatorJoystick.getBButton()) {
			
		}
		if(operatorJoystick.getAButton()) {
			
		}
		if(operatorJoystick.getYButton()) {
			
		}
		if(operatorJoystick.getLeftButton()) {
			
		}
		if(operatorJoystick.getRightButton()) {
			
		}
		if(operatorJoystick.getLeftTrigger()) {
			
		}
		if(operatorJoystick.getRightTrigger()) {
			
		}
		if(operatorJoystick.getBackButton()) {
			
		}
		if(operatorJoystick.getStartButton()) {
			
		}
		if(operatorJoystick.getLeftJoystickClick()) {
			
		}
		if(operatorJoystick.getRightJoystickClick()) {
			
		}
		/*if(operatorJoystick.getLeftJoystickVertical() != 0.0) {
			
		}
		if(operatorJoystick.getRightJoystickVertical() != 0.0) {
			
		}
		if(operatorJoystick.getLeftJoystickHorizontal() != 0.0) {
			
		}
		if(operatorJoystick.getRightJoystickHorizontal() != 0.0) {
			
		}*/
	}
}
