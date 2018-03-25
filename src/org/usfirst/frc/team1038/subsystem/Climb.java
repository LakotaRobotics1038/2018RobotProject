package org.usfirst.frc.team1038.subsystem;

import org.usfirst.frc.team1038.robot.Prox;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Climb extends Subsystem {

	//variables
	private final int ARM_MOTOR_PORT = 2;
	private final int ARM_ENCODER_PORT_A = 8;
	private final int ARM_ENCODER_PORT_B = 9;
	private final int ACQ_ARMS_DEADBAND = 10;
	private Spark armMotor = new Spark(ARM_MOTOR_PORT);
	private Encoder armEncoder = new Encoder(ARM_ENCODER_PORT_A, ARM_ENCODER_PORT_B);
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
	
	/**
	 * Moves the climb arm
	 * @param joystickPower power at which to move the climb arm. thic value is cut in half.
	 */
	public void move (double joystickPower) {
		if ((armEncoder.get() <= 8 && joystickPower > .1) || (joystickPower > .1 || joystickPower < -.1)) {
			if (armEncoder.get() > 0) {
				if (AcquisitionScoring.getInstance().getUpDownEncoder() > ACQ_ARMS_DEADBAND)
					AcquisitionScoring.getInstance().armsToZero();
				if (AcquisitionScoring.getInstance().areArmsOpen())
					AcquisitionScoring.getInstance().closeArms();
			}
			armMotor.set(joystickPower / 2);
		}
		else
			armMotor.set(0);
	}
	
	/**
	 * Reset the arm encoder
	 */
	public void resetEncoder() {
		armEncoder.reset();
	}
	
	/**
	 * Get the state of the arm prox
	 * @return is the arm in front of the prox
	 */
	public boolean getProx() {
		return armProx.get();
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
