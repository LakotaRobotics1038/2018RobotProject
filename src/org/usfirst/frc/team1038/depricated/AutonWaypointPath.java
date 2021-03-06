package org.usfirst.frc.team1038.depricated;

import java.io.File;

import org.usfirst.frc.team1038.auton.AutonSelector;
import org.usfirst.frc.team1038.auton.commands.AcquireCommand;
import org.usfirst.frc.team1038.auton.commands.AcquisitionAngleCommand;
import org.usfirst.frc.team1038.auton.commands.ElevatorCommand;
import org.usfirst.frc.team1038.auton.commands.TurnCommand;
import org.usfirst.frc.team1038.subsystem.AcquisitionScoring;
import org.usfirst.frc.team1038.subsystem.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

@Deprecated
public class AutonWaypointPath{
	public static File NRight = new File("/home/lvuser/Paths/NForward_right_detailed.csv");
	public static File NLeft = new File("/home/lvuser/Paths/NForward_left_detailed.csv");
	public static File LForwardRight = new File("/home/lvuser/Paths/LForward_right_detailed.csv");
	public static File LForwardLeft = new File("/home/lvuser/Paths/LForward_left_detailed.csv");
	public static File RForwardRight = new File("/home/lvuser/Paths/RForward_right_detailed.csv");
	public static File RForwardLeft = new File("/home/lvuser/Paths/RForward_left_detailed.csv");
	public static File CForwardRight = new File("/home/lvuser/Paths/CForward_right_detailed.csv");
	public static File CForwardLeft = new File("/home/lvuser/Paths/CForward_left_detailed.csv");
	public static File RToRightScaleRight = new File("/home/lvuser/Paths/RToRightScale_right_detailed.csv");
	public static File RToRightScaleLeft = new File("/home/lvuser/Paths/RToRightScale_left_detailed.csv");
	public static File RToLeftScaleRight = new File("/home/lvuser/Paths/RToLeftScale_right_detailed.csv");
	public static File RToLeftScaleLeft = new File("/home/lvuser/Paths/RToLeftScale_left_detailed.csv");
	public static File LToRightScaleRight = new File("/home/lvuser/Paths/LToRightScale_right_detailed.csv");
	public static File LToRightScaleLeft = new File("/home/lvuser/Paths/LToRightScale_left_detailed.csv");
	public static File LToLeftScaleRight = new File("/home/lvuser/Paths/LToLeftScale_right_detailed.csv");
	public static File LToLeftScaleLeft = new File("/home/lvuser/Paths/LToLeftScale_left_detailed.csv");
	public static File CToRightScaleRight = new File("/home/lvuser/Paths/CToRightScale_right_detailed.csv");
	public static File CToRightScaleLeft = new File("/home/lvuser/Paths/CToRightScale_left_detailed.csv");
	public static File CToLeftScaleRight = new File("/home/lvuser/Paths/CToLeftScale_right_detailed.csv");
	public static File CToLeftScaleLeft = new File("/home/lvuser/Paths/CToLeftScale_left_detailed.csv");
	private CommandGroup autonSequence = new CommandGroup();;
	private String autonPath;
	private static AutonSelector autonSelector;
	private static AutonWaypointPath waypointPath;
	
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
		//autonPath = autonSelector.chooseAuton();
		switch (autonPath) {
			case "N":
				autonSequence.addSequential(new Pathfinder1038(NLeft, NRight));
				break;
			case "LF":
				//add wait
				autonSequence.addSequential(new Pathfinder1038(LForwardLeft, LForwardRight));
				autonSequence.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
				break;
			case "RF":
				//add wait
				autonSequence.addSequential(new Pathfinder1038(RForwardLeft, RForwardRight));
				autonSequence.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
				break;
			case "CF":
				//add wait
				autonSequence.addSequential(new Pathfinder1038(CForwardLeft, CForwardRight));
				autonSequence.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
				break;
			case "CRScale":
				break;
			case "CLScale":
				break;
			case "LLScale":
				autonSequence.addSequential(new Pathfinder1038(LToLeftScaleLeft, LToLeftScaleRight));
				autonSequence.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
				autonSequence.addSequential(new ElevatorCommand(Elevator.SCALE_HIGH));
				//autonSequence.addSequential(new AcquireCommand(false, 3));
				autonSequence.addSequential(new ElevatorCommand(Elevator.FLOOR));
				break;
			case "LRScale":
				autonSequence.addSequential(new Pathfinder1038(LToRightScaleLeft, LToRightScaleRight));
				autonSequence.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
				autonSequence.addSequential(new ElevatorCommand(Elevator.SCALE_HIGH));
				//autonSequence.addSequential(new AcquireCommand(false, 3));
				autonSequence.addSequential(new ElevatorCommand(Elevator.FLOOR));
				break;
			case "RRScale":
				autonSequence.addSequential(new Pathfinder1038(RToRightScaleLeft, RToRightScaleRight));
				autonSequence.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
				autonSequence.addSequential(new ElevatorCommand(Elevator.SCALE_HIGH));
				//autonSequence.addSequential(new AcquireCommand(false, 3));
				autonSequence.addSequential(new ElevatorCommand(Elevator.FLOOR));
				break;
			case "RLScale":
				autonSequence.addSequential(new Pathfinder1038(RToLeftScaleLeft, RToLeftScaleRight));
				autonSequence.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
				autonSequence.addSequential(new ElevatorCommand(Elevator.SCALE_HIGH));
				//autonSequence.addSequential(new AcquireCommand(false, 3));
				autonSequence.addSequential(new ElevatorCommand(Elevator.FLOOR));
				break;
		}
//			case "CL":
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.CToLSwitchFile));
//				autonSequence.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
//				autonSequence.addSequential(new TurnCommand(90));
//				autonSequence.addSequential(new ElevatorCommand(Elevator.SWITCH));
//				autonSequence.addSequential(new AcquireCommand(false, 1.5));
//				autonSequence.addSequential(new ElevatorCommand(Elevator.FLOOR));
//				break;
//			case "CLL":
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.CToLSwitchFile));
//				autonSequence.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
//				autonSequence.addSequential(new TurnCommand(90));
//				autonSequence.addSequential(new ElevatorCommand(Elevator.SWITCH));
//				autonSequence.addSequential(new AcquireCommand(false, 3));
//				autonSequence.addSequential(new ElevatorCommand(Elevator.FLOOR));
//				autonSequence.addSequential(new TurnCommand(270));
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.LSwitchToLCubeFile));
//				autonSequence.addSequential(new TurnCommand(90));
//				//add go forward
//				autonSequence.addParallel(new AcquireCommand(true, 1.5));
//				//go back
//				autonSequence.addSequential(new TurnCommand(270));
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.LCubeToLScaleFile));
//				autonSequence.addSequential(new TurnCommand(90));
//				autonSequence.addSequential(new ElevatorCommand(Elevator.SCALE_HIGH));
//				autonSequence.addSequential(new AcquireCommand(false, 3));
//				autonSequence.addSequential(new ElevatorCommand(Elevator.FLOOR));
//				break;
//			case "CR":
//				System.out.println("Selected CR");
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.CToRSwitchFile));
//				autonSequence.addSequential(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
//				autonSequence.addSequential(new TurnCommand(181));
//				autonSequence.addSequential(new ElevatorCommand(Elevator.SWITCH));
//				autonSequence.addSequential(new AcquireCommand(false, 1.5));
//				autonSequence.addSequential(new ElevatorCommand(Elevator.FLOOR));
//				break;
//			case "CRR":
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.CToRSwitchFile));
//				autonSequence.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
//				autonSequence.addSequential(new TurnCommand(270));
//				autonSequence.addSequential(new ElevatorCommand(Elevator.SWITCH));
//				autonSequence.addSequential(new AcquireCommand(false, 3));
//				autonSequence.addSequential(new ElevatorCommand(Elevator.FLOOR));
//				autonSequence.addSequential(new TurnCommand(90));
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.RSwitchToRCubeFile));
//				autonSequence.addSequential(new TurnCommand(270));
//				//add go forward
//				autonSequence.addParallel(new AcquireCommand(true, 1.5));
//				//go back
//				autonSequence.addSequential(new TurnCommand(90));
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.RCubeToRScaleFile));
//				autonSequence.addSequential(new TurnCommand(270));
//				autonSequence.addSequential(new ElevatorCommand(Elevator.SCALE_HIGH));
//				autonSequence.addSequential(new AcquireCommand(false, 3));
//				autonSequence.addSequential(new ElevatorCommand(Elevator.FLOOR));
//				break;
//			case "LLL":
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.LToLSwitchFile));
//				autonSequence.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
//				autonSequence.addSequential(new TurnCommand(90));
//				autonSequence.addSequential(new ElevatorCommand(Elevator.SWITCH));
//				autonSequence.addSequential(new AcquireCommand(false, 3));
//				autonSequence.addSequential(new ElevatorCommand(Elevator.FLOOR));
//				autonSequence.addSequential(new TurnCommand(270));
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.LSwitchToLCubeFile));
//				autonSequence.addSequential(new TurnCommand(90));
//				//add go forward
//				autonSequence.addParallel(new AcquireCommand(true, 1.5));
//				//go back
//				autonSequence.addSequential(new TurnCommand(270));
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.LCubeToLScaleFile));
//				autonSequence.addSequential(new TurnCommand(90));
//				autonSequence.addSequential(new ElevatorCommand(Elevator.SCALE_HIGH));
//				autonSequence.addSequential(new AcquireCommand(false, 3));
//				autonSequence.addSequential(new ElevatorCommand(Elevator.FLOOR));
//				break;
//			case "LRR":
//				autonSequence.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.LToLSwitchFile));
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.LSwitchToRCubeFile));
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.RCubeToRScaleFile));
//				//addSequential for Switch drop, cube pick up, scale drop
//				break;
			
