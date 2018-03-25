package org.usfirst.frc.team1038.subsystem;

import org.usfirst.frc.team1038.robot.Prox;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends PIDSubsystem {

	//fields
	private static Elevator elevator;
	private final int TOLERANCE = 5;
	public final static double P_UP = .0034;
	public final static double I_UP = .0001;
	public final static double D_UP = .000;
	public final static double P_DOWN = .00036;
	public final static double I_DOWN = .000;
	public final static double D_DOWN = .000;
	public final static int SCALE_HIGH = 670; //615 old
	public final static int SCALE_LOW = 530; //480 old
	public final static int SWITCH = 300; //220 old
	public final static int FLOOR = 0;
	private final int ELEVATOR_CHANNEL_A = 4;
	private final int ELEVATOR_CHANNEL_B = 5;
	private final int ELEVATOR_PROX_HIGH_PORT = 10;
	private final int ELEVATOR_PROX_LOW_PORT = 11;
	private final int ELEVATOR_MOTOR_PORT = 5;
	private Spark elevatorMotor = new Spark(ELEVATOR_MOTOR_PORT);
	private Encoder elevatorEncoder = new Encoder(ELEVATOR_CHANNEL_A, ELEVATOR_CHANNEL_B, false);
	private Prox highProx = new Prox(ELEVATOR_PROX_HIGH_PORT);
	private Prox lowProx = new Prox(ELEVATOR_PROX_LOW_PORT);
	public PIDController elevatorPID = getPIDController();
	
	public static Elevator getInstance() {
		if (elevator == null) {
			System.out.println("Creating a new Elevator");
			elevator = new Elevator();
		}
		return elevator;
	}
	
	private Elevator() {
		super(P_UP, I_UP, D_UP);
		elevatorPID.setAbsoluteTolerance(TOLERANCE);
		//elevatorPID.setOutputRange(-.2, 0 + ramp);
		elevatorPID.setOutputRange(-.1, .75);
		super.setInputRange(0, SCALE_HIGH);
		elevatorPID.setContinuous(false);
		//SmartDashboard.putData("Controls/Elevator PID Controller", elevatorPID);
		elevatorMotor.setInverted(true);
	}
	
	/**
	 * Get the state of the low prox
	 * @return is low prox active
	 */
	public boolean getLowProx() {
		return lowProx.get();
	}
	
	/**
	 * Get the state of the high prox
	 * @return is high prox active
	 */
	public boolean getHighProx() {
		return highProx.get();
	}
	
	/**
	 * Get the encoder counts from the elevator
	 * @return encoder counts of elevator
	 */
	public int getEncoderCount() {
		return elevatorEncoder.get();
	}
	
	/**
	 * Get the speed at which the elevator is moving
	 * @return speed the elevator is moving in counts / second
	 */
	public double getElevatorSpeed() {
		return elevatorEncoder.getRate();
	}
	
	/**
	 * Get the current motor output to the elevator
	 * @return the current motor outpur to the elevator
	 */
	public double getMotorOutput() {
		return elevatorMotor.get();
	}
	
	/**
	 * Call this method in robot period to control the elevator
	 */
	public void elevatorPeriodic() {
		if (elevatorPID.isEnabled()) {
			double PIDValue = elevatorPID.get();
			//System.out.println(elevatorPID.getP() + " " + elevatorPID.getI() + " " + elevatorPID.getD());
			usePIDOutput(PIDValue);
		}
	}
	
	/**
	 * Determine if the new setpoint for the elevator goes up or down
	 * @param newSetpoint The new setpoint for the elevator
	 * @return is new setpoint lower than current
	 */
	public boolean goingDown(int newSetpoint) {
		if (getSetpoint() > newSetpoint) {
			System.out.println("Going Down");
			return true;
		} else {
			System.out.println("Going Up");
			return false;
		}
	}
	
	/**
	 * Changes the setpoint of the elevator to the defined high scale value
	 */
	public void moveToScaleHigh() {
		enable();
		if (goingDown(SCALE_HIGH))
			elevatorPID.setPID(P_DOWN, I_DOWN, D_DOWN);
		else
			elevatorPID.setPID(P_UP, I_UP, D_UP);
		setSetpoint(SCALE_HIGH);
	}
	
	/**
	 * Changes the setpoint of the elevator to the defined low scale value
	 */
	public void moveToScaleLow() {
		enable();
		if (goingDown(SCALE_LOW))
			elevatorPID.setPID(P_DOWN, I_DOWN, D_DOWN);
		else
			elevatorPID.setPID(P_UP, I_UP, D_UP);
		setSetpoint(SCALE_LOW);
	}
	
	/**
	 * Changes the setpoint of the elevator to the defined switch value
	 */
	public void moveToSwitch() {
		enable();
		if (goingDown(SWITCH))
			elevatorPID.setPID(P_DOWN, I_DOWN, D_DOWN);
		else
			elevatorPID.setPID(P_UP, I_UP, D_UP);
		setSetpoint(SWITCH);
	}
	
	/**
	 * Changes the setpoint of the elevator to zero
	 */
	public void moveToFloor() {
		enable();
		if (goingDown(FLOOR))
			elevatorPID.setPID(P_DOWN, I_DOWN, D_DOWN);
		else
			elevatorPID.setPID(P_UP, I_UP, D_UP);
		setSetpoint(FLOOR);
	}
	
	/**
	 * Changes the setpoint of the elevator by plus or minus 2
	 * @param joystickValue Value used to change setpoint. If positive, setpoint += 2. If negative, setpoint -= 2
	 */
	public void move(double joystickValue) {
		
		if(getSetpoint() <= SCALE_HIGH && joystickValue > .09) {
			elevatorPID.setPID(P_UP, I_UP, D_UP);
			enable();
			setSetpoint(getSetpoint() + 2);
		} 
		else if(getSetpoint() > 0 && joystickValue < -.09) {
			elevatorPID.setPID(P_UP, I_UP, D_UP);
			enable();
			setSetpoint(getSetpoint() - 2);
		}
	}
	
	/**
	 * Reset the elevator encoder
	 */
	public void resetEncoder() {
		elevatorEncoder.reset();
	}

	@Override
	protected double returnPIDInput() {
		return elevatorEncoder.get();
	}

	@Override
	protected void usePIDOutput(double output) {
		if ((output < 0 && !lowProx.get()) || (output > 0 && !highProx.get())) {
			elevatorMotor.set(output);
			//elevatorPID.setOutputRange(-.2, output + ramp);
		}
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void disable() {
		super.disable();
		elevatorMotor.set(0);
	}
}