package org.usfirst.frc.team1038.auton;

import org.usfirst.frc.team1038.robot.Robot;
import org.usfirst.frc.team1038.subsystem.DriveTrain;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDCommand;

public class VisionDriveToTarget extends PIDCommand{
	
	private double drivePower = 0.60;
	private final double END_DRIVE_SPEED = 0.0;
	private final double END_DRIVE_ROTATION = 0.0;
	private final int TOLERANCE = 2;
	private final static double P = 0.015;
	private final static double I = 0.012;
	private final static double D = 0.005;
	private double leftPower;
	private double rightPower;
	private Vision camera = new Vision();
	private DriveTrain drive = DriveTrain.getInstance();
	private PIDController turnPID = getPIDController();

	public VisionDriveToTarget() {
		super(P, I, D, .2);
		setSetpoint(0);
		turnPID.setAbsoluteTolerance(TOLERANCE);
		turnPID.setOutputRange(-.75, .75);
		super.setInputRange(-180, 180);
		turnPID.setContinuous(true);
		requires(Robot.robotDrive);
	}
	
	protected void initialize() {
		turnPID.enable();
	}
	
	protected void execute() {
		double PIDDriveAdjust = turnPID.get();
		this.usePIDOutput(PIDDriveAdjust);
		System.out.println("Drive Adjust: " + PIDDriveAdjust + ", Camera Angle: " + camera.getAngle());
		
	}
	
	protected void end() {
		turnPID.disable();
		turnPID.reset();
		drive.drive(END_DRIVE_SPEED, END_DRIVE_ROTATION);
		double cameraReading = camera.getAngle();
		System.out.println("Finished at " + cameraReading);
	}

	@Override
	protected double returnPIDInput() {
		return camera.getAngle();
	}

	@Override
	protected void usePIDOutput(double turnPower) {
		leftPower = drivePower + (0.5 * turnPower);
		rightPower = drivePower - (0.5 * turnPower);
		if(leftPower > 0.75) {
			leftPower= 0.75;
		}else if(leftPower < -0.75) {
			leftPower = -0.75;
		}
		
		if(rightPower > 0.75) {
			rightPower = 0.75;
		}else if(rightPower < -0.75) {
			rightPower = -0.75;
		}
		
		System.out.println("LeftPower: " + leftPower + ", RightPower: " + rightPower);
		drive.tankDrive(leftPower, rightPower);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
