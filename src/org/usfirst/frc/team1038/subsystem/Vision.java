package org.usfirst.frc.team1038.subsystem;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Vision extends Subsystem {
	
	//IP 10.10.38.200
	private DriveTrain robotDrive = DriveTrain.getInstance();
	private NetworkTableInstance piTable = NetworkTableInstance.create();
	private NetworkTable smartDash = piTable.getTable("SmartDashboard");
	private NetworkTableEntry netFPS = smartDash.getEntry("FPS");   //May need slashes to denote hierarchy
	private NetworkTableEntry netAngle = smartDash.getEntry("Angle");   //May need slashes to denote hierarchy
	private static Vision vision;
	
	public static Vision getInstance() {
		if (vision == null) {
			System.out.println("Creating new Vison");
			vision = new Vision();
		}
		return vision;
	}
	
	private Vision() {
		piTable.setNetworkIdentity("RoboRIO");
		piTable.startClient("raspberrypi.local", 1735);	
	}
	
	/**
	 * Get the FPS of the camera
	 * @return the FPS of the camera
	 */
	public double getFPS() {
		return netFPS.getDouble(0);
	}
	
	/**
	 * Get the angle of the cube in the camera frame
	 * @return the angle of the cube in the camera frame
	 */
	public int getAngle() {
		return (int)Math.round(netAngle.getDouble(-1));
	}
	
	/**
	 * Turn the robot to the camera angle
	 */
	public void turnToAngle() {
		while(this.getAngle() < -1 || this.getAngle() > 1) {
			if(this.getAngle() > 1) {
				robotDrive.dualArcadeDrive(0, -0.5);
				System.out.println("turning left");
			}else if(this.getAngle() < -1) {
				robotDrive.dualArcadeDrive(0, 0.5);
				System.out.println("turning right");
			}
		}
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
}
