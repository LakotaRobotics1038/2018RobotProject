package org.usfirst.frc.team1038.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drive extends DifferentialDrive{
	//Fields
	
	private static Spark leftDrive = new Spark(0);
	private static Spark rightDrive = new Spark(1);
	private double solenoidShifter;
	private double solenoidPTO;
	private Encoder leftDriveEncoder;
	private Encoder rightDriveEncoder;
	private boolean isHighGear;
	private boolean PTOisEngaged = false;
	
	
	//Constructor
	public Drive() {
		super(leftDrive, rightDrive);
	}
	
	//Getters and setters- for fields we arent using
	public Encoder getLeftDriveEncoder() {
		return leftDriveEncoder;
	}
	
	public Encoder getRightDriveEncoder() {
		return rightDriveEncoder;
	}
	
	public double getSolenoidShifter() {
		return solenoidShifter;
	}
	
	public double getSolenoidPTO() {
		return solenoidPTO;
	}
	
	//Methods
	/**
	 * Drive robot using tank drive (left stick controls left side, right stick controllsl right side)
	 * 
	 * @param inputL Left stick input (range -1 to 1)
	 * @param inputR Right stick input (range -1 to 1)
	 */
	public void tankDrive(double inputL, double inputR) {
		super.tankDrive(inputL, inputR, true);
	}
	
	/**
	 * Drive robot using single stick 
	 * 
	 * @param speed Speed of robot (range -1 to 1)
	 * @param curve Wanted turn value of robot
	 */
	public void singleArcadeDrive(double speed, double curve) {
		super.arcadeDrive(speed, curve, true);
	}
	
	/**
	 * Drive robot using two sticks (one controlling forward and backward, the other controlling left and right)
	 * 
	 * @param inputFB Forward/Backward value (range -1 to 1)
	 * @param inputLR Left/Right value (range -1 to 1)
	 */
	public void dualArcadeDrive(double inputFB, double inputLR) {
		super.arcadeDrive(inputFB, inputLR, true);
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
	}
	
	/**
	 * Toggle PTO to off
	 */
	public void PTOoff() {
		PTOisEngaged = false;
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
	}
	
	/**
	 * Toggle gear to low
	 */
	public void lowGear() {
		isHighGear = false;
	}
	
	/**
	 * Returns if the gear is set to high
	 * 
	 * @return False if in low gear, true if in high gear
	 */
	public boolean isHighGear() {
		boolean ret = false;
		if(isHighGear) {
			ret = true;
		}
		return ret;
	}
	
	/**
	 * Returns if the PTO is engaged
	 * 
	 * @return False if disengaged, true if engaged
	 */
	public boolean PTOisEngaged() {
		boolean ret = false;
		if(PTOisEngaged) {
			ret = true;
		}
		return ret;
	}
	
	/**
	 * Drive robot based on a speed and rotation given
	 * 
	 * @param moveVal Speed (range -1 to 1)
	 * @param rotateVal Rotation (range -1 to 1)
	 */
	public void drive(double moveVal, double rotateVal) {
		super.curvatureDrive(moveVal, rotateVal, false);
	}
}
