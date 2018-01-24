package org.usfirst.frc.team1038.robot;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1038.robot.Robot;

public class DriveStraightCommand extends Command{

	
	private double driveSpeed = 0.4;
	private final double END_DRIVE_SPEED = 0.0;
	private double driveRotation = 0.0;
	private double driveDistance = 48;
	private I2CGyro gyroSensor = I2CGyro.getInstance();
	private DriveTrain drive = DriveTrain.getInstance();
	
	public double getDriveDistance() {
		return driveDistance;
	}
	
	public DriveStraightCommand(int distance) {
		driveDistance = distance;
		requires(Robot.robotDrive);
	}
	
	protected void initialize() {
		gyroSensor.resetGyro();
	}
	
	protected void execute() {
		if(gyroSensor.getAngle() > 0 && gyroSensor.getAngle() < 180){
			driveRotation = 0.15;
		}
		else if(gyroSensor.getAngle() > 180 && gyroSensor.getAngle() <= 359) {
			driveRotation = -0.15;
		}else {
			driveRotation = 0.0;
		}
		drive.drive(driveSpeed,driveRotation);
		System.out.println(gyroSensor.getAngle());
	}
	
	protected void end() {
		drive.drive(END_DRIVE_SPEED, driveRotation);
	}
	
	protected void interrupted() {
		end();
	}
	
	
	@Override
	protected boolean isFinished() {
		 return DriveTrain.getInstance().getLeftDriveEncoderDistance() >= driveDistance;
	}

}
