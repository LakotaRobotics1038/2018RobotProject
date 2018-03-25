package org.usfirst.frc.team1038.auton;

import org.usfirst.frc.team1038.auton.commands.AcquireCommand;
import org.usfirst.frc.team1038.auton.commands.AcquireCommand.Modes;
import org.usfirst.frc.team1038.auton.commands.AcquisitionAngleCommand;
import org.usfirst.frc.team1038.auton.commands.ChangeAcquisitionPowerCommand;
import org.usfirst.frc.team1038.auton.commands.DriveStraightCommand;
import org.usfirst.frc.team1038.auton.commands.ElevatorCommand;
import org.usfirst.frc.team1038.auton.commands.TurnCommand;
import org.usfirst.frc.team1038.subsystem.AcquisitionScoring;
import org.usfirst.frc.team1038.subsystem.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SingleScaleAuton extends Auton {
	
	private final double ACQ_POWER = 1;
	private final double DIST_TO_SCALE_FROM_DS_WALL = 23.2;
	private final double DIST_TO_BW_SW_AND_SCALE_FROM_DS_WALL = 18.7;
	private final double DIST_TO_CROSS_BW_SW_AND_SCALE = 15.5;
	private final double DIST_FROM_CROSS_TO_SCALE = 4.5;
	private final double DISPOSE_TIME = 1.5;
	
	/**
	 * Creates a new single scale auton
	 * @param positionIn The position of the robot on the field
	 * @param gameDataIn Game data from FMS
	 */
	public SingleScaleAuton(String positionIn, String gameDataIn) {
		super(positionIn, gameDataIn);
	}

	@Override
	public CommandGroup select() {
		switch (position) {
			case AutonSelector.kLeftPosition:
				if (gameData.substring(1, 2).equals("L")) {
					group.addParallel(new ChangeAcquisitionPowerCommand(ACQ_POWER));
					group.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
					group.addSequential(new DriveStraightCommand(DIST_TO_SCALE_FROM_DS_WALL));
					group.addSequential(new TurnCommand(50));
					group.addSequential(new ElevatorCommand(Elevator.SCALE_HIGH));
					group.addSequential(new AcquireCommand(Modes.Dispose, DISPOSE_TIME));
					group.addSequential(new ElevatorCommand(Elevator.FLOOR));
				} else {
					if (gameData.substring(0, 1).equals("L")) {
						group = new SingleSwitchAuton(position, gameData).select();
					} else {
						group = new ForwardAuton().select();
					}
//					group.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
//					group.addSequential(new DriveStraightCommand(DIST_TO_BW_SW_AND_SCALE_FROM_DS_WALL));				
//					group.addSequential(new TurnCommand(90));
//					group.addSequential(new DriveStraightCommand(DIST_TO_CROSS_BW_SW_AND_SCALE));
//					group.addSequential(new TurnCommand(0));
//					group.addSequential(new DriveStraightCommand(DIST_FROM_CROSS_TO_SCALE));
				}
//				group.addSequential(new ElevatorCommand(Elevator.SCALE_HIGH));
//				group.addSequential(new AcquireCommand(Modes.Dispose, DISPOSE_TIME));
//				group.addSequential(new ElevatorCommand(Elevator.FLOOR));
				break;
			case AutonSelector.kRightPosition:			
				if (gameData.substring(1, 2).equals("R")) {
					group.addParallel(new ChangeAcquisitionPowerCommand(ACQ_POWER));
					group.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
					group.addSequential(new DriveStraightCommand(DIST_TO_SCALE_FROM_DS_WALL));					
					group.addSequential(new TurnCommand(310));
					group.addSequential(new ElevatorCommand(Elevator.SCALE_HIGH));
					group.addSequential(new AcquireCommand(Modes.Dispose, DISPOSE_TIME));
					group.addSequential(new ElevatorCommand(Elevator.FLOOR));
				} else {
					if (gameData.substring(0, 1).equals("R")) {
						group = new SingleSwitchAuton(position, gameData).select();
					} else {
						group = new ForwardAuton().select();
					}
//					group.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
//					group.addSequential(new DriveStraightCommand(DIST_TO_BW_SW_AND_SCALE_FROM_DS_WALL));
//					group.addSequential(new TurnCommand(270));
//					group.addSequential(new DriveStraightCommand(DIST_TO_CROSS_BW_SW_AND_SCALE));
//					group.addSequential(new TurnCommand(0));
//					group.addSequential(new DriveStraightCommand(DIST_FROM_CROSS_TO_SCALE));
				}
//				group.addSequential(new ElevatorCommand(Elevator.SCALE_HIGH));
//				group.addSequential(new AcquireCommand(Modes.Dispose, DISPOSE_TIME));
//				group.addSequential(new ElevatorCommand(Elevator.FLOOR));
				break;
			case AutonSelector.kCenterPosition:
				group = new ForwardAuton().select();
				break;
		}
		return group;
	}
}
