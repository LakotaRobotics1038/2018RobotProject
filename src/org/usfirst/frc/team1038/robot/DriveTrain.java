package org.usfirst.frc.team1038.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain extends Subsystem {
	//Fields
//	private final static double P = 0.000;
//	private final static double I = 0.000;
//	private final static double D = 0.000;
//	private final double TOLERANCE = 2.0;
	private final int LEFT_ENCODER_CHANNEL_A = 0;
	private final int RIGHT_ENCODER_CHANNEL_A = 2;
	private final int LEFT_ENCODER_CHANNEL_B = 1;
	private final int RIGHT_ENCODER_CHANNEL_B = 3;
	private final int ENCODER_COUNTS_PER_REV = 220;
	private final double WHEEL_DIAMETER = 6;
	private final static int LEFT_DRIVE_PORT = 0;
	private final static int RIGHT_DRIVE_PORT = 1;
	private static Spark leftDrive = new Spark(LEFT_DRIVE_PORT);
	private static Spark rightDrive = new Spark(RIGHT_DRIVE_PORT);
	private DoubleSolenoid shifter = new DoubleSolenoid(0, 1);
	private DoubleSolenoid PTO = new DoubleSolenoid(2, 3);
	private Encoder1038 leftDriveEncoder = new Encoder1038(LEFT_ENCODER_CHANNEL_A, LEFT_ENCODER_CHANNEL_B, false, ENCODER_COUNTS_PER_REV, WHEEL_DIAMETER);
	private Encoder1038 rightDriveEncoder = new Encoder1038(RIGHT_ENCODER_CHANNEL_A, RIGHT_ENCODER_CHANNEL_B, true, ENCODER_COUNTS_PER_REV, WHEEL_DIAMETER);
	private boolean isHighGear = false;
	private boolean PTOisEngaged = false;
	private DifferentialDrive differentialDrive;
	private static DriveTrain driveTrain = new DriveTrain();
	
	public static DriveTrain getInstance() {
		if (driveTrain == null) {
			System.out.println("Creating a new driveTrain");
			driveTrain = new DriveTrain();
		}
		return driveTrain;
	}
	
	//Constructor
	public DriveTrain() {
		//super(P, I, D);
		//setAbsoluteTolerance(TOLERANCE);
		leftDrive.setInverted(true);
		rightDrive.setInverted(true);
		differentialDrive = new DifferentialDrive(leftDrive, rightDrive);
	}
	
	//Getters
	public int getLeftDriveEncoderCount() {
		return leftDriveEncoder.getCount();
	}
	
	public int getRightDriveEncoderCount() {
		return rightDriveEncoder.getCount();
	}
	 
	public double getLeftDriveEncoderDistance() {
		return leftDriveEncoder.getDistance();
	}
	
	public double getRightDriveEncoderDistance() {
		return rightDriveEncoder.getDistance();
	}
		
	//Methods
	/**
	 * Drive robot using tank drive (left stick controls left side, right stick controls right side)
	 * 
	 * @param inputL Left stick input (range -1 to 1)
	 * @param inputR Right stick input (range -1 to 1)
	 */

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	public void resetEncoders() {
		leftDriveEncoder.resetEncoder();
		rightDriveEncoder.resetEncoder();
	}
	
	public void tankDrive(double inputL, double inputR) {
		differentialDrive.tankDrive(inputL, inputR, true);
	}
	
	/**
	 * Drive robot using single stick 
	 * 
	 * @param speed Speed of robot (range -1 to 1)
	 * @param curve Wanted turn value of robot
	 */
	public void singleArcadeDrive(double speed, double curve) {
		differentialDrive.arcadeDrive(speed, curve, true);
	}
	
	/**
	 * Drive robot using two sticks (one controlling forward and backward, the other controlling left and right)
	 * 
	 * @param inputFB Forward/Backward value (range -1 to 1)
	 * @param inputLR Left/Right value (range -1 to 1)
	 */
	public void dualArcadeDrive(double yaxis, double xaxis) {
		differentialDrive.arcadeDrive(yaxis, xaxis, true);
	}
	
	/**
	 * Toggle the PTO between on and off
	 */
	public void togglePTO() {
		if(PTOisEngaged == true) {
			PTOisEngaged = false;
		}else {
			PTOisEngaged = true;
		}
	}
	
	/**
	 * Toggle PTO to on
	 */
	public void PTOon() {
		PTOisEngaged = true;
		PTO.set(DoubleSolenoid.Value.kReverse);
	}
	
	/**
	 * Toggle PTO to off
	 */
	public void PTOoff() {
		PTOisEngaged = false;
		PTO.set(DoubleSolenoid.Value.kForward);
	}
	
	/**
	 * Toggle the Gear between high and low
	 */
	public void toggleGear() {
		if(isHighGear == true) {
			isHighGear = false;
		}else {
			isHighGear = true;
		}
	}
	
	/**
	 * Toggle gear to high
	 */
	public void highGear() {
		isHighGear = true;
		shifter.set(DoubleSolenoid.Value.kForward);
	}
	
	/**
	 * Toggle gear to low
	 */
	public void lowGear() {
		isHighGear = false;
		shifter.set(DoubleSolenoid.Value.kReverse);
	}
	
	/**
	 * Returns if the gear is set to high
	 * 
	 * @return False if in low gear, true if in high gear
	 */
	public boolean isHighGear() {
		return isHighGear;
	}
	
	/**
	 * Returns if the PTO is engaged
	 * 
	 * @return False if disengaged, true if engaged
	 */
	public boolean PTOisEngaged() {
		return PTOisEngaged;
	}
	
	/**
	 * Drive robot based on a speed and rotation given
	 * 
	 * @param moveVal Speed (range -1 to 1)
	 * @param rotateVal Rotation (range -1 to 1)
	 */
	public void drive(double moveVal, double rotateVal) {
		differentialDrive.curvatureDrive(moveVal, rotateVal, false);
	}

//	@Override
//	protected double returnPIDInput() {
//		// TODO Auto-generated method stub
//		return leftDriveEncoder.getDistance();
//	}
//
//	@Override
//	protected void usePIDOutput(double output) {
//		// TODO Auto-generated method stub
//		differentialDrive.curvatureDrive(output, 0, false);
//		
//	}
}
