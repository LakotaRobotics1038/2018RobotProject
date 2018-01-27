package org.usfirst.frc.team1038.robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;

public class TurnCommand extends PIDCommand {
	//fields
	private double drivePower = 0.0;
	private Timer timer = new Timer();
	private boolean timerRunning = false;
	private final double END_DRIVE_SPEED = 0.0;
	private final double END_DRIVE_ROTATION = 0.0;
	private final int TOLERANCE = 2;
	private final static double P = 0.080;
	private final static double I = 0.015;
	private final static double D = 0.001;
	private I2CGyro gyroSensor = I2CGyro.getInstance();
	private DriveTrain drive = DriveTrain.getInstance();
	private PIDController PIDController = getPIDController();
	
	//constructor
	public TurnCommand(int setpoint) {
		super(P, I, D, .2);
		setSetpoint(setpoint);
		PIDController.setAbsoluteTolerance(TOLERANCE);
		PIDController.setOutputRange(-.65, .65);
		requires(Robot.robotDrive);
	}
	
	//methods
	protected void initialize() {
		gyroSensor.resetGyro();
		PIDController.enable();
		super.setInputRange(0, 359);
	}
	
	protected void execute() {
	
		PIDController.enable();
		double PIDTurnAdjust = PIDController.get();
		drive.dualArcadeDrive(drivePower, -PIDTurnAdjust);
		System.out.println("Current Angle: " + gyroSensor.getAngle() + ", PIDTurnAdjust: " + PIDController.get());
	}
	
	@Override
	protected void end() {
		PIDController.disable();
		double gyroReading = gyroSensor.getAngle();
		drive.drive(END_DRIVE_SPEED, END_DRIVE_ROTATION);
		System.out.println("Finished at " + gyroReading);
	}
	
	@Override
	protected boolean isFinished() {
		System.out.println("Checked: " + gyroSensor.getAngle()/* + ", " + timer.get()*/);
//		if (!timerRunning && PIDController.onTarget())
//		{
//			System.out.println("Timer Started");
//			timer.start();
//			timerRunning = true;
//		}	
//		else if (timerRunning)
//		{
//			if (timer.get() >= .25 && PIDController.onTarget())
//			{
//				System.out.println("Done");
//				timerRunning = false;
//				return true;
//			}
//			else if (!PIDController.onTarget())
//			{
//				System.out.println("Timer failed");
//				timerRunning = false;
//				timer.stop();
//				timer.reset();		
//			}
//		}
		
		return PIDController.onTarget();
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
