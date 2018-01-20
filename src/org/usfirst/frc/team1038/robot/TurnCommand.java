package org.usfirst.frc.team1038.robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDCommand;

public class TurnCommand extends PIDCommand {
	//fields
	private double drivePower = 0.0;
	private final double END_DRIVE_SPEED = 0.0;
	private final double END_DRIVE_ROTATION = 0.0;
	private final double TOLERANCE = 0.0;
	private final static double P = 0.000;
	private final static double I = 0.000;
	private final static double D = 0.000;
	//private double driveRotationInit = 0.0;
	//private int goalAngle = 90;
	private I2CGyro gyroSensor = I2CGyro.getInstance();
	private DriveTrain drive = DriveTrain.getInstance();
	private PIDController PIDControllerTurnAdjust = getPIDController();
	
	//constructor
	public TurnCommand(int setpoint) {
		super(P, I, D);
		setSetpoint(setpoint);
		PIDControllerTurnAdjust.setAbsoluteTolerance(TOLERANCE);
		requires(Robot.robotDrive);
	}
	
	//methods
	protected void initialize() {
		gyroSensor.resetGyro();
		PIDControllerTurnAdjust.enable();
		super.setInputRange(0, 359);
	}
	
	/**
	 * Turns robot until it reaches a certain angle
	 * 
	 * @param turnAngle Goal angle of turn as an integer
	 */
//	protected void turn(int turnAngle) {
//		goalAngle = turnAngle;
//		if(turnAngle > 0 && turnAngle < 180) {
//			if(gyroSensor.getAngle() > 350 || gyroSensor.getAngle() < turnAngle){
//				driveRotationInit = 0.15;
//			}
//			else if(gyroSensor.getAngle() > turnAngle) {
//				driveRotationInit = -0.15;
//			}else {
//				driveRotationInit = 0.0;
//			}
//		}else {
//			if(gyroSensor.getAngle() < 10 || gyroSensor.getAngle() > turnAngle){
//				driveRotationInit = -0.15;
//			}
//			else if(gyroSensor.getAngle() < turnAngle) {
//				driveRotationInit = 0.15;
//			}else {
//				driveRotationInit = 0.0;
//			}
//		}
		//driveRotationInit = 0.7;
		//drive.singleArcadeDrive(driveSpeed, driveRotationInit);
		//System.out.println(gyroSensor.getAngle());
//	}
	
	protected void execute() {
	
		getPIDController().enable();
		double PIDTurnAdjust = PIDControllerTurnAdjust.get();
		/*goalAngle = 90;
		if(gyroSensor.getAngle() < goalAngle) {
			driveRotationInit = 0.35;
		}
		else if(gyroSensor.getAngle() > goalAngle) {
			driveRotationInit = -0.35;
		}else {
			driveRotationInit = -0.0;
		}*/
		//driveRotationInit = -0.65;
		drive.dualArcadeDrive(drivePower, -1* PIDTurnAdjust);
		System.out.println("Current Angle: " + gyroSensor.getAngle() + ", PIDTurnAdjust: " + PIDControllerTurnAdjust.get());
	}
	
	protected void end() {
		double gyroReading = gyroSensor.getAngle();
		drive.drive(END_DRIVE_SPEED, END_DRIVE_ROTATION);
		System.out.println("Finished at " + gyroReading);
	}
//	
//	protected void interrupted() {
//		end();
//	}
	
	@Override
	protected boolean isFinished() {
		System.out.println("Check Finshed\n\n\n\n\n\n\n\n\n\n\n" + gyroSensor.getAngle());
		return gyroSensor.getAngle() == getSetpoint();
	}

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return gyroSensor.getAngle();
	}

	@Override
	protected void usePIDOutput(double turnPower) {
		// TODO Auto-generated method stub
		drive.dualArcadeDrive(drivePower, turnPower);		
	}
}