//			case "LLSwitch":
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.LToLSwitchFile));
//				autonSequence.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
////				autonSequence.addSequential(new TurnCommand(90));
////				autonSequence.addSequential(new ElevatorCommand(Elevator.SWITCH));
//				autonSequence.addParallel(new ElevatorCommand(Elevator.SWITCH));
//				autonSequence.addSequential(new AcquireCommand(false, 3));
//				autonSequence.addSequential(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_MAX));
//				autonSequence.addSequential(new ElevatorCommand(Elevator.FLOOR));
////				autonSequence.addSequential(new TurnCommand(270));
////				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.LSwitchToLCubeFile));
////				autonSequence.addSequential(new TurnCommand(90));
//				//add go forward
//				autonSequence.addParallel(new AcquireCommand(true, 1.5));
//				break;
//			case "RRR":
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.RToRSwitchFile));
//				autonSequence.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
//				autonSequence.addSequential(new TurnCommand(270));
//				autonSequence.addSequential(new ElevatorCommand(Elevator.SWITCH));
//				autonSequence.addSequential(new AcquireCommand(false, 3));
//				autonSequence.addSequential(new ElevatorCommand(Elevator.FLOOR));
//				autonSequence.addSequential(new TurnCommand(90));
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.RSwitchToRCubeFile));
//				autonSequence.addSequential(new TurnCommand(270));
//				//add go forward
//				autonSequence.addParallel(new AcquireCommand(true, 1.5));
//				//go back
//				autonSequence.addSequential(new TurnCommand(90));
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.RCubeToRScaleFile));
//				autonSequence.addSequential(new TurnCommand(270));
//				autonSequence.addSequential(new ElevatorCommand(Elevator.SCALE_HIGH));
//				autonSequence.addSequential(new AcquireCommand(false, 3));
//				autonSequence.addSequential(new ElevatorCommand(Elevator.FLOOR));
//				break;
//			case "RLL":
//				autonSequence.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.RToRSwitchFile));
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.RSwitchToLCubeFile));
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.LCubeToLScaleFile));
//				//addSequential for Switch drop, cube pick up, scale drop
//				break;
//			case "RRSwitch":
//				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.RToRSwitchFile));
//				autonSequence.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
//				autonSequence.addParallel(new ElevatorCommand(Elevator.SWITCH));
//				autonSequence.addSequential(new AcquireCommand(false, 3));
//				autonSequence.addSequential(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_MAX));
//				autonSequence.addSequential(new ElevatorCommand(Elevator.FLOOR));
////				autonSequence.addSequential(new TurnCommand(270));
////				autonSequence.addSequential(new ElevatorCommand(Elevator.SWITCH));
////				autonSequence.addSequential(new AcquireCommand(false, 3));
////				autonSequence.addSequential(new ElevatorCommand(Elevator.FLOOR));
////				autonSequence.addSequential(new Pathfinder1038(Pathfinder1038.RSwitchToRCubeFile));
////				autonSequence.addSequential(new TurnCommand(270));
//				//add go forward
//				autonSequence.addParallel(new AcquireCommand(true, 1.5));
//				break;
			
		return autonSequence;
	}
	
