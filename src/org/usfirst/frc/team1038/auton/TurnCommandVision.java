package org.usfirst.frc.team1038.auton;

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
	private final static double I = 0.015;
	private final static double D = 0.005;
	private I2CGyro gyroSensor = I2CGyro.getInstance();
	private Vision camera = new Vision();
	private DriveTrain drive = DriveTrain.getInstance();
	private PIDController turnPID = getPIDController();
	
	//constructor
	public TurnCommandVision() {
		super(P, I, D, .2);
		setSetpoint(2);
		turnPID.setAbsoluteTolerance(TOLERANCE);
		turnPID.setOutputRange(-.65, .65);
		requires(Robot.robotDrive);
	}
	
	//methods
	protected void initialize() {
		gyroSensor.resetGyro();
		//super.setInputRange(-180, 180);
		super.setInputRange(0, 359);
	}
	
	public void execute() {
		turnPID.enable();
		double PIDTurnAdjust = turnPID.get();
//		if(getSetpoint() > camera.getAngle())
//			drive.dualArcadeDrive(drivePower, PIDTurnAdjust);
//		else
//			drive.dualArcadeDrive(drivePower, -PIDTurnAdjust);
		
		//System.out.println("Current Angle: " + camera.getAngle() + ", PIDTurnAdjust: " + turnPID.get());
		boolean hasSeen = false;
		while(!hasSeen) {
			if(camera.getAngle() != 0) {
				hasSeen = true;
				if(camera.getAngle() > 0) {
					setSetpoint(camera.getAngle() + 2);
				}else {
					setSetpoint(360 + camera.getAngle() - 2);
				}
			}
		}
		System.out.println(camera.getAngle());
		
		if(getSetpoint() < 180) {
			drive.dualArcadeDrive(drivePower, PIDTurnAdjust);
		}else {
			drive.dualArcadeDrive(drivePower, -PIDTurnAdjust);
		}
		
		System.out.println("Current Angle: " + gyroSensor.getAngle() + ", PIDTurnAdjust: " + turnPID.get() + ", setPoint: " + getSetpoint());
	}
	
	@Override
	protected void end() {
		turnPID.disable();
		//double visionAngle = camera.getAngle();
		drive.drive(END_DRIVE_SPEED, END_DRIVE_ROTATION);
		//System.out.println("Finished at " + visionAngle);
		System.out.println("Finished at " + gyroSensor.getAngle());
	}
	
	@Override
	protected boolean isFinished() {
		//System.out.println("Checked: " + camera.getAngle());		
		return turnPID.onTarget();
		//return false;
	}

	@Override
	protected double returnPIDInput() {
		//return camera.getAngle();
		return gyroSensor.getAngle();
	}

	@Override
	protected void usePIDOutput(double turnPower) {
		drive.dualArcadeDrive(drivePower, turnPower);		
	}
}
