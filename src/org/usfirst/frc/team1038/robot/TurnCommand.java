package org.usfirst.frc.team1038.robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;

public class TurnCommand  extends Command {
	//fields
	private double driveSpeed = 0.0;
	private final double END_DRIVE_SPEED = 0.0;
	private final double END_DRIVE_ROTATION = 0.0;
	private double driveRotationInit = 0.0;
	private int goalAngle = 90;
	private I2CGyro gyroSensor = I2CGyro.getInstance();
	private DriveTrain drive = DriveTrain.getInstance();
	
	//constructor
	public TurnCommand() {
		requires(Robot.robotDrive);
	}
	
	//methods
	protected void initialize() {
		//gyroSensor.resetGyro();
	}
	
	/**
	 * Turns robot until it reaches a certain angle
	 * 
	 * @param turnAngle Goal angle of turn as an integer
	 */
	protected void turn(int turnAngle) {
		goalAngle = turnAngle;
//		if(turnAngle > 0 && turnAngle < 180) {
//			if(gyroSensor.readGyro() > 350 || gyroSensor.readGyro() < turnAngle){
//				driveRotationInit = 0.15;
//			}
//			else if(gyroSensor.readGyro() > turnAngle) {
//				driveRotationInit = -0.15;
//			}else {
//				driveRotationInit = 0.0;
//			}
//		}else {
//			if(gyroSensor.readGyro() < 10 || gyroSensor.readGyro() > turnAngle){
//				driveRotationInit = -0.15;
//			}
//			else if(gyroSensor.readGyro() < turnAngle) {
//				driveRotationInit = 0.15;
//			}else {
//				driveRotationInit = 0.0;
//			}
//		}
		//driveRotationInit = 0.7;
		//drive.singleArcadeDrive(driveSpeed, driveRotationInit);
		//System.out.println(gyroSensor.readGyro());
	}
	
	protected void execute() {
		/*goalAngle = 90;
		if(gyroSensor.readGyro() < goalAngle) {
			driveRotationInit = 0.35;
		}
		else if(gyroSensor.readGyro() > goalAngle) {
			driveRotationInit = -0.35;
		}else {
			driveRotationInit = -0.0;
		}*/
		driveRotationInit = -0.5;
		drive.singleArcadeDrive(driveSpeed, driveRotationInit);
		System.out.println(gyroSensor.readGyro());
	}
	
	protected void end() {
		int gyroReading = gyroSensor.readGyro();
		drive.drive(END_DRIVE_SPEED, END_DRIVE_ROTATION);
		System.out.println("Finished at " + gyroReading);
	}
	
	protected void interrupted() {
		end();
	}
	
	@Override
	protected boolean isFinished() {
		return gyroSensor.readGyro() >= goalAngle;
	}
}
