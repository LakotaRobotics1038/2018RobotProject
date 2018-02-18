package org.usfirst.frc.team1038.robot;

import org.usfirst.frc.team1038.subsystem.DriveTrain;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Dashboard {

	public static void update(double lowPressure, double highPressure)
	{
		SmartDashboard.putNumber("Gyro", I2CGyro.getInstance().getAngle());
		SmartDashboard.putNumber("Left Encoder", DriveTrain.getInstance().getLeftDriveEncoderDistance());
		SmartDashboard.putNumber("Right Encoder", DriveTrain.getInstance().getRightDriveEncoderDistance());
		SmartDashboard.putNumber("High Pressure", highPressure);
		SmartDashboard.putNumber("Low Pressure", lowPressure);
	}
}
