package org.usfirst.frc.team1038.subsystem;

import org.usfirst.frc.team1038.robot.Prox;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;


public class Climb extends Subsystem {
										//I NEED A DISTANCE FOR IT TO TRAVEL
	//variables
	private final int ARM_MOTOR_PORT = 2;
	private final int ARM_ENCODER_PORT_A = 8;
	private final int ARM_ENCODER_PORT_B = 9;
	private Spark armMotor = new Spark(ARM_MOTOR_PORT);
	private Encoder armEncoder = new Encoder(ARM_ENCODER_PORT_A, ARM_ENCODER_PORT_B);
	private final double FINAL_DISTANCE = 0;
	private final double RAISE_SPEED = 0.4;
	private boolean climbing = false;
	private boolean lowering = false;
	private final int ARM_PROX_PORT = 12;
	private Prox armProx = new Prox(ARM_PROX_PORT);
	private static Climb climb;
	
	public static Climb getInstance() {
		if (climb == null) {
			System.out.println("Creating a new Climb");
			climb = new Climb();
		}
		return climb;
	}
	
	private Climb() {
		
	}
	
	//methods
	public boolean autoArmRaise()
	{
		//pushes out telescoping arm
//		encoderDistance = armEncoder.getDistance();
		armMotor.set(RAISE_SPEED);
		if(armEncoder.get() < FINAL_DISTANCE/*set to distance in inches from rest to bar*/)
		{
			climbing = true;
		}
		else
		{
		climbing = false;
		armMotor.set(0);
		}
		return climbing;
	}
	
	public void move (double joystickPower)
	{
		if ((armEncoder.get() <= 0 && joystickPower > 0) || armEncoder.get() > 0)
			armMotor.set(joystickPower);
	}
	
	public void resetEncoder()
	{
		armEncoder.reset();
	}
	
	public boolean getProx()
	{
		return armProx.get();
	}
	
	public boolean armLower()
	{
		// lowers telescoping arm
		armMotor.set(RAISE_SPEED*-1);
		if(armEncoder.get() < FINAL_DISTANCE) //TODO: Find value
		{
			lowering = true;
		}
		else
		{
			lowering = false;
			armMotor.set(0);
		}
		return lowering;
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
