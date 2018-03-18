package org.usfirst.frc.team1038.auton;

import org.usfirst.frc.team1038.robot.Dashboard;
import org.usfirst.frc.team1038.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;

public class AutonSelector {
	public static final String kSingleScaleAuto = "SingleScale";
	public static final String kDualScaleAuto = "DoubleScale";
	public static final String kSingleSwitchAuto = "SingleSwitch";
	public static final String kDualSwitchAuto = "DoubleSwitch";
	public static final String kForwardAuto = "Forward";
	public static final String kLeftPosition = "L";
	public static final String kCenterPosition = "C";
	public static final String kRightPosition = "R";
	//fields
	private String autonChooser;
	private String position;
	private static AutonSelector autonSelector;
	
	
	public static AutonSelector getInstance() {
		if(autonSelector == null) {
			System.out.println("Creating new AutonSelector");
			autonSelector = new AutonSelector();
		}
		return autonSelector;
	}
	
	//constructor
	public AutonSelector() {
		
	}
	
	//methods
	public String chooseAuton() {
		
		position = Dashboard.getInstance().getPosition(); 
		autonChooser = Dashboard.getInstance().getAutonChooser(); 
		String gameDataAuton = DriverStation.getInstance().getGameSpecificMessage().substring(1, 2);
		System.out.println(position);
		System.out.println(autonChooser);
		System.out.println(gameDataAuton);
		System.out.println("Starting choose");
		String path = "N";
		switch (autonChooser)
		{
			case kForwardAuto:
				break;
			case kDualScaleAuto:
				break;
			case kDualSwitchAuto:
				break;
			case kSingleScaleAuto:
				break;
			case kSingleSwitchAuto:
				break;
		}
		return path;
	}
}
