package org.usfirst.frc.team1038.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain extends Subsystem{
	//Fields
	
	
	private int leftEncoderChannelA = 0;
	private int rightEncoderChannelA = 2;
	private int leftEncoderChannelB = 1;
	private int rightEncoderChannelB = 3;
	private int encoderCountsPerRev = 220;
	private double wheelDiameter = 6;
	private static int leftDrivePWMPort = 0;
	private static int rightDrivePWMPort = 1;
	private static Spark leftDrive = new Spark(leftDrivePWMPort);
	private static Spark rightDrive = new Spark(rightDrivePWMPort);
	private DoubleSolenoid shifter;/* = new DoubleSolenoid(0, 1);*/
	private DoubleSolenoid PTO; /* = new DoubleSolenoid(2, 3);*/
	private Encoder1038 leftDriveEncoder = new Encoder1038(leftEncoderChannelA, leftEncoderChannelB, false, encoderCountsPerRev, wheelDiameter);
	private Encoder1038 rightDriveEncoder = new Encoder1038(rightEncoderChannelA, rightEncoderChannelB, true, encoderCountsPerRev, wheelDiameter);
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
	public void dualArcadeDrive(double inputFB, double inputLR) {
		differentialDrive.arcadeDrive(inputFB, inputLR, true);
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
		PTO.set(DoubleSolenoid.Value.kForward);
	}
	
	/**
	 * Toggle PTO to off
	 */
	public void PTOoff() {
		PTOisEngaged = false;
		PTO.set(DoubleSolenoid.Value.kReverse);
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
		//shifter.set(DoubleSolenoid.Value.kReverse);
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
}
