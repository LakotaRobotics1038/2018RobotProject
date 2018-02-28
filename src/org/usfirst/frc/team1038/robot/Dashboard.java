package org.usfirst.frc.team1038.robot;

import org.usfirst.frc.team1038.auton.TurnCommand;
import org.usfirst.frc.team1038.auton.TurnCommandPID;
import org.usfirst.frc.team1038.subsystem.DriveTrain;

import edu.wpi.cscore.HttpCamera;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Dashboard {

	public static void update()
	{
		SmartDashboard.putNumber("Gyro", I2CGyro.getInstance().getAngle());
		SmartDashboard.putNumber("Left Encoder", DriveTrain.getInstance().getLeftDriveEncoderDistance());
		SmartDashboard.putNumber("Right Encoder", DriveTrain.getInstance().getRightDriveEncoderDistance());
		SmartDashboard.putNumber("Turn Command", TurnCommand.P);
		SmartDashboard.putNumber("Turn Command", TurnCommand.I);
		SmartDashboard.putNumber("Turn Command", TurnCommand.D);
		SmartDashboard.getNumber("HueMax", 39);
		SmartDashboard.getNumber("HueMin", 20);
		SmartDashboard.getNumber("SaturationMax", 255);
		SmartDashboard.getNumber("SaturationMin", 0);
		SmartDashboard.getNumber("ValueMax", 255);
		SmartDashboard.getNumber("ValueMin", 0);
		SmartDashboard.putString("CameraPublisher","10.10.38.101:1180/?action=stream");
	}
}
