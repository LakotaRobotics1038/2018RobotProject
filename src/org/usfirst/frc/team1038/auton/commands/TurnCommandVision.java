package org.usfirst.frc.team1038.auton.commands;

import org.usfirst.frc.team1038.auton.Vision;
import org.usfirst.frc.team1038.robot.I2CGyro;
import org.usfirst.frc.team1038.robot.Robot;
import org.usfirst.frc.team1038.subsystem.DriveTrain;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDCommand;

public class TurnCommandVision extends PIDCommand {
	//fields
	private double drivePower = 0.0;
	private final double END_DRIVE_SPEED = 0.0;
	private final double END_DRIVE_ROTATION = 0.0;
	private final int TOLERANCE = 1;
	private final static double P = 0.015;
	private final static double I = 0.012;
	private final static double D = 0.005;
	private I2CGyro gyroSensor = I2CGyro.getInstance();
	private Vision camera = new Vision();
	private DriveTrain drive = DriveTrain.getInstance();
	private PIDController turnPID = getPIDController();
	boolean hasSeen = false;
	
	//constructor
	public TurnCommandVision() {
		super(P, I, D, .2);
		setSetpoint(2);
		turnPID.setAbsoluteTolerance(TOLERANCE);
		turnPID.setOutputRange(-.65, .65);
		super.setInputRange(0, 359);
		turnPID.setContinuous(true);
		requires(Robot.robotDrive);
	}
	
	//methods
	protected void initialize() {
		gyroSensor.reset();
	}
	
	public void execute() {
		turnPID.enable();
		double PIDTurnAdjust = turnPID.get();
		
		while(!hasSeen) {
			if(camera.getAngle() != 0) {
				hasSeen = true;
				if(camera.getAngle() > 0) {
					setSetpoint(camera.getAngle());
				}else {
					setSetpoint(360 + camera.getAngle());
				}
			}
		}
		
		this.usePIDOutput(-PIDTurnAdjust);
		
		System.out.println("Current Angle: " + gyroSensor.getAngle() + ", PIDTurnAdjust: " + turnPID.get() + ", setPoint: " + getSetpoint());
	}
	
	@Override
	protected void end() {
		turnPID.disable();
		turnPID.reset();
		hasSeen = false;
		drive.drive(END_DRIVE_SPEED, END_DRIVE_ROTATION);
		double gyroReading = gyroSensor.getAngle();
		System.out.println("Finished at " + gyroReading);
		setSetpoint(5);
	}
	
	@Override
	protected boolean isFinished() {
		return turnPID.onTarget();
	}

	@Override
	protected double returnPIDInput() {
		return gyroSensor.getAngle();
	}

	@Override
	protected void usePIDOutput(double turnPower) {
		drive.dualArcadeDrive(drivePower, turnPower);	
	}
}
