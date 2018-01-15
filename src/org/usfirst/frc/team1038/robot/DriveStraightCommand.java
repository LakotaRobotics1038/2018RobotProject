package org.usfirst.frc.team1038.robot;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1038.robot.Robot;

public class DriveStraightCommand extends Command{

	
	private double driveSpeed = 0.3;
	private double driveSpeedEnd = 0.0;
	private double driveRotation = 0.0;
	private double driveDistance = 72;
	I2CGyro gyroSensor = new I2CGyro();
	
	public double getDriveDistance() {
		return driveDistance;
	}
	
	public DriveStraightCommand() {
		requires(Robot.robotDrive);
	}
	
	protected void initialize() {
		gyroSensor.resetGyro();
	}
	
	protected void execute() {
		if(gyroSensor.readGyro() > 0 && gyroSensor.readGyro() < 180){
			driveRotation = 0.15;
		}
		else if(gyroSensor.readGyro() > 180 && gyroSensor.readGyro() <= 359) {
			driveRotation = -0.15;
		}else {
			driveRotation = 0.0;
		}
		DriveTrain.getInstance().drive(driveSpeed,driveRotation);
		System.out.println(gyroSensor.readGyro());
	}
	
	protected void end() {
		DriveTrain.getInstance().drive(driveSpeedEnd, driveRotation);
	}
	
	protected void interrupted() {
		end();
	}
	
	
	@Override
	protected boolean isFinished() {
		 return DriveTrain.getInstance().getLeftDriveEncoderDistance() >= driveDistance;
	}

}
