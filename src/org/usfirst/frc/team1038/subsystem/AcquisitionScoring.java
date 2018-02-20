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
	private final double ACQ_UP_DOWN_SPEED = .5;
	private final double MIN_ACQ_SPEED = .6;
	private final double MAX_ACQ_SPEED = 1;
	private double acqMotorSpeed = 0.4;
	private Spark leftAcqMotor = new Spark(LEFT_ACQ_MOTOR_PORT);
    private Spark rightAcqMotor = new Spark(RIGHT_ACQ_MOTOR_PORT);
    private SpeedControllerGroup acqMotors = new SpeedControllerGroup(leftAcqMotor, rightAcqMotor);
    private DoubleSolenoid acqArmsOpenClose = new DoubleSolenoid(ACQ_ARMS_OPEN, ACQ_ARMS_CLOSE);
    private Spark acqArmsUpDown = new Spark(ACQ_ARMS_UP_DOWN_PORT);
    private Encoder acqArmsUpDownEncoder = new Encoder(ACQ_ARMS_ENCODER_A_PORT, ACQ_ARMS_ENCODER_B_PORT);
    private static AcquisitionScoring acqSco;
    
    public static AcquisitionScoring getInstance() {
		if (acqSco == null) {
			System.out.println("Creating a new Acquisition/Scoring");
			acqSco = new AcquisitionScoring();
		}
		return acqSco;
	}
    
    private AcquisitionScoring()
    {
    		rightAcqMotor.setInverted(true);
    }
    
    public double getAcqSpeed()
    {
    		return acqMotorSpeed;
    }
	
    public void setAcqSpeed(boolean up)
    {
    		if (acqMotorSpeed < MAX_ACQ_SPEED && up)
    			acqMotorSpeed += .2;
    		else if (acqMotorSpeed > MIN_ACQ_SPEED && !up)
    			acqMotorSpeed -= .2;
    }
    
    public void aquire() 
    {
    		acqMotors.set(acqMotorSpeed);
    }
    
    public void dispose() 
    {
    		acqMotors.set(-acqMotorSpeed);
    }
    
    public void stop()
    {
    		acqMotors.set(0);
    }
    
    public void raiseArms() 
    {
    		if (acqArmsUpDownEncoder.get() < 0 /*TODO: Find value*/)
    			acqArmsUpDown.set(ACQ_UP_DOWN_SPEED);
    }
    
    public void lowerArms() 
    {
    		if (acqArmsUpDownEncoder.get() > 0)
    			acqArmsUpDown.set(-ACQ_UP_DOWN_SPEED);
    }
    
    public void openArms()
    {
    		acqArmsOpenClose.set(DoubleSolenoid.Value.kForward);
    }
    
    public void closeArms()
    {
    		acqArmsOpenClose.set(DoubleSolenoid.Value.kReverse);
    }
}