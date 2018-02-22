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
	private final static double P = 4;
	private final static double I = .0001;
	private final static double D = .001;
	private final static int SCALE_HIGH = 615;
	private final int SCALE_LOW = 480;
	private final int MIDDLE = 300;
	private final int SWITCH = 10;
	private final int PORTAL = 0; // TODO find value
	private final int FLOOR = 0;
	private final int ELEVATOR_CHANNEL_A = 4;
	private final int ELEVATOR_CHANNEL_B = 5;
	private final int ELEVATOR_PROX_HIGH_PORT = 10;
	private final int ELEVATOR_PROX_LOW_PORT = 11;
	private final int ELEVATOR_MOTOR_PORT = 5;
	private Spark elevatorMotor = new Spark(ELEVATOR_MOTOR_PORT);
	private Encoder elevatorEncoder = new Encoder(ELEVATOR_CHANNEL_A, ELEVATOR_CHANNEL_B, false);
	private Prox highProx = new Prox(ELEVATOR_PROX_HIGH_PORT);
	private Prox lowProx = new Prox(ELEVATOR_PROX_LOW_PORT);
	private PIDController elevatorPID = getPIDController();
	private double ramp = .2;
	
	public static Elevator getInstance() {
		if (elevator == null) {
			System.out.println("Creating a new Elevator");
			elevator = new Elevator();
		}
		return elevator;
	}
	
	private Elevator() {
		super(P / SCALE_HIGH, I /*/ SCALE_HIGH*/, D /*/ SCALE_HIGH*/);
		elevatorPID.setAbsoluteTolerance(TOLERANCE);
		elevatorPID.setOutputRange(-.2, 0 + ramp);
		super.setInputRange(0, 615);
		elevatorPID.setContinuous(false);
		SmartDashboard.putData("Elevator PID Controller", elevatorPID);
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
	public double getMotorOutput() {
		return elevatorMotor.get();
	}
	
	public void elevatorPeriodic() {
		if (elevatorPID.isEnabled())
		{
			double PIDValue = elevatorPID.get();
			usePIDOutput(PIDValue);
		}
	}
	
	public void moveToScaleHigh() {
		enable();
		setSetpoint(SCALE_HIGH);
	}
	

	public void moveToScaleLow() {
		enable();
		setSetpoint(MIDDLE);
	}
	
	public void moveToSwitch() {
		enable();
		setSetpoint(SWITCH);
	}
	
	public void moveToPortal() {
		enable();
		setSetpoint(PORTAL);
	}
	
	public void moveToFloor() {
		enable();
		setSetpoint(FLOOR);
	}
	
	public void move(double joystickValue) {
		if(getSetpoint() <= SCALE_HIGH && joystickValue > .09)
		{
			enable();
			setSetpoint(getSetpoint() + 2);
		}
		else if(getSetpoint() > 0 && joystickValue < -.09)
		{
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
			elevatorPID.setOutputRange(-.2, output + ramp);
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