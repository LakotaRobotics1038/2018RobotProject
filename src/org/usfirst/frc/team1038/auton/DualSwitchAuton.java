package org.usfirst.frc.team1038.auton;

import org.usfirst.frc.team1038.auton.commands.AcquisitonOpenCloseCommand;
import org.usfirst.frc.team1038.auton.commands.AcquisitonOpenCloseCommand.States;
import org.usfirst.frc.team1038.auton.commands.DriveStraightCommand;
import org.usfirst.frc.team1038.auton.commands.ElevatorCommand;
import org.usfirst.frc.team1038.auton.commands.TurnCommand;
import org.usfirst.frc.team1038.auton.commands.TurnCommandVision;
import org.usfirst.frc.team1038.auton.commands.AcquireCommand;
import org.usfirst.frc.team1038.auton.commands.AcquireCommand.Modes;
import org.usfirst.frc.team1038.subsystem.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DualSwitchAuton extends Auton {
	
	private final double DIST_FROM_SIDE_SWITCH_TO_BW_SW_AND_SCALE = 5;
	private final double DIST_TO_COLLECT_CUBE = 3; //TODO check this value
	private final double DIST_TO_BACK_AWAY = 1;
	private final double ACQUIRE_TIME = 2;
	private final double DISPOSE_TIME = 1.5;
	
	/**
	 * Creates a new dual switch auton
	 * @param positionIn The position of the robot on the field
	 * @param gameDataIn Game data from FMS
	 */
	public DualSwitchAuton(String positionIn, String gameDataIn) {
		super(positionIn, gameDataIn);
	}
	
	@Override
	public CommandGroup select() {
		switch (position) {
			case AutonSelector.kLeftPosition:
				group = new SingleSwitchAuton(position, gameData).select();
				if (gameData.substring(0, 1).equals("L")) {
					group.addSequential(new DriveStraightCommand(DIST_FROM_SIDE_SWITCH_TO_BW_SW_AND_SCALE));
					group.addSequential(new TurnCommand(135));					
					group.addParallel(new AcquisitonOpenCloseCommand(States.Open));
					group.addSequential(new TurnCommandVision());
					group.addSequential(new DriveStraightCommand(DIST_TO_COLLECT_CUBE));					
					group.addParallel(new AcquireCommand(Modes.Acquire, ACQUIRE_TIME));
					group.addSequential(new AcquisitonOpenCloseCommand(States.Close));
					group.addSequential(new ElevatorCommand(Elevator.SWITCH));
					group.addSequential(new AcquireCommand(Modes.Dispose, DISPOSE_TIME));					
					group.addParallel(new ElevatorCommand(Elevator.FLOOR));
					group.addSequential(new DriveStraightCommand(DIST_TO_BACK_AWAY));
					group.addSequential(new TurnCommand(0));
				}
				break;
			case AutonSelector.kRightPosition:
				group = new SingleSwitchAuton(position, gameData).select();
				if (gameData.substring(0, 1).equals("R")) {
					group.addSequential(new DriveStraightCommand(DIST_FROM_SIDE_SWITCH_TO_BW_SW_AND_SCALE));
					group.addSequential(new TurnCommand(215));
					group.addParallel(new AcquisitonOpenCloseCommand(States.Open));
					group.addSequential(new TurnCommandVision());				
					group.addSequential(new DriveStraightCommand(DIST_TO_COLLECT_CUBE));
					group.addParallel(new AcquireCommand(Modes.Acquire, ACQUIRE_TIME));
					group.addSequential(new AcquisitonOpenCloseCommand(States.Close));					
					group.addSequential(new ElevatorCommand(Elevator.SWITCH));
					group.addSequential(new AcquireCommand(Modes.Dispose, DISPOSE_TIME));
					group.addParallel(new ElevatorCommand(Elevator.FLOOR));
					group.addSequential(new DriveStraightCommand(DIST_TO_BACK_AWAY));					
					group.addSequential(new TurnCommand(0));
				}
				break;
			case AutonSelector.kCenterPosition:
				group = new ForwardAuton().select();
				break;
		}
		return group;
	}
}
