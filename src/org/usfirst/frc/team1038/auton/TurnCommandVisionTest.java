package org.usfirst.frc.team1038.auton;

import org.usfirst.frc.team1038.robot.I2CGyro;
import org.usfirst.frc.team1038.robot.Robot;
import org.usfirst.frc.team1038.subsystem.DriveTrain;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDCommand;

public class TurnCommandVisionTest extends PIDCommand {
	//fields
	private double drivePower = 0.0;
	private final double END_DRIVE_SPEED = 0.0;
	private final double END_DRIVE_ROTATION = 0.0;
	private final int TOLERANCE = 2;
	private final static double P = 0.015;
	private final static double I = 0.015;
	private final static double D = 0.005;
	private I2CGyro gyroSensor = I2CGyro.getInstance();
	private Vision camera = new Vision();
	private DriveTrain drive = DriveTrain.getInstance();
	private PIDController turnPID = getPIDController();
	
	//constructor
	public TurnCommandVisionTest() {
		super(P, I, D, .2);
		setSetpoint(2);
		turnPID.setAbsoluteTolerance(TOLERANCE);
		turnPID.setOutputRange(-.65, .65);
		turnPID.setContinuous(true);
		requires(Robot.robotDrive);
	}
	
	//methods
	public void initialize() {
		gyroSensor.resetGyro();
		super.setInputRange(0, 359);
		
		if (camera.getAngle() > 0)
			setSetpoint(camera.getAngle());
		else if (camera.getAngle() < 0)
			setSetpoint(360 + camera.getAngle());
		else
			System.out.println("target not found. Please try again.");
		
		//super.setInputRange(-180, 180);
	}
	
	public void execute() {
		turnPID.enable();
		double PIDTurnAdjust = turnPID.get();
	
//		//if buttonPressed()
//		if (camera.getAngle() > 0)
//			setSetpoint(camera.getAngle());
//		else if (camera.getAngle() < 0)
//			setSetpoint(360 + camera.getAngle());
//		else
//			System.out.println("target not found. Please try again.");
		
	
		//System.out.println(camera.getAngle());
		
		if(getSetpoint() < 180) {
			drive.dualArcadeDrive(drivePower, PIDTurnAdjust);
		}else {
			drive.dualArcadeDrive(drivePower, -PIDTurnAdjust);
		}
		
		System.out.println("Current Angle: " + gyroSensor.getAngle() + ", PIDTurnAdjust: " + turnPID.get() + ", setPoint: " + getSetpoint());
	}
	
	@Override
	public void end() {
		turnPID.disable();
		turnPID.reset();
		turnPID.free();
		//double visionAngle = camera.getAngle();
		drive.dualArcadeDrive(END_DRIVE_SPEED, END_DRIVE_ROTATION);
		//System.out.println("Finished at " + visionAngle);
		System.out.println("Finished at " + gyroSensor.getAngle());
	}
	
	@Override
	public boolean isFinished() {
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

