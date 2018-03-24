package org.usfirst.frc.team1038.subsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
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
	private final double ACQ_UP_DOWN_SPEED = .65;
	private final double MIN_ACQ_SPEED = .6;
	private final double MAX_ACQ_SPEED = 1;
	private final static double P = .1;
	private final static double I = 0.001;
	private final static double D = 0.0001;
	private final int TOLERANCE = 5;
	public final static int UP_DOWN_MAX = 490;
	public final static int UP_DOWN_HALF = (UP_DOWN_MAX / 2) + 80;
	public final static int UP_DOWN_ZERO = 0;
	private double acqMotorSpeed = 0.8;
	private boolean armsOpen;
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
    		acqArmsUpDown.setInverted(true);
    		closeArms();
    		acqUpDownController.setInputRange(0, UP_DOWN_MAX);
    		acqUpDownController.setOutputRange(-ACQ_UP_DOWN_SPEED, ACQ_UP_DOWN_SPEED);
    		acqUpDownController.setAbsoluteTolerance(TOLERANCE);
    		acqUpDownController.setContinuous(false);
    		//SmartDashboard.putData("Acq PID Controller", acqUpDownController);
    		//SmartDashboard.putData("AcqUpDown", acqArmsUpDown);
    }
    
    public double getAcqSpeed()
    {
    		return acqMotorSpeed;
    }
    
    public double getAcqMotorPower()
    {
    		return acqMotors.get();
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
    		acqMotors.set(1);
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
    		setSetpoint(UP_DOWN_ZERO);
    }
    
    public void armsTo90() 
    {
    		enable();
    		setSetpoint(UP_DOWN_MAX);
    }
    
    public void armsTo45() 
    {
    		enable();
    		setSetpoint(UP_DOWN_HALF);
    }
    
    public void armsUpDownSetMotor(double power)
    {
    		acqArmsUpDown.set(power);
    }
    
    public void AcquisitionPeriodic()
    {
    		if (acqUpDownController.isEnabled())
    		{		
    			if (acqArmsUpDownEncoder.get() > -10 && acqArmsUpDownEncoder.get() < 10 && getSetpoint() == 0)
    			{
    				acqArmsUpDown.set(0);
    				disable();
    			}
    			else
    			{
    				double PIDValue = acqUpDownController.get();
    				usePIDOutput(PIDValue);
    			}
    		}
    }
    
	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return acqArmsUpDownEncoder.get();
	}

	@Override
	protected void usePIDOutput(double output) {
//		if ((acqArmsUpDownEncoder.get() <= 0 && output > 0) || (acqArmsUpDownEncoder.get() >= UP_DOWN_MAX && output < 0) || (acqArmsUpDown.get() > 0 && acqArmsUpDownEncoder.get() < UP_DOWN_MAX))
			acqArmsUpDown.set(output);
	}
    
    public void openArms()
    {
    		acqArmsOpenClose.set(DoubleSolenoid.Value.kForward);
    		armsOpen = true;
    }
    
    public void closeArms()
    {
    		acqArmsOpenClose.set(DoubleSolenoid.Value.kReverse);
    		armsOpen = false;
    }
    
    public int getUpDownEncoder()
    {
    		return acqArmsUpDownEncoder.get();
    }
    
    public void resetUpDownEncoder()
    {
    		acqArmsUpDownEncoder.reset();
    }
    
    public boolean areArmsOpen()
    {
    		return armsOpen;
    }

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void disable()
	{
		super.disable();
		acqArmsUpDown.set(0);
	}
}