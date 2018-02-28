package org.usfirst.frc.team1038.auton;

import org.usfirst.frc.team1038.auton.commands.AcquireCommand;
import org.usfirst.frc.team1038.auton.commands.AcquisitionAngleCommand;
import org.usfirst.frc.team1038.auton.commands.ElevatorCommand;
import org.usfirst.frc.team1038.auton.commands.Pathfinder1038;
import org.usfirst.frc.team1038.auton.commands.TurnCommand;
import org.usfirst.frc.team1038.robot.Conversions;
import org.usfirst.frc.team1038.subsystem.AcquisitionScoring;
import org.usfirst.frc.team1038.subsystem.Elevator;

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
	
	public CommandGroup autonChoice() {
		autonPath = autonSelector.chooseAuton();
		switch (autonPath) {
			case "N":
				autonSequence.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.NFile));
				break;
			case "LF":
				//add wait
				autonSequence.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.LToLSwitchFile));
				break;
			case "RF":
				//add wait
				autonSequence.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.RToRSwitchFile));
				break;
			case "CF":
				//add wait
				autonSequence.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.CToLSwitchFile));
				break;
			case "CL":
				autonSequence.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.CToLSwitchFile));
				autonSequence.addSequential(new TurnCommand(90));
				autonSequence.addSequential(new ElevatorCommand(Elevator.SWITCH));
				autonSequence.addSequential(new AcquireCommand(false, 3));
				autonSequence.addSequential(new ElevatorCommand(Elevator.FLOOR));
				break;
			case "CLL":
				autonSequence.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.CToLSwitchFile));
				autonSequence.addSequential(new TurnCommand(90));
				autonSequence.addSequential(new ElevatorCommand(Elevator.SWITCH));
				autonSequence.addSequential(new AcquireCommand(false, 3));
				autonSequence.addSequential(new ElevatorCommand(Elevator.FLOOR));
				autonSequence.addSequential(new TurnCommand(270));
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.LSwitchToLCubeFile));
				autonSequence.addSequential(new TurnCommand(90));
				autonSequence.addParallel(new AcquireCommand(true, 1.5));
				//add go forward
				//go back
				autonSequence.addSequential(new TurnCommand(270));
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.LCubeToLScaleFile));
				autonSequence.addSequential(new TurnCommand(90));
				autonSequence.addSequential(new ElevatorCommand(Elevator.SCALE_HIGH));
				autonSequence.addSequential(new AcquireCommand(false, 3));
				autonSequence.addSequential(new ElevatorCommand(Elevator.FLOOR));
				break;
			case "CR":
				autonSequence.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.CToRSwitchFile));
				autonSequence.addSequential(new TurnCommand(270));
				autonSequence.addSequential(new ElevatorCommand(Elevator.SWITCH));
				autonSequence.addSequential(new AcquireCommand(false, 3));
				autonSequence.addSequential(new ElevatorCommand(Elevator.FLOOR));
				break;
			case "CRR":
				autonSequence.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.CToRSwitchFile));
				autonSequence.addSequential(new TurnCommand(270));
				autonSequence.addSequential(new ElevatorCommand(Elevator.SWITCH));
				autonSequence.addSequential(new AcquireCommand(false, 3));
				autonSequence.addSequential(new ElevatorCommand(Elevator.FLOOR));
				autonSequence.addSequential(new TurnCommand(90));
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.RSwitchToRCubeFile));
				autonSequence.addSequential(new TurnCommand(270));
				autonSequence.addParallel(new AcquireCommand(true, 1.5));
				//add go forward
				//go back
				autonSequence.addSequential(new TurnCommand(90));
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.RCubeToRScaleFile));
				autonSequence.addSequential(new TurnCommand(270));
				autonSequence.addSequential(new ElevatorCommand(Elevator.SCALE_HIGH));
				autonSequence.addSequential(new AcquireCommand(false, 3));
				autonSequence.addSequential(new ElevatorCommand(Elevator.FLOOR));
				break;
			case "LLL":
				autonSequence.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.LToLSwitchFile));
				autonSequence.addSequential(new TurnCommand(90));
				autonSequence.addSequential(new ElevatorCommand(Elevator.SWITCH));
				autonSequence.addSequential(new AcquireCommand(false, 3));
				autonSequence.addSequential(new ElevatorCommand(Elevator.FLOOR));
				autonSequence.addSequential(new TurnCommand(270));
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.LSwitchToLCubeFile));
				autonSequence.addSequential(new TurnCommand(90));
				autonSequence.addParallel(new AcquireCommand(true, 1.5));
				//add go forward
				//go back
				autonSequence.addSequential(new TurnCommand(270));
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.LCubeToLScaleFile));
				autonSequence.addSequential(new TurnCommand(90));
				autonSequence.addSequential(new ElevatorCommand(Elevator.SCALE_HIGH));
				autonSequence.addSequential(new AcquireCommand(false, 3));
				autonSequence.addSequential(new ElevatorCommand(Elevator.FLOOR));
				break;
