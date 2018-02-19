package org.usfirst.frc.team1038.subsystem;

import org.usfirst.frc.team1038.robot.Prox;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Elevator extends PIDSubsystem {

	//fields
	private final int TOLERANCE = 1;
	private final static double P = 4.0;
	private final static double I = .001;
	private final static double D = .001;
	private final static int SCALE_HIGH = 615;
	private final int SCALE_LOW = 480;
	private final int MIDDLE = 300;
	private final int SWITCH = 10;
	private final int PORTAL = 0; // TODO find value
	private final int FLOOR = 0;
	private final int ELEVATOR_CHANNEL_A = 4;
	private final int ELEVATOR_CHANNEL_B = 5;
	private final int ELEVATOR_PROX_HIGH_PORT = 10;
	private final int ELEVATOR_PROX_LOW_PORT = 11;
	private final int ELEVATOR_MOTOR_PORT = 5;
	private Spark elevatorSpark = new Spark(ELEVATOR_MOTOR_PORT);
	private Encoder elevatorEncoder = new Encoder(ELEVATOR_CHANNEL_A, ELEVATOR_CHANNEL_B, false);
	private Prox highProx = new Prox(ELEVATOR_PROX_HIGH_PORT);
	private Prox lowProx = new Prox(ELEVATOR_PROX_LOW_PORT);
	private PIDController elevatorPID = getPIDController();
	private static Elevator elevator;
	
	public static Elevator getInstance() {
		if (elevator == null) {
			System.out.println("Creating a new Elevator");
			elevator = new Elevator();
		}
		return elevator;
	}
	
	private Elevator() {
		super(P / SCALE_HIGH, I /*/ SCALE_HIGH*/, D /*/ SCALE_HIGH*/);
		elevatorPID.setAbsoluteTolerance(TOLERANCE);
		elevatorPID.setOutputRange(-.5, .75);
		super.setInputRange(0, 615);
		elevatorPID.setContinuous(false);
	}
	
	//methods	
	public boolean getLowProx() {
		return lowProx.get();
	}
	
	public boolean getHighProx() {
		return highProx.get();
	}
	
	public int getEncoderCount() {
		return elevatorEncoder.get();
	}
	public double getMotorOutput() {
		return elevatorSpark.get();
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
	
	public void elevatorPeriodic() {
		double PIDValue = elevatorPID.get();
		usePIDOutput(-PIDValue);
	}
	
	public void moveToScaleHigh() {
		elevatorPID.enable();
		setSetpoint(SCALE_HIGH);
	}
	

	public void moveToScaleLow() {
		elevatorPID.enable();
		setSetpoint(MIDDLE);
	}
	
	public void moveToSwitch() {
		elevatorPID.enable();
		setSetpoint(SWITCH);
	}
	
	public void moveToPortal() {
		elevatorPID.enable();
		setSetpoint(PORTAL);
	}
	
	public void moveToFloor() {
		elevatorPID.enable();
		setSetpoint(FLOOR);
	}
	
	public void move(double joystickValue) {
		if(getSetpoint() <= SCALE_HIGH && joystickValue > .09)
		{
			setSetpoint(getSetpoint() + 2);
		}
		else if(getSetpoint() > 0 && joystickValue < -.09)
		{
			setSetpoint(getSetpoint() - 2);
		}
	}
	
	public void resetEncoder() {
		elevatorEncoder.reset();
	}

	@Override
	protected double returnPIDInput() {
		return elevatorEncoder.get();
	}

	@Override
	protected void usePIDOutput(double output) {
		if ((output > 0 && !lowProx.get()) || (output < 0 && !highProx.get()))
			elevatorSpark.set(output);
	}
}