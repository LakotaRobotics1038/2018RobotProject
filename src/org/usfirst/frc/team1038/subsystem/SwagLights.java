package org.usfirst.frc.team1038.subsystem;

import org.usfirst.frc.team1038.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SwagLights extends Subsystem {
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
	private final String DISPOSING = "p";
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
	
	private SwagLights() {
	}
	
	/**
	 * Changes the state of the wheel well lights
	 * @param state state to change the wheel well lights to
	 */
	public void setWheelWell(WheelWellStates state) {
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
	
	/**
	 * Changes the state of the Name/Number plates
	 * @param state state to change the Name/Number plates to
	 */
	public void setNameNumer(NameNumberStates state) {
		switch (state) {
		case HighGear:
			nameNumber = HIGH_GEAR;
			break;
		case LowGear:
			nameNumber = LOW_GEAR;
			break;		
		}
	}
	
	/**
	 * Changes the state of the tower lights
	 * @param state state to change the tower lights to
	 */
	public void setTower(TowerStates state) {
		switch (state) {
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
	
	/**
	 * Call this method in robotPeriodic() to write the currently selected state to the arduino
	 */
	public void swagPeriodic() {
		String toWrite = wheelWell + "\r" + nameNumber + "\r" + tower + "\r";
		ard.writeString(toWrite);
	}	
	
	/**
	 * Call this method in Teleop and Auton periodic to update the state of the LEDs
	 */
	public void swagEnabledPeriodic() {
		if(robotDrive.isHighGear()) {
			setNameNumer(NameNumberStates.HighGear);
		} else {
			setNameNumer(NameNumberStates.LowGear);
		}
		
		/*if (elevator.getElevatorSpeed() > 1) {
			setTower(TowerStates.ElevatorUp);
		} else if (elevator.getElevatorSpeed() < 1) {
			setTower(TowerStates.ElevatorDown);*/
		/*} else*/ if (acqSco.getAcqMotorPower() > .2) {
			setTower(TowerStates.Acquiring);
		} else if (acqSco.getAcqMotorPower() < -.2) {
			setTower(TowerStates.Disposing);
		} else {
			setTower(TowerStates.Default);
		}
	}
	
	/**
	 * Call this method in Teleop and Auton Init to change the leds out of disabled mode
	 */
	public void enable() {
		Robot.disabled = false;
		Robot.eStopped = false;
		
		if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Red) {
			setWheelWell(WheelWellStates.Red);
		} else {
			setWheelWell(WheelWellStates.Blue);
		}
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
}
