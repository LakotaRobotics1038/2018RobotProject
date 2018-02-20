package org.usfirst.frc.team1038.robot;

import org.usfirst.frc.team1038.subsystem.DriveTrain;
import org.usfirst.frc.team1038.subsystem.Elevator;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Dashboard {
	
	private static String position = SmartDashboard.getString("Start Position", "C");

	public static void update(double lowPressure, double highPressure)
	{
		SmartDashboard.putNumber("Gyro", I2CGyro.getInstance().getAngle());
		SmartDashboard.putNumber("Left Encoder", DriveTrain.getInstance().getLeftDriveEncoderDistance());
		SmartDashboard.putNumber("Right Encoder", DriveTrain.getInstance().getRightDriveEncoderDistance());
		SmartDashboard.putNumber("High Pressure", highPressure);
		SmartDashboard.putNumber("Low Pressure", lowPressure);
		SmartDashboard.putNumber("Elevator Encoder", Elevator.getInstance().getEncoderCount());
		SmartDashboard.putNumber("Elevator Motor Value", Elevator.getInstance().getMotorOutput());
	}
	
	public static String getPosition() {
		return position;
	}
}
