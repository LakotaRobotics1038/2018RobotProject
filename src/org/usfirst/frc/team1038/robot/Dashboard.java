package org.usfirst.frc.team1038.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Dashboard {

	public static void execute()
	{
		SmartDashboard.putNumber("GyroVal", I2CGyro.getInstance().readGyro());
		SmartDashboard.putNumber("LeftEncoderVal", DriveTrain.getInstance().getLeftDriveEncoderDistance());
		SmartDashboard.putNumber("RightEncoderVal", DriveTrain.getInstance().getRightDriveEncoderDistance());
	}
}
