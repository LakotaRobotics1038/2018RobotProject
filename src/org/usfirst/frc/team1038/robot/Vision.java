package org.usfirst.frc.team1038.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Vision extends Subsystem{
	//fields
	
	//IP 10.10.38.167
	public static DriveTrain robotDrive = DriveTrain.getInstance();
	NetworkTableInstance targetSmartDash = NetworkTableInstance.getDefault();
	targetSmartDash.setNetworkIdentity("");
	NetworkTable smartDash = targetSmartDash.getTable("SmartDashBoard");   //May need slashes to denote hierarchy
	NetworkTableEntry targetX = smartDash.getEntry("FPS");   //May need slashes to denote hierarchy
	NetworkTableEntry targetY = smartDash.getEntry("Angle");   //May need slashes to denote hierarchy
	double XVal = targetX.getDouble(-1.0);
	double YVal = targetY.getDouble(-1.0);
	
	//Constructor
	public Vision(int port) {
		//TODO put in stuff
	}
	
	//methods
	public double getXVal() {
		return XVal;
	}
	
	public double getYVal() {
		return YVal;
	}
	
//	public void adjustDirection() {
//		if(XVal > 0.5) {
//			robotDrive.drive(moveVal, rotateVal);
//		}
//	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
}