//	public void writeToFile(Waypoint[] waypoints, String segment) {
//		Trajectory trajectory = Pathfinder.generate(waypoints, config);
//		switch (segment) {
//			case "None":
//				Pathfinder.writeToFile(Pathfinder1038.NFile, trajectory);
//				break;
//			case "LToLSwitch":
//				Pathfinder.writeToFile(Pathfinder1038.LToLSwitchFile, trajectory);
//				break;
//			case "RToRSwitch":
//				Pathfinder.writeToFile(Pathfinder1038.RToRSwitchFile, trajectory);
//				break;
//			case "CToLSwitch":
//				Pathfinder.writeToFile(Pathfinder1038.CToLSwitchFile, trajectory);
//				break;
//			case "CToRSwitch":
//				Pathfinder.writeToFile(Pathfinder1038.CToRSwitchFile, trajectory);
//				break;
//			case "LSwitchToLCube":
//				Pathfinder.writeToFile(Pathfinder1038.LSwitchToLCubeFile, trajectory);
//				break;
//			case "RSwitchToRCube":
//				Pathfinder.writeToFile(Pathfinder1038.RSwitchToRCubeFile, trajectory);
//				break;
//			case "LSwitchToRCube":
//				Pathfinder.writeToFile(Pathfinder1038.LSwitchToRCubeFile, trajectory);
//				break;
//			case "RSwitchToLCube":
//				Pathfinder.writeToFile(Pathfinder1038.RSwitchToLCubeFile, trajectory);
//				break;
//			case "LCubeToLScale":
//				Pathfinder.writeToFile(Pathfinder1038.LCubeToLScaleFile, trajectory);
//				break;
//			case "LCubeToRScale":
//				Pathfinder.writeToFile(Pathfinder1038.LCubeToRScaleFile, trajectory);
//				break;
//			case "RCubeToRScale":
//				Pathfinder.writeToFile(Pathfinder1038.RCubeToRScaleFile, trajectory);
//				break;
//			case "RCubeToLScale":
//				Pathfinder.writeToFile(Pathfinder1038.RCubeToLScaleFile, trajectory);
//				break;
//			case "RSwitchToRScale":
//				Pathfinder.writeToFile(Pathfinder1038.RSwitchToRScaleFile, trajectory);
//				break;
//			case "LSwitchToLScale":
//				Pathfinder.writeToFile(Pathfinder1038.LSwitchToLScaleFile, trajectory);
//				break;
//			default:
//				System.out.println("Default Case! ERR!");
//		}
//	}
	
