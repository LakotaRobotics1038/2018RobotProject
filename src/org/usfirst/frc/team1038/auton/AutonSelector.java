package org.usfirst.frc.team1038.auton;

import org.usfirst.frc.team1038.robot.Dashboard;

import edu.wpi.first.wpilibj.DriverStation;

public class AutonSelector {
	//fields
	private String gameDataAuton = DriverStation.getInstance().getGameSpecificMessage().substring(0, 2);
	private String position = Dashboard.getPosition().toUpperCase();
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
		String path = "F";
		if(position == "C") {
			if(gameDataAuton == "LR") {
				path = "CL";
			}else if(gameDataAuton == "LL") {
				path = "CLL";
			}else if(gameDataAuton == "RL") {
				path = "CR";
			}else if(gameDataAuton == "RR") {
				path = "CRR";
			}
		}else if(position == "L") {
			if(gameDataAuton == "LR") {
				path = "LLR";
			}else if(gameDataAuton == "LL") {
				path = "LLL";
			}else if(gameDataAuton == "RL") {
				path = "LL";
			}else if(gameDataAuton == "RR") {
				path = "LRR";
			}
		}else if(position == "R") {
			if(gameDataAuton == "LR") {
				path = "RR";
			}else if(gameDataAuton == "LL") {
				path = "RLL";
			}else if(gameDataAuton == "RL") {
				path = "RRL";
			}else if(gameDataAuton == "RR") {
				path = "RRR";
			}
		}
		return path;
	}
}
