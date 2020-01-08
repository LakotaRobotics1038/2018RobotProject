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
	private final int ACQ_ARMS_OPEN = 0;
	private final int ACQ_ARMS_CLOSE = 1;
	private final int ACQ_ARMS_UP_DOWN_PORT = 6;
	private final int ACQ_ARMS_ENCODER_A_PORT = 6;
	private final int ACQ_ARMS_ENCODER_B_PORT = 7;
	private final double ACQ_UP_DOWN_SPEED = .65;
	private final double MIN_ACQ_SPEED = .6;
	private final double MAX_ACQ_SPEED = 1;
	public enum SpeedModes { Up, Down };
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
    
    private AcquisitionScoring() {
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
    
    /**
     * Get the speed setpoint for the acquisition wheels
     * @return the speed setpoint for the acquisition wheels
     */
    public double getAcqSpeed() {
    		return acqMotorSpeed;
    }
    
    /**
     * Get the current power setting for the acquisition motors
     * @return the current power setting for the acquisition motors
     */
    public double getAcqMotorPower() {
    		return acqMotors.get();
    }
	
    /**
     * Change the speed setpoint for disposing a cube
     * @param mode direction to move value (plus or minus .2) (postcondition bounded between .4 and 1)
     */
    public void setAcqSpeed(SpeedModes mode) {
    		if (acqMotorSpeed < MAX_ACQ_SPEED && mode == SpeedModes.Up)
    			acqMotorSpeed += .2;
    		else if (acqMotorSpeed > MIN_ACQ_SPEED && mode == SpeedModes.Down)
    			acqMotorSpeed -= .2;
    }
    
    /**
     * Set the speed setpoint for disposing a cube
     * @param speed new value for setpoint (precondition bounded between .4 and 1)
     */
    public void setAcqSpeed(double speed) {
    		acqMotorSpeed = speed;
    }
    
    /**
     * Runs acquisition motors at full power inwards
     */
    public void aquire() {
    		acqMotors.set(1);
    }
    
    /**
     * Runs acquisition motors inwards at the current setpoint
     */
    public void dispose() {
    		acqMotors.set(-acqMotorSpeed);
    }
    
    /**
     * sets the acquisition motors to zero
     */
    public void stop() {
    		acqMotors.set(0);
    }
    
    /**
     * change the angle setpoint to zero
     */
    public void armsToZero() {
    		enable();
    		setSetpoint(UP_DOWN_ZERO);
    }
    
    /**
     * change the angle setpoint to 90 degrees
     */
    public void armsTo90() {
    		enable();
    		setSetpoint(UP_DOWN_MAX);
    }
    
    /**
     * change the angle setpoint to 60 degrees
     */
    public void armsTo60() {
    		enable();
    		setSetpoint(UP_DOWN_HALF);
    }
    
    /**
     * set the motor power for the acquisition angle
     * @param power to set the motor to
     */
    public void armsUpDownSetMotor(double power) {
    		acqArmsUpDown.set(power);
    }
    
    /**
     * Call this method in robot periodic to control the acquision
     */
    public void AcquisitionPeriodic() {
    		if (acqUpDownController.isEnabled()) {		
    			if (acqArmsUpDownEncoder.get() > -10 && acqArmsUpDownEncoder.get() < 10 && getSetpoint() == 0) {
    				acqArmsUpDown.set(0);
    				disable();
    			} else {
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
    
	/**
	 * open the acquisition arms
	 */
    public void openArms() {
    		acqArmsOpenClose.set(DoubleSolenoid.Value.kForward);
    		armsOpen = true;
    }
    
    /**
     * close the acquisition arms
     */
    public void closeArms() {
    		acqArmsOpenClose.set(DoubleSolenoid.Value.kReverse);
    		armsOpen = false;
    }
    
    /**
     * Get the value of the acquisition angle encoder
     * @return the value of the acquisition angle encoder
     */
    public int getUpDownEncoder() {
    		return acqArmsUpDownEncoder.get();
    }
    
    /**
     * reset the encoder for the acquisition angle
     */
    public void resetUpDownEncoder() {
    		acqArmsUpDownEncoder.reset();
    }
    
    /**
     * Determine if the acquisition arms are open
     * @return are the acquisition arms open
     */
    public boolean areArmsOpen() {
    		return armsOpen;
    }

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void disable() {
		super.disable();
		acqArmsUpDown.set(0);
	}
}