//	public Waypoint[] getWaypointPath(String segment) {
//		Waypoint[] waypoints;
//		switch (segment) {
//			case "None":
//				waypoints = new Waypoint[] {
//						new Waypoint(Conversions.f2m(0), Conversions.f2m(0), Pathfinder.d2r(0)),    
//		        		new Waypoint(Conversions.f2m(1), Conversions.f2m(0), Pathfinder.d2r(0))
//				};
//				break;
//			case "LToLSwitch":
//				waypoints = new Waypoint[] {
////						new Waypoint(Conversions.f2m(0), Conversions.f2m(-6.4), Pathfinder.d2r(0)),    
////		        		new Waypoint(Conversions.f2m(8), Conversions.f2m(-8), Pathfinder.d2r(0)),
////		        		new Waypoint(Conversions.f2m(14), Conversions.f2m(-8), Pathfinder.d2r(0))
//						new Waypoint(Conversions.f2m(0), Conversions.f2m(0), Pathfinder.d2r(0)),
//						new Waypoint(Conversions.f2m(10), Conversions.f2m(0), Pathfinder.d2r(0))
//				};
//				break;
//			case "RToRSwitch":
//				waypoints = new Waypoint[] {
////						new Waypoint(Conversions.f2m(0), Conversions.f2m(6.4), Pathfinder.d2r(0)),    
////		        		new Waypoint(Conversions.f2m(8), Conversions.f2m(8), Pathfinder.d2r(0)),
////		        		new Waypoint(Conversions.f2m(14), Conversions.f2m(8), Pathfinder.d2r(0))
//						new Waypoint(Conversions.f2m(0), Conversions.f2m(0), Pathfinder.d2r(0)),
//						new Waypoint(Conversions.f2m(10), Conversions.f2m(0), Pathfinder.d2r(0))
//				};
//				break;
//			case "CToLSwitch":
//				waypoints = new Waypoint[] {
//						new Waypoint(Conversions.f2m(0), Conversions.f2m(0), Pathfinder.d2r(0)),    
//		        		new Waypoint(Conversions.f2m(2.5), Conversions.f2m(6), Pathfinder.d2r(45)),
//		        		new Waypoint(Conversions.f2m(12), Conversions.f2m(6), Pathfinder.d2r(0))
//				};
//				break;
//			case "CToRSwitch":
//				waypoints = new Waypoint[] {
//						new Waypoint(Conversions.f2m(0), Conversions.f2m(0), Pathfinder.d2r(0)),    
//		        		new Waypoint(Conversions.f2m(4), Conversions.f2m(-9), Pathfinder.d2r(-45)),
//		        		new Waypoint(Conversions.f2m(14), Conversions.f2m(-9), Pathfinder.d2r(0))
//				};
//				break;
//			case "LSwitchToLCube":
//				waypoints = new Waypoint[] {
//						new Waypoint(Conversions.f2m(14), Conversions.f2m(-8), Pathfinder.d2r(0)),    
//		        		new Waypoint(Conversions.f2m(16.5), Conversions.f2m(-7.5), Pathfinder.d2r(45)),
//		        		new Waypoint(Conversions.f2m(19), Conversions.f2m(-7), Pathfinder.d2r(90))
//				};
//				break;
//			case "RSwitchToRCube":
//				waypoints = new Waypoint[] {
//						new Waypoint(Conversions.f2m(14), Conversions.f2m(8), Pathfinder.d2r(0)),    
//		        		new Waypoint(Conversions.f2m(16.5), Conversions.f2m(7.5), Pathfinder.d2r(-45)),
//		        		new Waypoint(Conversions.f2m(19), Conversions.f2m(7), Pathfinder.d2r(-90))
//				};
//				break;
//			case "LSwitchToRCube":
//				waypoints = new Waypoint[] {
//						new Waypoint(Conversions.f2m(14), Conversions.f2m(-8), Pathfinder.d2r(0)),    
//		        		new Waypoint(Conversions.f2m(16.5), Conversions.f2m(-7.5), Pathfinder.d2r(45)),
//		        		new Waypoint(Conversions.f2m(19), Conversions.f2m(-7), Pathfinder.d2r(90)),
//		        		new Waypoint(Conversions.f2m(19), Conversions.f2m(6), Pathfinder.d2r(90))
//				};
//				break;
//			case "RSwitchToLCube":
//				waypoints = new Waypoint[] {
//						new Waypoint(Conversions.f2m(14), Conversions.f2m(8), Pathfinder.d2r(0)),    
//		        		new Waypoint(Conversions.f2m(16.5), Conversions.f2m(7.5), Pathfinder.d2r(-45)),
//		        		new Waypoint(Conversions.f2m(19), Conversions.f2m(7), Pathfinder.d2r(-90)),
//		        		new Waypoint(Conversions.f2m(19), Conversions.f2m(-6), Pathfinder.d2r(-90))
//				};
//				break;
//			case "LCubeToLScale":
//				waypoints = new Waypoint[] {
//		        		new Waypoint(Conversions.f2m(19), Conversions.f2m(-6), Pathfinder.d2r(-90)),
//		        		//new Waypoint(Conversions.f2m(19.25), Conversions.f2m(-9), Pathfinder.d2r(-90)),
//		        		new Waypoint(Conversions.f2m(21), Conversions.f2m(-9), Pathfinder.d2r(-45)),
//		        		new Waypoint(Conversions.f2m(26.2), Conversions.f2m(-9), Pathfinder.d2r(0))
//				};
//				break;
//			case "LCubeToRScale":
//				waypoints = new Waypoint[] {
//		        		new Waypoint(Conversions.f2m(19), Conversions.f2m(-6), Pathfinder.d2r(90)),
//		        		//new Waypoint(Conversions.f2m(19.25), Conversions.f2m(0), Pathfinder.d2r(45)),
//		        		new Waypoint(Conversions.f2m(19), Conversions.f2m(9), Pathfinder.d2r(90)),
//		        		new Waypoint(Conversions.f2m(21), Conversions.f2m(10), Pathfinder.d2r(45)),
//		        		new Waypoint(Conversions.f2m(26.2), Conversions.f2m(9), Pathfinder.d2r(0))
//				};
//				break;
//			case "RCubeToRScale":
//				waypoints = new Waypoint[] {
//		        		new Waypoint(Conversions.f2m(19), Conversions.f2m(6), Pathfinder.d2r(0)),
//		        		new Waypoint(Conversions.f2m(19.25), Conversions.f2m(9), Pathfinder.d2r(0)),
//		        		new Waypoint(Conversions.f2m(21), Conversions.f2m(9), Pathfinder.d2r(45)),
//		        		new Waypoint(Conversions.f2m(26.2), Conversions.f2m(9), Pathfinder.d2r(0))
//				};
//				break;
//			case "RCubeToLScale":
//				waypoints = new Waypoint[] {
//		        		new Waypoint(Conversions.f2m(19), Conversions.f2m(6), Pathfinder.d2r(-90)),
//		        		//new Waypoint(Conversions.f2m(19.25), Conversions.f2m(0), Pathfinder.d2r(-45)),
//		        		new Waypoint(Conversions.f2m(19), Conversions.f2m(-9), Pathfinder.d2r(-90)),
//		        		new Waypoint(Conversions.f2m(21), Conversions.f2m(-10), Pathfinder.d2r(-45)),
//		        		new Waypoint(Conversions.f2m(26.2), Conversions.f2m(-9), Pathfinder.d2r(0))
//				};
//				break;
//			case "LSwitchToLScale":
//				waypoints = new Waypoint[] {
//		        		new Waypoint(Conversions.f2m(14), Conversions.f2m(-8), Pathfinder.d2r(0)),
//		        		new Waypoint(Conversions.f2m(19.25), Conversions.f2m(-9), Pathfinder.d2r(0)),
//		        		new Waypoint(Conversions.f2m(26.2), Conversions.f2m(-9), Pathfinder.d2r(0))
//				};
//				break;
//			case "RSwitchToRScale":
//				waypoints = new Waypoint[] {
//		        		new Waypoint(Conversions.f2m(14), Conversions.f2m(8), Pathfinder.d2r(0)),
//		        		new Waypoint(Conversions.f2m(19.25), Conversions.f2m(9), Pathfinder.d2r(0)),
//		        		new Waypoint(Conversions.f2m(26.2), Conversions.f2m(9), Pathfinder.d2r(0))
//				};
//				break;
//			default:
//				waypoints = new Waypoint[] {
//						new Waypoint(Conversions.f2m(0), Conversions.f2m(0), Pathfinder.d2r(0)),
//		        		new Waypoint(Conversions.f2m(1), Conversions.f2m(0), Pathfinder.d2r(0))};
//		}
//		return waypoints;
//	}
}
