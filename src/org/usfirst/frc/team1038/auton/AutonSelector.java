package org.usfirst.frc.team1038.auton;

import org.usfirst.frc.team1038.robot.Dashboard;

import edu.wpi.first.wpilibj.DriverStation;

public class AutonSelector {
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
		String gameDataAuton = DriverStation.getInstance().getGameSpecificMessage().substring(0, 2);
		System.out.println(position);
		System.out.println(autonChooser);
		System.out.println(gameDataAuton);
		System.out.println("Starting choose");
		String path = "N";
		if(autonChooser.equals("Forward")) {
			if(position.equals("C")) {
				path = "CF";
			}else if(position.equals("L")) {
				path = "LF";
			}else if(position.equals("R")) {
				path = "RF";
			}
			System.out.println("Choose Forward path");
		}else if(autonChooser.equals("Custom")) {
			if(position.equals("C")) {
				if(gameDataAuton.equals("LR")) {
					path = "CL";
					System.out.println("Choose CL");
				}else if(gameDataAuton.equals("LL")) {
					path = "CLL";
				}else if(gameDataAuton.equals("RL")) {
					path = "CR";
				}else if(gameDataAuton.equals("RR")) {
					path = "CRR";
				}
			}else if(position.equals("L")) {
				if(gameDataAuton.equals("LR")) {
					path = "LLSwitch";
				}else if(gameDataAuton.equals("LL")) {
					path = "LLL";
				}else if(gameDataAuton.equals("RL")) {
					path = "LLScale";
				}else if(gameDataAuton.equals("RR")) {
					path = "LF";
				}
			}else if(position.equals("R")) {
				if(gameDataAuton.equals("LR")) {
					path = "RRScale";
				}else if(gameDataAuton.equals("LL")) {
					path = "RF";
				}else if(gameDataAuton.equals("RL")) {
					path = "RRSwitch";
				}else if(gameDataAuton.equals("RR")) {
					path = "RRR";
				}
			}
		}
		return path;
	}
}
