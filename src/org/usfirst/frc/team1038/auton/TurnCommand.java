package org.usfirst.frc.team1038.auton;

import org.usfirst.frc.team1038.robot.I2CGyro;
import org.usfirst.frc.team1038.robot.Robot;
import org.usfirst.frc.team1038.subsystem.DriveTrain;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDCommand;

public class TurnCommand extends PIDCommand {
	//fields
	private double drivePower = 0.0;
	private final double END_DRIVE_SPEED = 0.0;
	private final double END_DRIVE_ROTATION = 0.0;
	private final int TOLERANCE = 2;
	private final static double P = 0.015;
	private final static double I = 0.015;
	private final static double D = 0.005;
	private I2CGyro gyroSensor = I2CGyro.getInstance();
	private DriveTrain drive = DriveTrain.getInstance();
	private PIDController turnPID = getPIDController();
	
	//constructor
	public TurnCommand(int setpoint) {
		super(P, I, D, .2);
		setSetpoint(setpoint - 5);
		turnPID.setAbsoluteTolerance(TOLERANCE);
		turnPID.setOutputRange(-.65, .65);
		requires(Robot.robotDrive);
	}
	
	//methods
	protected void initialize() {
		gyroSensor.resetGyro();
		super.setInputRange(0, 359);
	}
	
	protected void execute() {
		turnPID.enable();
		double PIDTurnAdjust = turnPID.get();
		if(getSetpoint() > 180)
			drive.dualArcadeDrive(drivePower, PIDTurnAdjust);
		else
			drive.dualArcadeDrive(drivePower, -PIDTurnAdjust);
		System.out.println("Current Angle: " + gyroSensor.getAngle() + ", PIDTurnAdjust: " + turnPID.get());
	}
	
	@Override
	protected void end() {
		turnPID.disable();
		double gyroReading = gyroSensor.getAngle();
		drive.drive(END_DRIVE_SPEED, END_DRIVE_ROTATION);
		System.out.println("Finished at " + gyroReading);
	}
	
	@Override
	protected boolean isFinished() {
		System.out.println("Checked: " + gyroSensor.getAngle());		
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
