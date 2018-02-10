package org.usfirst.frc.team1038.auton;

import org.usfirst.frc.team1038.subsystem.DriveTrain;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Vision extends Subsystem {
	//fields
	
	//IP 10.10.38.167
	public static DriveTrain robotDrive = DriveTrain.getInstance();
	NetworkTableInstance piTable = NetworkTableInstance.create();
	NetworkTable smartDash = piTable.getTable("SmartDashboard");
	NetworkTableEntry netFPS = smartDash.getEntry("FPS");   //May need slashes to denote hierarchy
	NetworkTableEntry netAngle = smartDash.getEntry("Angle");   //May need slashes to denote hierarchy
	
	//Constructor
	public Vision() {
		piTable.setNetworkIdentity("RoboRIO");
		piTable.startClient("10.10.38.167", 1735);
		
	}
	
	//methods
	public double getFPS() {
		return netFPS.getDouble(0);
	}
	
	public int getAngle() {
		return (int)Math.round(netAngle.getDouble(-1));
	}
	
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
