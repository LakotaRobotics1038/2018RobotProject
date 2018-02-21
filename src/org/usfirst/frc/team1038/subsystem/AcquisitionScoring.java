package org.usfirst.frc.team1038.subsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class AcquisitionScoring extends PIDSubsystem {

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
	private final static double P = 0.0;
	private final static double I = 0.0;
	private final static double D = 0.0;
	private final int UP_DOWN_MAX = 470;
	private double acqMotorSpeed = 0.4;
	private Spark leftAcqMotor = new Spark(LEFT_ACQ_MOTOR_PORT);
    private Spark rightAcqMotor = new Spark(RIGHT_ACQ_MOTOR_PORT);
    private SpeedControllerGroup acqMotors = new SpeedControllerGroup(leftAcqMotor, rightAcqMotor);
    private DoubleSolenoid acqArmsOpenClose = new DoubleSolenoid(ACQ_ARMS_OPEN, ACQ_ARMS_CLOSE);
    private Spark acqArmsUpDown = new Spark(ACQ_ARMS_UP_DOWN_PORT);
    private Encoder acqArmsUpDownEncoder = new Encoder(ACQ_ARMS_ENCODER_A_PORT, ACQ_ARMS_ENCODER_B_PORT, true);
    private static AcquisitionScoring acqSco;
    private PIDController acqUpDownController = getPIDController();
    
    public static AcquisitionScoring getInstance() {
		if (acqSco == null) {
			System.out.println("Creating a new Acquisition/Scoring");
			acqSco = new AcquisitionScoring();
		}
		return acqSco;
	}
    
    private AcquisitionScoring()
    {
		super (P, I, D);
    		leftAcqMotor.setInverted(true);
    		//acqArmsOpenClose.set(DoubleSolenoid.Value.kReverse);
    		closeArms();
    		acqUpDownController.setInputRange(0, UP_DOWN_MAX);
    		acqUpDownController.setOutputRange(-ACQ_UP_DOWN_SPEED, ACQ_UP_DOWN_SPEED);
    		acqUpDownController.setContinuous(false);
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
    
    public void armsToZero() 
    {
    		enable();
    		setSetpoint(0);
    }
    
    public void armsTo90() 
    {
    		enable();
    		setSetpoint(UP_DOWN_MAX);
    }
    
    public void armsTo45() 
    {
    		enable();
    		setSetpoint(UP_DOWN_MAX / 2);
    }
    
    public void AcquisitionPeriodic()
    {
    		double PIDValue = acqUpDownController.get();
    		usePIDOutput(PIDValue);
    		System.out.println(acqArmsUpDownEncoder.get());
    }
    
	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return acqArmsUpDownEncoder.get();
	}

	@Override
	protected void usePIDOutput(double output) {
		if (acqArmsUpDownEncoder.get() < 0 && output > 0 || acqArmsUpDownEncoder.get() > UP_DOWN_MAX && output < 0)
		acqArmsUpDown.set(output);
	}
    
    public void openArms()
    {
    		acqArmsOpenClose.set(DoubleSolenoid.Value.kForward);
    }
    
    public void closeArms()
    {
    		acqArmsOpenClose.set(DoubleSolenoid.Value.kReverse);
    }
    
    public int getUpDownEncoder()
    {
    		return acqArmsUpDownEncoder.get();
    }

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}