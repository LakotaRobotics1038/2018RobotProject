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
	public final static int SCALE_HIGH = 615;
	public final static int SCALE_LOW = 480;
	public final static int MIDDLE = 300;
	public final static int SWITCH = 220;
	private final int PORTAL = 0; // TODO find value
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
		super.setInputRange(0, 615);
		elevatorPID.setContinuous(false);
		//SmartDashboard.putData("Elevator PID Controller", elevatorPID);
		elevatorMotor.setInverted(true);
	}
	
	//methods	
	public boolean getLowProx() {
		return lowProx.get();
	}
	
	public boolean getHighProx() {
		return highProx.get();
	}
	
	public int getEncoderCount() {
		return elevatorEncoder.get();
	}
	
	public double getElevatorSpeed() {
		return elevatorEncoder.getRate();
	}
	
	public double getMotorOutput() {
		return elevatorMotor.get();
	}
	
	public void elevatorPeriodic() {
		if (elevatorPID.isEnabled())
		{
			double PIDValue = elevatorPID.get();
			//System.out.println(elevatorPID.getP() + " " + elevatorPID.getI() + " " + elevatorPID.getD());
			usePIDOutput(PIDValue);
		}
	}
	
	public boolean goingDown(int newSetpoint)
	{
		if (getSetpoint() > newSetpoint)
		{
			System.out.println("Going Down");
			return true;
		}
		else
		{
			System.out.println("Going Up");
			return false;
		}
	}
	
	public void moveToScaleHigh() {
		enable();
		if (goingDown(SCALE_HIGH))
			elevatorPID.setPID(P_DOWN, I_DOWN, D_DOWN);
		else
			elevatorPID.setPID(P_UP, I_UP, D_UP);
		setSetpoint(SCALE_HIGH);
	}
	

	public void moveToScaleLow() {
		enable();
		if (goingDown(SCALE_LOW))
			elevatorPID.setPID(P_DOWN, I_DOWN, D_DOWN);
		else
			elevatorPID.setPID(P_UP, I_UP, D_UP);
		setSetpoint(SCALE_LOW);
	}
	
	public void moveToSwitch() {
		enable();
		if (goingDown(SWITCH))
			elevatorPID.setPID(P_DOWN, I_DOWN, D_DOWN);
		else
			elevatorPID.setPID(P_UP, I_UP, D_UP);
		setSetpoint(SWITCH);
	}
	
	public void moveToPortal() {
		enable();
		if (goingDown(PORTAL))
			elevatorPID.setPID(P_DOWN, I_DOWN, D_DOWN);
		else
			elevatorPID.setPID(P_UP, I_UP, D_UP);
		setSetpoint(PORTAL);
	}
	
	public void moveToFloor() {
		enable();
		if (goingDown(FLOOR))
			elevatorPID.setPID(P_DOWN, I_DOWN, D_DOWN);
		else
			elevatorPID.setPID(P_UP, I_UP, D_UP);
		setSetpoint(FLOOR);
	}
	
	public void move(double joystickValue) {
		
		if(getSetpoint() <= SCALE_HIGH && joystickValue > .09)
		{
			elevatorPID.setPID(P_UP, I_UP, D_UP);
			enable();
			setSetpoint(getSetpoint() + 2);
		}
		else if(getSetpoint() > 0 && joystickValue < -.09)
		{
			elevatorPID.setPID(P_UP, I_UP, D_UP);
			enable();
			setSetpoint(getSetpoint() - 2);
		}
	}
	
	public void resetEncoder() {
		elevatorEncoder.reset();
	}

	@Override
	protected double returnPIDInput() {
		return elevatorEncoder.get();
	}

	@Override
	protected void usePIDOutput(double output) {
		if ((output < 0 && !lowProx.get()) || (output > 0 && !highProx.get()))
		{
			elevatorMotor.set(output);
			//elevatorPID.setOutputRange(-.2, output + ramp);
		}
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void disable()
	{
		super.disable();
		elevatorMotor.set(0);
	}
}