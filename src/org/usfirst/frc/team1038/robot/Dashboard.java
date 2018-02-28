package org.usfirst.frc.team1038.robot;

<<<<<<< HEAD
import org.usfirst.frc.team1038.auton.TurnCommand;
import org.usfirst.frc.team1038.auton.TurnCommandPID;
=======
import org.usfirst.frc.team1038.subsystem.AcquisitionScoring;
>>>>>>> b6287eb0a1c15d63a3b2e108daee6ae022fadbb5
import org.usfirst.frc.team1038.subsystem.DriveTrain;
import org.usfirst.frc.team1038.subsystem.Elevator;

import edu.wpi.cscore.HttpCamera;
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
<<<<<<< HEAD
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
=======
		SmartDashboard.putNumber("High Pressure", highPressure);
		SmartDashboard.putNumber("Low Pressure", lowPressure);
		SmartDashboard.putNumber("Elevator Encoder", Elevator.getInstance().getEncoderCount());
		SmartDashboard.putNumber("Elevator Motor Value", Elevator.getInstance().getMotorOutput());
		SmartDashboard.putNumber("Acq Motor Speed", AcquisitionScoring.getInstance().getAcqSpeed());
		position = Robot.startPosition.getSelected();
		autonChooser = Robot.autoChooser.getSelected();
	}
	
	public String getPosition() {
		return position;
	}
	
	public String getAutonChooser() {
		return autonChooser;
>>>>>>> b6287eb0a1c15d63a3b2e108daee6ae022fadbb5
	}
}
