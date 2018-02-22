package org.usfirst.frc.team1038.robot;

import org.usfirst.frc.team1038.subsystem.DriveTrain;
import org.usfirst.frc.team1038.subsystem.Elevator;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Dashboard {
	
	private String position;
	private String autonChooser;
	private static Dashboard dashboard;
	
	public static Dashboard getInstance() {
		if (dashboard == null) {
			System.out.println("Creating a new Dashboard");
			dashboard = new Dashboard();
		}
		return dashboard;
	}
	
	private Dashboard()
	{
		
	}
	
	public void update(double lowPressure, double highPressure)
	{
		SmartDashboard.putNumber("Gyro", I2CGyro.getInstance().getAngle());
		SmartDashboard.putNumber("Left Encoder", DriveTrain.getInstance().getLeftDriveEncoderDistance());
		SmartDashboard.putNumber("Right Encoder", DriveTrain.getInstance().getRightDriveEncoderDistance());
		SmartDashboard.putNumber("High Pressure", highPressure);
		SmartDashboard.putNumber("Low Pressure", lowPressure);
		SmartDashboard.putNumber("Elevator Encoder", Elevator.getInstance().getEncoderCount());
		SmartDashboard.putNumber("Elevator Motor Value", Elevator.getInstance().getMotorOutput());
		position = SmartDashboard.getString((String)Robot.startPosition.getSelected(), "None");
		autonChooser = SmartDashboard.getString((String)Robot.autoChooser.getSelected(), "None");

		System.out.println(autonChooser);
		System.out.println(position);
	}
	
	public String getPosition() {
		return position;
	}
	
	public String getAutonChooser() {
		return autonChooser;
	}
}
