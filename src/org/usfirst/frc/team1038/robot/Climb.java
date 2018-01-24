package org.usfirst.frc.team1038.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;


public class Climb extends Subsystem {
										//I NEED A DISTANCE FOR IT TO TRAVEL
	//variables
	private final int ARM_MOTOR_PORT = 2;
	private final int ARM_ENCODER_PORT_A = 7;
	private final int ARM_ENCODER_PORT_B = 8;
	private Spark armMotor = new Spark(ARM_MOTOR_PORT);
	private Encoder armEncoder = new Encoder(ARM_ENCODER_PORT_A, ARM_ENCODER_PORT_B);
	private double encoderDistance = 0;
	private final double FINAL_DISTANCE = 3;
	private final double RAISE_SPEED = 0.4;
	private boolean climbing = false;
	private boolean lowering = false;
	private double manualClimbingSpeed = 0;
	
	public Climb()
	{
		
	}
	
	//methods
	public boolean autoArmRaise()
	{
		//pushes out telescoping arm
//		encoderDistance = armEncoder.getDistance();
		armMotor.set(RAISE_SPEED);
		if(encoderDistance < FINAL_DISTANCE/*set to distance in inches from rest to bar*/)
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
	
	public void  manualArmRaise(double joystickPower)
	{
		//move telescopic arm manually
		encoderDistance = armEncoder.getDistance();
		joystickPower = manualClimbingSpeed;
		if(encoderDistance < FINAL_DISTANCE)
		{
		armMotor.set(manualClimbingSpeed);
		}
		else 
		{
			armMotor.set(0);
		}
	}
	
	public boolean armLower()
	{
		// lowers telescoping arm
		encoderDistance =  armEncoder.getDistance();
		armMotor.set(RAISE_SPEED*-1);
		if(encoderDistance < FINAL_DISTANCE/*set to distance in inches from rest to bar*/)
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
