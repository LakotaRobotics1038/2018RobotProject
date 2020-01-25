package org.usfirst.frc.team1038.auton.commands;

import org.usfirst.frc.team1038.robot.I2CGyro;
import org.usfirst.frc.team1038.robot.Robot;
import org.usfirst.frc.team1038.subsystem.DriveTrain;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveStraightCommand extends PIDCommand {
	
	private final double END_DRIVE_SPEED = 0.0;
	private final double END_DRIVE_ROTATION = 0.0;
	private final double TOLERANCE = 1.9;
	private final double MAX_OUTPUT = .8;
	private final static double dP = 0.150; //.04 proto
	private final static double dI = 0.000;
	private final static double dD = 0.002;
	private final static double tP = 0.200; //.23 proto
	private final static double tI = 0.001;
	private final static double tD = 0.000;
	private I2CGyro gyroSensor = I2CGyro.getInstance();
	private DriveTrain drive = DriveTrain.getInstance();
	private PIDController drivePID = getPIDController();
	private PIDController turnPID = new PIDController(tP, tI, tD, gyroSensor, Robot.emptySpark);
		
	/**
	 * Makes a new Drive Straight Command
	 * @param setpoint in feet
	 */
	public DriveStraightCommand(double setpoint) {
		//Drive
		super(dP, dI, dD);
		setSetpoint(setpoint * 12);
		drivePID.setAbsoluteTolerance(TOLERANCE);
		drivePID.setOutputRange(-MAX_OUTPUT, MAX_OUTPUT);
		drivePID.setContinuous(false);
		SmartDashboard.putData("Controls/Drive Straight", drivePID);
			
		//Angle
		turnPID.setAbsoluteTolerance(TOLERANCE);
		turnPID.setOutputRange(-MAX_OUTPUT, MAX_OUTPUT);
		turnPID.setInputRange(0, 360);
		turnPID.setContinuous(true);
		SmartDashboard.putData("Controls/Drive Straight Angle", turnPID);
		requires(Robot.robotDrive);
	}
		
	@Override
	public void initialize() {
//		gyroSensor.reset();
		turnPID.setSetpoint(gyroSensor.getAngle());
		drive.resetEncoders();
		//turnPID.setInputRange(0, 359);
	}
		
	@Override
	public void execute() {
		drivePID.enable();
		turnPID.enable();
		double distancePID = drivePID.get();
		double anglePID = turnPID.get();
		System.out.println("dist out: " + distancePID + " ang out: " + anglePID + " ang sp: " + turnPID.getSetpoint() + "ang: " + gyroSensor.getAngle());
		usePIDOutput(distancePID, anglePID);
	}
		
	@Override
	public void interrupted() {
		end();
		System.out.println("Straight interrupted");
	}
		
	@Override
	public void end() {
		drivePID.reset();
		turnPID.reset();
		drive.drive(END_DRIVE_SPEED, END_DRIVE_ROTATION);
		System.out.println("DriveStraight ended");
	}
		
	@Override
	public boolean isFinished() {
		return drivePID.onTarget() && turnPID.onTarget();
	}

	@Override
	protected double returnPIDInput() {
		return drive.getRightDriveEncoderDistance();
	}

	/**
	 * Uses the value that the pid loop calculated
	 * @param drivePower Power to drive straight
	 * @param turnPower  Power to turn (used to maintain heading)
	 */
	protected void usePIDOutput(double drivePower, double turnPower) {
		drive.dualArcadeDrive(drivePower, turnPower);		
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		usePIDOutput(output, turnPID.get());
	}
}
