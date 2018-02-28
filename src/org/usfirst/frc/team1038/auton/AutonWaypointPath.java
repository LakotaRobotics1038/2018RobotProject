package org.usfirst.frc.team1038.auton;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

public class AutonWaypointPath{
	private final double TIME_STEP = .05;
	private final double MAX_VELOCITY = .85;
	private final double MAX_ACC = 1.0;
	private final double MAX_JERK = 60.0;
	private CommandGroup autonSequence;
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
	
	public CommandGroup autonChoice() {
		autonPath = autonSelector.chooseAuton();
		switch (autonPath) {
			case "N":
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.NFile));
				break;
			case "F":
				//add wait
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.FFile));
				break;
			case "CF":
				//add wait
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.CFFile));
				break;
			case "CL":
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.CLFile));
				//addSequential for Switch drop
				break;
			case "CLL":
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.CLLFile));
				//addSequential for Switch drop, cube pick up, scale drop
				break;
			case "CR":
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.CRFile));
				//addSequential for Switch drop
				break;
			case "CRR":
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.CRRFile));
				//addSequential for Switch drop, cube pick up, scale drop
				break;
			case "LLL":
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.LLLFile));
				//addSequential for Switch drop, cube pick up, scale drop
				break;
			case "LRR":
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.LRRFile));
				//addSequential for Switch drop, cube pick up, scale drop
				break;
			case "LLR":
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.LLRFile));
				//addSequential for Switch drop, cube pick up, scale drop
				break;
			case "LL":
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.LLFile));
				//addSequential for Switch drop
				break;
			case "RRR":
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.RRRFile));
				//addSequential for Switch drop, cube pick up, scale drop
				break;
			case "RLL":
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.RLLFile));
				//addSequential for Switch drop, cube pick up, scale drop
				break;
			case "RRL":
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.RRLFile));
				//addSequential for Switch drop, cube pick up, scale drop
				break;
			case "RR":
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.RRFile));
				//addSequential for Switch drop
				break;
		}
		return autonSequence;
	}
	
	public void writeToFile(Waypoint[] waypoints) {
		Trajectory trajectory = Pathfinder.generate(waypoints, config);
		autonPath = autonSelector.chooseAuton();
		switch (autonPath) {
			case "N":
				Pathfinder.writeToFile(Pathfinder1038.NFile, trajectory);
				break;
			case "F":
				Pathfinder.writeToFile(Pathfinder1038.FFile, trajectory);
				break;
			case "CL":
				Pathfinder.writeToFile(Pathfinder1038.CLFile, trajectory);
				break;
			case "CLL":
				Pathfinder.writeToFile(Pathfinder1038.CLLFile, trajectory);
				break;
			case "CR":
				Pathfinder.writeToFile(Pathfinder1038.CRFile, trajectory);
				break;
			case "CRR":
				Pathfinder.writeToFile(Pathfinder1038.CRRFile, trajectory);
				break;
			case "LLL":
				Pathfinder.writeToFile(Pathfinder1038.LLLFile, trajectory);
				break;
			case "LRR":
				Pathfinder.writeToFile(Pathfinder1038.LRRFile, trajectory);
				break;
			case "LLR":
				Pathfinder.writeToFile(Pathfinder1038.LLRFile, trajectory);
				break;
			case "LL":
				Pathfinder.writeToFile(Pathfinder1038.LLFile, trajectory);
				break;
			case "RRR":
				Pathfinder.writeToFile(Pathfinder1038.RRRFile, trajectory);
				break;
			case "RLL":
				Pathfinder.writeToFile(Pathfinder1038.RLLFile, trajectory);
				break;
			case "RRL":
				Pathfinder.writeToFile(Pathfinder1038.RRLFile, trajectory);
				break;
			case "RR":
				Pathfinder.writeToFile(Pathfinder1038.RRFile, trajectory);
				break;
		}
	}
}
