package org.usfirst.frc.team1038.robot;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1038.robot.Robot;

public class DriveStraightCommand extends Command{

	
	private double driveSpeed = 0.2;
	private double driveSpeedEnd = 0.0;
	private double driveRotation = 0;
	private double driveDistance = 36;
	
	public double getDriveDistance() {
		return driveDistance;
	}
	
	public DriveStraightCommand() {
		requires(Robot.robotDrive);
	}
	
	protected void initialize() {
		
	}
	
	protected void execute() {
		DriveTrain.getInstance().drive(driveSpeed,driveRotation);
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
