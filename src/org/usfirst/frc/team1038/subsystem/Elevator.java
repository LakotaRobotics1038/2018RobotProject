package org.usfirst.frc.team1038.subsystem;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Elevator extends PIDSubsystem {

	//fields
	private final static double P = 0;
	private final static double I = 0;
	private final static double D = 0;
	private final int ELEVATOR_CHANNEL_A = 4;
	private final int ELEVATOR_CHANNEL_B = 5;
	private final int ELEVATOR_PROX_HIGH_PORT = 10;
	private final int ELEVATOR_PROX_LOW_PORT = 11;
	private final int ELEVATOR_MOTOR_PORT = 5;
	private double elevatorSpeed = 0;
	private Spark elevatorSpark = new Spark(ELEVATOR_MOTOR_PORT);
	private Encoder elevatorEncoder = new Encoder(ELEVATOR_CHANNEL_A, ELEVATOR_CHANNEL_B, false);
	private DigitalInput proxHigh = new DigitalInput(ELEVATOR_PROX_HIGH_PORT);
	private DigitalInput proxLow = new DigitalInput(ELEVATOR_PROX_LOW_PORT);
	
	public Elevator() {
		super(P, I ,D);
		
		// TODO Auto-generated constructor stub
	}
	
	//methods
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
	
	public boolean getLowProx()
	{
		return proxLow.get();
	}
	
	public void moveToScale() {
		
	}
	
	public void moveToSwitch() {
		
	}
	
	public void moveToPortal() {
		
	}
	
	public void moveToFloor() {
		
	}
	
	public void move(double joystickValue) {
		if(!proxLow.get())
		{
			elevatorSpark.set(joystickValue);
		}
	}
	
	public void encoderZero() {
		
	}

	@Override
	protected double returnPIDInput() {
		return elevatorEncoder.get();
	}

	@Override
	protected void usePIDOutput(double output) {
		elevatorSpark.set(output);
		
	}
}