package org.usfirst.frc.team1038.auton;

import org.usfirst.frc.team1038.robot.Dashboard;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

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
	private String gameData;
	private static AutonSelector autonSelector;
	
	
	public static AutonSelector getInstance() {
		if(autonSelector == null) {
			System.out.println("Creating new AutonSelector");
			autonSelector = new AutonSelector();
		}
		return autonSelector;
	}
	
	//constructor
	private AutonSelector() {
		
	}
	
	//methods
	public CommandGroup chooseAuton() {
		
		position = Dashboard.getInstance().getPosition(); 
		autonChooser = Dashboard.getInstance().getAutonChooser(); 
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		System.out.println("pos:" + position);
		System.out.println("auto:" + autonChooser);
		System.out.println("game data:" + gameData);
		
		switch (autonChooser)
		{
			case kForwardAuto:
				return new ForwardAuton().select();
			case kDualScaleAuto:
				return new DualScaleAuton(position, gameData).select();
			case kDualSwitchAuto:
				return new DualSwitchAuton(position, gameData).select();
			case kSingleScaleAuto:
				return new SingleScaleAuton(position, gameData).select();
			case kSingleSwitchAuto:
				return new SingleSwitchAuton(position, gameData).select();
			default:
				return new ForwardAuton().select();
		}
	}
}
