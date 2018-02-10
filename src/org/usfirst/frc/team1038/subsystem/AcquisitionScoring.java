package org.usfirst.frc.team1038.subsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class AcquisitionScoring {

	private final int LEFT_ACQ_MOTOR_PORT = 3;
	private final int RIGHT_ACQ_MOTOR_PORT = 4;
	private final int ACQ_ARMS_OPEN = 4;
	private final int ACQ_ARMS_CLOSE = 5;
	private final int ACQ_ARMS_UP_DOWN_PORT = 6;
	private final int ACQ_ARMS_ENCODER_A_PORT = 6;
	private final int ACQ_ARMS_ENCODER_B_PORT = 7;
	private double acqMotorSpeed = 0.0;
	private Spark leftAcqMotor = new Spark(LEFT_ACQ_MOTOR_PORT);
    private Spark rightAcqMotor = new Spark(RIGHT_ACQ_MOTOR_PORT);
    private SpeedControllerGroup acqMotors = new SpeedControllerGroup(leftAcqMotor, rightAcqMotor);
    private DoubleSolenoid acqArmsHoriz = new DoubleSolenoid(ACQ_ARMS_OPEN, ACQ_ARMS_CLOSE);
    private Spark acqArmsUpDown = new Spark(ACQ_ARMS_UP_DOWN_PORT);
    private Encoder acqArmsUpDownEncoder = new Encoder(ACQ_ARMS_ENCODER_A_PORT, ACQ_ARMS_ENCODER_B_PORT);
	
    public void setAcqSpeed(double speed)
    {
    		acqMotorSpeed = speed;
    }
    
    public void aquire() 
    {
    		acqMotors.set(acqMotorSpeed);
    }
    
    public void eject() 
    {
    		acqMotors.set(-acqMotorSpeed);
    }
    
    public void raiseArms() 
    {
    		acqArmsHoriz.set(DoubleSolenoid.Value.kForward);
    }
    
    public void lowerArms() 
    {
    		acqArmsHoriz.set(DoubleSolenoid.Value.kReverse);
    }
    
    public void openArms()
    {

    }
    
    public void closeArms()
    {

    }
}