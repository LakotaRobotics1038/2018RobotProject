package org.usfirst.frc.team1038.auton;

import org.usfirst.frc.team1038.auton.commands.Pathfinder1038;
import org.usfirst.frc.team1038.robot.Conversions;

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
	private Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, TIME_STEP, MAX_VELOCITY, MAX_ACC, MAX_JERK);
	
	public static AutonWaypointPath getInstance() {
		if(waypointPath == null) {
			System.out.println("Creating new AutonWaypointPath");
			waypointPath = new AutonWaypointPath();
		}
		return waypointPath;
	}
	
	private AutonWaypointPath() {
		
	}
	
//	public CommandGroup autonChoice() {
//		autonPath = autonSelector.chooseAuton();
//		switch (autonPath) {
//			case "N":
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.NFile));
//				break;
//			case "F":
//				//add wait
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.FFile));
//				break;
//			case "CF":
//				//add wait
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.CFFile));
//				break;
//			case "CL":
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.CLFile));
//				//addSequential for Switch drop
//				break;
//			case "CLL":
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.CLLFile));
//				//addSequential for Switch drop, cube pick up, scale drop
//				break;
//			case "CR":
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.CRFile));
//				//addSequential for Switch drop
//				break;
//			case "CRR":
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.CRRFile));
//				//addSequential for Switch drop, cube pick up, scale drop
//				break;
//			case "LLL":
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.LLLFile));
//				//addSequential for Switch drop, cube pick up, scale drop
//				break;
//			case "LRR":
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.LRRFile));
//				//addSequential for Switch drop, cube pick up, scale drop
//				break;
//			case "LLR":
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.LLRFile));
//				//addSequential for Switch drop, cube pick up, scale drop
//				break;
//			case "LL":
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.LLFile));
//				//addSequential for Switch drop
//				break;
//			case "RRR":
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.RRRFile));
//				//addSequential for Switch drop, cube pick up, scale drop
//				break;
//			case "RLL":
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.RLLFile));
//				//addSequential for Switch drop, cube pick up, scale drop
//				break;
//			case "RRL":
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.RRLFile));
//				//addSequential for Switch drop, cube pick up, scale drop
//				break;
//			case "RR":
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.RRFile));
//				//addSequential for Switch drop
//				break;
//		}
//		return autonSequence;
//	}
	
	public void writeToFile(Waypoint[] waypoints, String segment) {
		Trajectory trajectory = Pathfinder.generate(waypoints, config);
		//autonPath = autonSelector.chooseAuton();
		switch (segment) {
			case "None":
				Pathfinder.writeToFile(Pathfinder1038.NFile, trajectory);
				break;
			case "LToLSwitch":
				Pathfinder.writeToFile(Pathfinder1038.LToLSwitchFile, trajectory);
				break;
			case "RToRSwitch":
				Pathfinder.writeToFile(Pathfinder1038.RToRSwitchFile, trajectory);
				break;
			case "CToLSwitch":
				Pathfinder.writeToFile(Pathfinder1038.CToLSwitchFile, trajectory);
				break;
			case "CtoRSwitch":
				Pathfinder.writeToFile(Pathfinder1038.CToRSwitchFile, trajectory);
				break;
			case "LSwitchToLCube":
				Pathfinder.writeToFile(Pathfinder1038.LSwitchToLCubeFile, trajectory);
				break;
			case "RSwitchToRCube":
				Pathfinder.writeToFile(Pathfinder1038.RSwitchToRCubeFile, trajectory);
				break;
			case "LSwitchtoRCube":
				Pathfinder.writeToFile(Pathfinder1038.LSwitchtoRCubeFile, trajectory);
				break;
			case "RSwitchToLCube":
				Pathfinder.writeToFile(Pathfinder1038.RSwitchToLCubeFile, trajectory);
				break;
			case "LCubeToLScale":
				Pathfinder.writeToFile(Pathfinder1038.LCubeToLScaleFile, trajectory);
				break;
			case "LCubeToRScale":
				Pathfinder.writeToFile(Pathfinder1038.LCubeToRScaleFile, trajectory);
				break;
			case "RCubeToRScale":
				Pathfinder.writeToFile(Pathfinder1038.RCubeToRScaleFile, trajectory);
				break;
			case "RCubeToLScale":
				Pathfinder.writeToFile(Pathfinder1038.RCubeToLScaleFile, trajectory);
				break;
		}
	}
	
	public Waypoint[] getWaypointPath(String anchor) {
		Waypoint[] waypoints = new Waypoint[]{
				new Waypoint(Conversions.ftToDrive(0), Conversions.ftToDrive(0), Pathfinder.d2r(0)),    
        		new Waypoint(Conversions.ftToDrive(1), Conversions.ftToDrive(0), Pathfinder.d2r(0))
		};
		switch (anchor) {
			case "None":
				waypoints = new Waypoint[]{
						new Waypoint(Conversions.ftToDrive(0), Conversions.ftToDrive(0), Pathfinder.d2r(0)),    
		        		new Waypoint(Conversions.ftToDrive(1), Conversions.ftToDrive(0), Pathfinder.d2r(0))
				};
				break;
			case "LToLSwitch":
				waypoints = new Waypoint[]{
						new Waypoint(Conversions.ftToDrive(0), Conversions.ftToDrive(-6), Pathfinder.d2r(0)),    
		        		new Waypoint(Conversions.ftToDrive(8), Conversions.ftToDrive(-10), Pathfinder.d2r(0)),
		        		new Waypoint(Conversions.ftToDrive(14), Conversions.ftToDrive(-10), Pathfinder.d2r(0))
				};
				break;
			case "RToRSwitch":
				waypoints = new Waypoint[]{
						new Waypoint(Conversions.ftToDrive(0), Conversions.ftToDrive(6), Pathfinder.d2r(0)),    
		        		new Waypoint(Conversions.ftToDrive(8), Conversions.ftToDrive(10), Pathfinder.d2r(0)),
		        		new Waypoint(Conversions.ftToDrive(14), Conversions.ftToDrive(10), Pathfinder.d2r(0))
				};
				break;
			case "CToLSwitch":
				waypoints = new Waypoint[]{
						new Waypoint(Conversions.ftToDrive(0), Conversions.ftToDrive(0), Pathfinder.d2r(0)),    
		        		new Waypoint(Conversions.ftToDrive(8), Conversions.ftToDrive(-10), Pathfinder.d2r(0)),
		        		new Waypoint(Conversions.ftToDrive(14), Conversions.ftToDrive(-10), Pathfinder.d2r(0))
				};
				break;
			case "CToRSwitch":
				waypoints = new Waypoint[]{
						new Waypoint(Conversions.ftToDrive(0), Conversions.ftToDrive(6), Pathfinder.d2r(0)),    
		        		new Waypoint(Conversions.ftToDrive(8), Conversions.ftToDrive(10), Pathfinder.d2r(0)),
		        		new Waypoint(Conversions.ftToDrive(14), Conversions.ftToDrive(10), Pathfinder.d2r(0))
				};
				break;
			case "LSwitchToLCube":
				waypoints = new Waypoint[]{
						new Waypoint(Conversions.ftToDrive(14), Conversions.ftToDrive(-10), Pathfinder.d2r(0)),    
		        		new Waypoint(Conversions.ftToDrive(16), Conversions.ftToDrive(-10), Pathfinder.d2r(45)),
		        		new Waypoint(Conversions.ftToDrive(18), Conversions.ftToDrive(-9), Pathfinder.d2r(90))
				};
				break;
			case "RSwitchToRCube":
				waypoints = new Waypoint[]{
						new Waypoint(Conversions.ftToDrive(14), Conversions.ftToDrive(10), Pathfinder.d2r(0)),    
		        		new Waypoint(Conversions.ftToDrive(16), Conversions.ftToDrive(10), Pathfinder.d2r(-45)),
		        		new Waypoint(Conversions.ftToDrive(18), Conversions.ftToDrive(9), Pathfinder.d2r(-90))
				};
				break;
			case "LSwitchToRCube":
				waypoints = new Waypoint[]{
						new Waypoint(Conversions.ftToDrive(14), Conversions.ftToDrive(-10), Pathfinder.d2r(0)),    
		        		new Waypoint(Conversions.ftToDrive(16), Conversions.ftToDrive(-10), Pathfinder.d2r(45)),
		        		new Waypoint(Conversions.ftToDrive(18), Conversions.ftToDrive(-9), Pathfinder.d2r(90)),
		        		new Waypoint(Conversions.ftToDrive(18), Conversions.ftToDrive(6), Pathfinder.d2r(90))
				};
				break;
			case "RSwitchToLCube":
				waypoints = new Waypoint[]{
						new Waypoint(Conversions.ftToDrive(14), Conversions.ftToDrive(10), Pathfinder.d2r(0)),    
		        		new Waypoint(Conversions.ftToDrive(16), Conversions.ftToDrive(10), Pathfinder.d2r(-45)),
		        		new Waypoint(Conversions.ftToDrive(18), Conversions.ftToDrive(9), Pathfinder.d2r(-90)),
		        		new Waypoint(Conversions.ftToDrive(18), Conversions.ftToDrive(-6), Pathfinder.d2r(-90))
				};
				break;
			case "LCubeToLScale":
				waypoints = new Waypoint[]{
		        		new Waypoint(Conversions.ftToDrive(18), Conversions.ftToDrive(-6), Pathfinder.d2r(0)),
		        		new Waypoint(Conversions.ftToDrive(24), Conversions.ftToDrive(-11), Pathfinder.d2r(0)),
		        		new Waypoint(Conversions.ftToDrive(27), Conversions.ftToDrive(-11), Pathfinder.d2r(0))
				};
				break;
			case "LCubeToRScale":
				waypoints = new Waypoint[]{
		        		new Waypoint(Conversions.ftToDrive(18), Conversions.ftToDrive(-6), Pathfinder.d2r(0)),
		        		new Waypoint(Conversions.ftToDrive(21), Conversions.ftToDrive(0), Pathfinder.d2r(45)),
		        		new Waypoint(Conversions.ftToDrive(24), Conversions.ftToDrive(11), Pathfinder.d2r(0)),
		        		new Waypoint(Conversions.ftToDrive(27), Conversions.ftToDrive(11), Pathfinder.d2r(0))
				};
				break;
			case "RCubeToRScale":
				waypoints = new Waypoint[]{
		        		new Waypoint(Conversions.ftToDrive(18), Conversions.ftToDrive(6), Pathfinder.d2r(0)),
		        		new Waypoint(Conversions.ftToDrive(24), Conversions.ftToDrive(11), Pathfinder.d2r(0)),
		        		new Waypoint(Conversions.ftToDrive(27), Conversions.ftToDrive(11), Pathfinder.d2r(0))
				};
				break;
			case "RCubeToLScale":
				waypoints = new Waypoint[]{
		        		new Waypoint(Conversions.ftToDrive(18), Conversions.ftToDrive(6), Pathfinder.d2r(0)),
		        		new Waypoint(Conversions.ftToDrive(21), Conversions.ftToDrive(0), Pathfinder.d2r(-45)),
		        		new Waypoint(Conversions.ftToDrive(24), Conversions.ftToDrive(-11), Pathfinder.d2r(0)),
		        		new Waypoint(Conversions.ftToDrive(27), Conversions.ftToDrive(-11), Pathfinder.d2r(0))
				};
				break;
		}
		return waypoints;
	}
}
