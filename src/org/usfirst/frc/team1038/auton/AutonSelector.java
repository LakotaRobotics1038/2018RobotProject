package org.usfirst.frc.team1038.auton;

import org.usfirst.frc.team1038.robot.Dashboard;
import org.usfirst.frc.team1038.robot.Robot;

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
		if(autonChooser.equals(Robot.kForwardAuto)) {
			if(position.equals(Robot.kCenterPosition)) {
				path = "CF";
			}else if(position.equals(Robot.kLeftPosition)) {
				path = "LF";
			}else if(position.equals(Robot.kRightPosition)) {
				path = "RF";
			}
			System.out.println("Choose Forward path");
		}else if(autonChooser.equals(Robot.kCustomAuto)) {
			if(position.equals(Robot.kCenterPosition)) {
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
			}else if(position.equals(Robot.kLeftPosition)) {
				if(gameDataAuton.equals("LR")) {
					path = "LLSwitch";
				}else if(gameDataAuton.equals("LL")) {
					path = "LLL";
				}else if(gameDataAuton.equals("RL")) {
					path = "LLScale";
				}else if(gameDataAuton.equals("RR")) {
					path = "LF";
				}
			}else if(position.equals(Robot.kRightPosition)) {
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
