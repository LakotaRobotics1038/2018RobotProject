package org.usfirst.frc.team1038.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Elevator extends Subsystem{
	
	//fields
	private final int ELEVATOR_CHANNEL_A = 4;
	private final int ELEVATOR_CHANNEL_B = 5;
	//private final int ENCODER_COUNTS_PER_REVOLUTION; //find out val
	//private final double WHEEL_DIAMETER; //need val
	private double elevatorSpeed = 0.0;
	private Spark elevatorSpark = new Spark(7);
	//private Encoder1038 scoringElevatorPosition = new Encoder1038(ELEVATOR_CHANNEL_A, ELEVATOR_CHANNEL_B, false, ENCODER_COUNTS_PER_REVOLUTION, WHEEL_DIAMETER);
	private DigitalInput scoringElevatorZero;
	
	//methods
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
	
	public void moveToScale() {
		
	}
	
	public void moveToSwitch() {
		
	}
	
	public void moveToPortal() {
		
	}
	
	public void moveToFloor() {
		
	}
	
	public void moveUp() {
		
	}
	
	public void moveDown() {
		
	}
	
	public void encoderZero() {
		
	}

}
