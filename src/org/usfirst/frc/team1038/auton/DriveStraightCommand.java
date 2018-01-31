package org.usfirst.frc.team1038.auton;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDCommand;

import org.usfirst.frc.team1038.robot.DriveTrain;
import org.usfirst.frc.team1038.robot.I2CGyro;
import org.usfirst.frc.team1038.robot.Robot;

public class DriveStraightCommand extends PIDCommand{

	private double turnPower = 0.0;
	private final double END_DRIVE_SPEED = 0.0;
	private final double END_DRIVE_ROTATION = 0;
	private final int TOLERANCE = 2;
	private final static double P = 0.015;
	private final static double I = 0.015;
	private final static double D = 0.005;
	private double driveDistance = 48;
	private I2CGyro gyroSensor = I2CGyro.getInstance();
	private DriveTrain drive = DriveTrain.getInstance();
	private PIDController drivePID = getPIDController();
	
	public double getDriveDistance() {
		return driveDistance;
	}
	
	public DriveStraightCommand(int setpoint) {
		super(P, I, D, .2);
		setSetpoint(setpoint - 5);
		drivePID.setAbsoluteTolerance(TOLERANCE);
		drivePID.setOutputRange(-.65, .65);
		requires(Robot.robotDrive);
	}
	
	protected void initialize() {
		gyroSensor.resetGyro();
		drive.resetEncoders();
	}
	
	protected void execute() {
		drivePID.enable();
		double PIDDriveAdjust = drivePID.get();
		drive.dualArcadeDrive(-PIDDriveAdjust, turnPower);
		System.out.println("Current Angle: " + gyroSensor.getAngle() + ", PIDDriveAdjust: " + drivePID.get() + ", Current Distance: " + drive.getLeftDriveEncoderDistance());
	}
	
	@Override
	protected void end() {
		drivePID.disable();
		double distance = drive.getLeftDriveEncoderDistance();
		drive.drive(END_DRIVE_SPEED, END_DRIVE_ROTATION);
		System.out.println("Finished at " + distance);
	}
	
	
	@Override
	protected boolean isFinished() {
		System.out.println("Checked: " + drive.getLeftDriveEncoderDistance());
		return drivePID.onTarget();
	}
	
	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return drive.getLeftDriveEncoderDistance();
	}

	@Override
	protected void usePIDOutput(double drivePower) {
		// TODO Auto-generated method stub
		drive.dualArcadeDrive(drivePower, turnPower);		
	}

}
