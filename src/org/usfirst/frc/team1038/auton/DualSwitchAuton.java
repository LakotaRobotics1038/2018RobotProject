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

public class DualSwitchAuton {
	
	private CommandGroup group;
	private String position;
	private String gameData;
	
	private final double DIST_FROM_SIDE_SWITCH_TO_BW_SW_AND_SCALE = 5;
	private final double DIST_TO_COLLECT_CUBE = 3; //TODO check this value
	private final double DIST_TO_BACK_AWAY = 1;
	private final double ACQUIRE_TIME = 2;
	private final double DISPOSE_TIME = 1.5;
	
	public DualSwitchAuton(String positionIn, String gameDataIn)
	{
		group = new CommandGroup();
		position = positionIn;
		gameData = gameDataIn;
	}
	
	public CommandGroup select()
	{
		switch (position)
		{
			case AutonSelector.kLeftPosition:
				group = new SingleSwitchAuton(position, gameData).select();
				if (gameData.substring(0, 1) == "L")
				{
					group.addSequential(new DriveStraightCommand(DIST_FROM_SIDE_SWITCH_TO_BW_SW_AND_SCALE));
					group.addSequential(new TurnCommand(135));
					group.addSequential(new TurnCommandVision());
					group.addParallel(new AcquisitonOpenCloseCommand(States.Open));
					group.addSequential(new DriveStraightCommand(DIST_TO_COLLECT_CUBE));
					group.addSequential(new AcquisitonOpenCloseCommand(States.Close));
					group.addParallel(new AcquireCommand(Modes.Acquire, ACQUIRE_TIME));
					group.addSequential(new ElevatorCommand(Elevator.SWITCH));
					group.addSequential(new AcquireCommand(Modes.Dispose, DISPOSE_TIME));
					group.addSequential(new DriveStraightCommand(DIST_TO_BACK_AWAY));
					group.addParallel(new ElevatorCommand(Elevator.FLOOR));
					group.addSequential(new TurnCommand(0));
				}
				break;
			case AutonSelector.kRightPosition:
				group = new SingleSwitchAuton(position, gameData).select();
				if (gameData.substring(0, 1) == "R")
				{
					group.addSequential(new DriveStraightCommand(DIST_FROM_SIDE_SWITCH_TO_BW_SW_AND_SCALE));
					group.addSequential(new TurnCommand(215));
					group.addSequential(new TurnCommandVision());
					group.addParallel(new AcquisitonOpenCloseCommand(States.Open));
					group.addSequential(new DriveStraightCommand(DIST_TO_COLLECT_CUBE));
					group.addSequential(new AcquisitonOpenCloseCommand(States.Close));
					group.addParallel(new AcquireCommand(Modes.Acquire, ACQUIRE_TIME));
					group.addSequential(new ElevatorCommand(Elevator.SWITCH));
					group.addSequential(new AcquireCommand(Modes.Dispose, DISPOSE_TIME));
					group.addSequential(new DriveStraightCommand(DIST_TO_BACK_AWAY));
					group.addParallel(new ElevatorCommand(Elevator.FLOOR));
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