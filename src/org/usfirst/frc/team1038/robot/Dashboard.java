package org.usfirst.frc.team1038.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Dashboard {

	public static void update()
	{
		SmartDashboard.putNumber("Gyro", I2CGyro.getInstance().getAngle());
		SmartDashboard.putNumber("Left Encoder", DriveTrain.getInstance().getLeftDriveEncoderDistance());
		SmartDashboard.putNumber("Right Encoder", DriveTrain.getInstance().getRightDriveEncoderDistance());
	}
}
