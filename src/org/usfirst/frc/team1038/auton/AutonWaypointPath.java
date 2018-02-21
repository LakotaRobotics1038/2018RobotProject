package org.usfirst.frc.team1038.auton;

import org.usfirst.frc.team1038.robot.Conversions;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class AutonWaypointPath {
	private Waypoint[] points;
	
	private String autonPath;
	private static AutonSelector autonSelector = AutonSelector.getInstance();
	
	public Waypoint[] waypointPath() {
		autonPath = autonSelector.chooseAuton();
		switch (autonPath) {
			case "F":
				points = new Waypoint[] {
					new Waypoint(Conversions.ftToDrive(0), Conversions.ftToDrive(0), Pathfinder.d2r(0)),
					new Waypoint(Conversions.ftToDrive(10), Conversions.ftToDrive(0), Pathfinder.d2r(0))
				};
				break;
			case "CL":
				
				break;
			case "CLL":
				
				break;
			case "CR":
				
				break;
			case "CRR":
				
				break;
			case "LLL":
				
				break;
			case "LRR":
				
				break;
			case "LLR":
				
				break;
			case "LL":
				
				break;
			case "RRR":
				
				break;
			case "RLL":
				
				break;
			case "RRL":
				
				break;
			case "RR":
				
				break;
		}
		return points;
	}
}