//			case "LRR":
//				autonSequence.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.LToLSwitchFile));
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.LSwitchToRCubeFile));
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.RCubeToRScaleFile));
//				//addSequential for Switch drop, cube pick up, scale drop
//				break;
			case "LLScale":
				autonSequence.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.LToLSwitchFile));
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.LSwitchToLScaleFile));
				autonSequence.addSequential(new TurnCommand(90));
				autonSequence.addSequential(new ElevatorCommand(Elevator.SCALE_HIGH));
				autonSequence.addSequential(new AcquireCommand(false, 3));
				autonSequence.addSequential(new ElevatorCommand(Elevator.FLOOR));
				//addSequential for Switch drop, cube pick up, scale drop
				break;
			case "LLSwitch":
				autonSequence.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.LToLSwitchFile));
				autonSequence.addSequential(new TurnCommand(90));
				autonSequence.addSequential(new ElevatorCommand(Elevator.SWITCH));
				autonSequence.addSequential(new AcquireCommand(false, 3));
				autonSequence.addSequential(new ElevatorCommand(Elevator.FLOOR));
				autonSequence.addSequential(new TurnCommand(270));
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.LSwitchToLCubeFile));
				autonSequence.addSequential(new TurnCommand(90));
				autonSequence.addParallel(new AcquireCommand(true, 1.5));
				//add go forward
				break;
			case "RRR":
				autonSequence.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.RToRSwitchFile));
				autonSequence.addSequential(new TurnCommand(270));
				autonSequence.addSequential(new ElevatorCommand(Elevator.SWITCH));
				autonSequence.addSequential(new AcquireCommand(false, 3));
				autonSequence.addSequential(new ElevatorCommand(Elevator.FLOOR));
				autonSequence.addSequential(new TurnCommand(90));
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.RSwitchToRCubeFile));
				autonSequence.addSequential(new TurnCommand(270));
				autonSequence.addParallel(new AcquireCommand(true, 1.5));
				//add go forward
				//go back
				autonSequence.addSequential(new TurnCommand(90));
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.RCubeToRScaleFile));
				autonSequence.addSequential(new TurnCommand(270));
				autonSequence.addSequential(new ElevatorCommand(Elevator.SCALE_HIGH));
				autonSequence.addSequential(new AcquireCommand(false, 3));
				autonSequence.addSequential(new ElevatorCommand(Elevator.FLOOR));
				break;
//			case "RLL":
//				autonSequence.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.RToRSwitchFile));
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.RSwitchToLCubeFile));
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.LCubeToLScaleFile));
//				//addSequential for Switch drop, cube pick up, scale drop
//				break;
			case "RRSwitch":
				autonSequence.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.RToRSwitchFile));
				autonSequence.addSequential(new TurnCommand(270));
				autonSequence.addSequential(new ElevatorCommand(Elevator.SWITCH));
				autonSequence.addSequential(new AcquireCommand(false, 3));
				autonSequence.addSequential(new ElevatorCommand(Elevator.FLOOR));
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.RSwitchToRCubeFile));
				autonSequence.addSequential(new TurnCommand(270));
				autonSequence.addParallel(new AcquireCommand(true, 1.5));
				//add go forward
				break;
			case "RRScale":
				autonSequence.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.RToRSwitchFile));
				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.RSwitchToRScaleFile));
				autonSequence.addSequential(new TurnCommand(270));
				autonSequence.addSequential(new ElevatorCommand(Elevator.SCALE_HIGH));
				autonSequence.addSequential(new AcquireCommand(false, 3));
				autonSequence.addSequential(new ElevatorCommand(Elevator.FLOOR));
				break;
		}
		return autonSequence;
	}
	
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
				Pathfinder.writeToFile(Pathfinder1038.LSwitchToRCubeFile, trajectory);
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
			case "RSwitchToRScale":
				Pathfinder.writeToFile(Pathfinder1038.RSwitchToRScaleFile, trajectory);
				break;
			case "LSwitchToLScale":
				Pathfinder.writeToFile(Pathfinder1038.LSwitchToLScaleFile, trajectory);
				break;
		}
	}
	
	public Waypoint[] getWaypointPath(String segment) {
		Waypoint[] waypoints = new Waypoint[]{
				new Waypoint(Conversions.ftToDrive(0), Conversions.ftToDrive(0), Pathfinder.d2r(0)),    
        		new Waypoint(Conversions.ftToDrive(1), Conversions.ftToDrive(0), Pathfinder.d2r(0))
		};
		switch (segment) {
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
			case "LSwitchToLScale":
				waypoints = new Waypoint[]{
		        		new Waypoint(Conversions.ftToDrive(14), Conversions.ftToDrive(-10), Pathfinder.d2r(0)),
		        		new Waypoint(Conversions.ftToDrive(24), Conversions.ftToDrive(-11), Pathfinder.d2r(0)),
		        		new Waypoint(Conversions.ftToDrive(27), Conversions.ftToDrive(-11), Pathfinder.d2r(0))
				};
				break;
			case "RSwitchToRScale":
				waypoints = new Waypoint[]{
		        		new Waypoint(Conversions.ftToDrive(14), Conversions.ftToDrive(10), Pathfinder.d2r(0)),
		        		new Waypoint(Conversions.ftToDrive(24), Conversions.ftToDrive(11), Pathfinder.d2r(0)),
		        		new Waypoint(Conversions.ftToDrive(27), Conversions.ftToDrive(11), Pathfinder.d2r(0))
				};
				break;
		}
		return waypoints;
	}
}
