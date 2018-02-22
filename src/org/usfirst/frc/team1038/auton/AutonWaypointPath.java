package org.usfirst.frc.team1038.auton;

import org.usfirst.frc.team1038.robot.Conversions;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

public class AutonWaypointPath {
	private final double TIME_STEP = .05;
	private final double MAX_VELOCITY = .85;
	private final double MAX_ACC = 1.0;
	private final double MAX_JERK = 60.0;
	private Waypoint[] points;
	private String autonPath;
	private static AutonSelector autonSelector = AutonSelector.getInstance();
	private static AutonWaypointPath waypointPath;
	private Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_LOW, TIME_STEP, MAX_VELOCITY, MAX_ACC, MAX_JERK);
	
	public static AutonWaypointPath getInstance() {
		if(waypointPath == null) {
			System.out.println("Creating new AutonWaypointPath");
			waypointPath = new AutonWaypointPath();
		}
		return waypointPath;
	}
	
	private AutonWaypointPath() {
		
	}
	
	public Waypoint[] waypointPathChoice() {
		autonPath = autonSelector.chooseAuton();
		switch (autonPath) {
			case "N":
				points = new Waypoint[] {
						new Waypoint(Conversions.ftToDrive(0), Conversions.ftToDrive(0), Pathfinder.d2r(0)),
						new Waypoint(Conversions.ftToDrive(1), Conversions.ftToDrive(0), Pathfinder.d2r(0))
				};
				break;
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
	
	public void writeToFile(Waypoint[] waypoints) {
		Trajectory trajectory = Pathfinder.generate(waypoints, config);
		autonPath = autonSelector.chooseAuton();
		switch (autonPath) {
			case "N":
				Pathfinder.writeToCSV(Pathfinder1038.NFile, trajectory);
				break;
			case "F":
				Pathfinder.writeToCSV(Pathfinder1038.FFile, trajectory);
				break;
			case "CL":
				Pathfinder.writeToCSV(Pathfinder1038.CLFile, trajectory);
				break;
			case "CLL":
				Pathfinder.writeToCSV(Pathfinder1038.CLLFile, trajectory);
				break;
			case "CR":
				Pathfinder.writeToCSV(Pathfinder1038.CRFile, trajectory);
				break;
			case "CRR":
				Pathfinder.writeToCSV(Pathfinder1038.CRRFile, trajectory);
				break;
			case "LLL":
				Pathfinder.writeToCSV(Pathfinder1038.LLLFile, trajectory);
				break;
			case "LRR":
				Pathfinder.writeToCSV(Pathfinder1038.LRRFile, trajectory);
				break;
			case "LLR":
				Pathfinder.writeToCSV(Pathfinder1038.LLRFile, trajectory);
				break;
			case "LL":
				Pathfinder.writeToCSV(Pathfinder1038.LLFile, trajectory);
				break;
			case "RRR":
				Pathfinder.writeToCSV(Pathfinder1038.RRRFile, trajectory);
				break;
			case "RLL":
				Pathfinder.writeToCSV(Pathfinder1038.RLLFile, trajectory);
				break;
			case "RRL":
				Pathfinder.writeToCSV(Pathfinder1038.RRLFile, trajectory);
				break;
			case "RR":
				Pathfinder.writeToCSV(Pathfinder1038.RRFile, trajectory);
				break;
		}
	}
}
