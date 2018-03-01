package org.usfirst.frc.team1038.robot;

import org.usfirst.frc.team1038.subsystem.AcquisitionScoring;
import org.usfirst.frc.team1038.subsystem.DriveTrain;
import org.usfirst.frc.team1038.subsystem.Elevator;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort;

public class SwagLights {
	public enum WheelWellStates { Disabled, EStop, Red, Blue };
	public enum NameNumberStates { HighGear, LowGear };
	public enum TowerStates { Default, Cam1, Cam2, Acquiring, Disposing, ElevatorUp, ElevatorDown};
	
	//Wheel Well
	private static final String DISABLED = "D";	
	private static final String E_STOP = "E";
	private static final String RED_ALLIANCE = "R";
	private static final String BLUE_ALLIANCE = "B";
	
	//Name Number
	private static final String HIGH_GEAR = "H";
	private static final String LOW_GEAR = "L";
	
	//Tower
	private final String CAN_CUN_NO = "s"; //Camera doesn't see cube, does not have cube, & isn't acquiring
	private final String CAY_CUN_NO = "f"; //Camera sees cube, doesn't have cube, & isn't  acquiring
	private final String ACQUIRING = "a";
	private final String DISPOSING = "a";
	private final String ELEVATOR_UP = "u";
	private final String ELEVATOR_DOWN = "d";
	private final String TOWER_DEFAULT = "n";
	
	private String wheelWell = "";
	private String tower = "";
	private String nameNumber = "";
	private DriveTrain robotDrive = DriveTrain.getInstance();
	private Elevator elevator = Elevator.getInstance();
	private AcquisitionScoring acqSco = AcquisitionScoring.getInstance();
	
	private SerialPort ard = new SerialPort(9600, SerialPort.Port.kMXP);
	private static SwagLights swag;
	
	public static SwagLights getInstance() {
		if (swag == null) {
			System.out.println("Creating new SwagLights");
			swag = new SwagLights();
		}
		return swag;
	}
	
	private SwagLights()
	{
		
	}
	
	public void setWheelWell(WheelWellStates state)
	{
		switch (state)
		{
		case Blue:
			wheelWell = BLUE_ALLIANCE;
			break;
		case Disabled:
			wheelWell = DISABLED;
			break;
		case EStop:
			wheelWell = E_STOP;
			break;
		case Red:
			wheelWell = RED_ALLIANCE;
			break;
		}
	}
	
	public void setNameNumer(NameNumberStates state)
	{
		switch (state)
		{
		case HighGear:
			nameNumber = HIGH_GEAR;
			break;
		case LowGear:
			nameNumber = LOW_GEAR;
			break;		
		}
	}
	
	public void setTower(TowerStates state)
	{
		switch (state)
		{
		case Acquiring:
			tower = ACQUIRING;
			break;
		case Cam1:
			tower = CAN_CUN_NO;
			break;
		case Cam2:
			tower = CAY_CUN_NO;
			break;
		case Disposing:
			tower = DISPOSING;
			break;
		case ElevatorDown:
			tower = ELEVATOR_DOWN;
			break;
		case ElevatorUp:
			tower = ELEVATOR_UP;
			break;
		case Default:
			tower = TOWER_DEFAULT;
			break;
		}
	}
	
	public void swagPeriodic()
	{
		ard.writeString(wheelWell + "\n" + nameNumber + "\n" + tower + "\n");
	}	
	
	public void swagEnabledPeriodic()
	{
		if(robotDrive.isHighGear()) {
			setNameNumer(NameNumberStates.HighGear);
		} else {
			setNameNumer(NameNumberStates.LowGear);
		}
		
		if (elevator.getElevatorSpeed() > 1) {
			setTower(TowerStates.ElevatorUp);
		} else if (elevator.getElevatorSpeed() < 1) {
			setTower(TowerStates.ElevatorDown);
		} else if (acqSco.getAcqMotorPower() > .2) {
			setTower(TowerStates.Acquiring);
		} else if (acqSco.getAcqMotorPower() < .2) {
			setTower(TowerStates.Disposing);
		} else {
			setTower(TowerStates.Default);
		}
	}
	
	public void enable()
	{
		Robot.disabled = false;
		Robot.eStopped = false;
		
		if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Red) {
			setWheelWell(WheelWellStates.Red);
		} else {
			setWheelWell(WheelWellStates.Blue);
		}
	}
}
