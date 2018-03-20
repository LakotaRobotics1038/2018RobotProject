package org.usfirst.frc.team1038.auton;

import org.usfirst.frc.team1038.auton.commands.AcquireCommand;
import org.usfirst.frc.team1038.auton.commands.AcquireCommand.Modes;
import org.usfirst.frc.team1038.auton.commands.AcquisitionAngleCommand;
import org.usfirst.frc.team1038.auton.commands.DriveStraightCommand;
import org.usfirst.frc.team1038.auton.commands.ElevatorCommand;
import org.usfirst.frc.team1038.auton.commands.TurnCommand;
import org.usfirst.frc.team1038.subsystem.AcquisitionScoring;
import org.usfirst.frc.team1038.subsystem.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SingleSwitchAuton {
	
	private CommandGroup group;
	private String position;
	private String gameData;
	
	private final double DIST_FROM_DS_WALL_TO_SWITCH = 14;
	private final double DIST_TO_APPROACH_SWITCH_FROM_SIDE = 2;
	private final double DIST_FROM_DS_WALL_TO_CUBE_PILE = 6;
	private final double DIST_FROM_CUBE_PILE_TO_SWITCH = 5;
	private final double DIST_TO_APPROACH_SWITCH_FROM_FRONT = 4.5;
	private final double DIST_TO_BACK_AWAY = 1;
	private final double DISPOSE_TIME = 1.5;
	
	public SingleSwitchAuton(String positionIn, String gameDataIn)
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
				if (gameData.substring(0, 1) == "L")
				{
					group.addSequential(new DriveStraightCommand(DIST_FROM_DS_WALL_TO_SWITCH));
					group.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
					group.addSequential(new TurnCommand(90));
					group.addSequential(new DriveStraightCommand(DIST_TO_APPROACH_SWITCH_FROM_SIDE));
					group.addParallel(new ElevatorCommand(Elevator.SWITCH));
					group.addSequential(new AcquireCommand(Modes.Dispose, DISPOSE_TIME));
					group.addSequential(new DriveStraightCommand(-DIST_TO_BACK_AWAY));
					group.addSequential(new TurnCommand(0));
					group.addParallel(new ElevatorCommand(Elevator.FLOOR));
				}
				else
				{
					group = new ForwardAuton().select();
				}
				break;
			case AutonSelector.kRightPosition:
				if (gameData.substring(0, 1) == "R")
				{
					group.addSequential(new DriveStraightCommand(DIST_FROM_DS_WALL_TO_SWITCH));
					group.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
					group.addSequential(new TurnCommand(270));
					group.addSequential(new DriveStraightCommand(DIST_TO_APPROACH_SWITCH_FROM_SIDE));
					group.addParallel(new ElevatorCommand(Elevator.SWITCH));
					group.addSequential(new AcquireCommand(Modes.Dispose, DISPOSE_TIME));
					group.addSequential(new DriveStraightCommand(-DIST_TO_BACK_AWAY));
					group.addSequential(new TurnCommand(0));
					group.addParallel(new ElevatorCommand(Elevator.FLOOR));
				}
				else
				{
					group = new ForwardAuton().select();
				}
				break;
			case AutonSelector.kCenterPosition:
				if (gameData.substring(0, 1) == "L")
				{
					group.addSequential(new DriveStraightCommand(DIST_FROM_DS_WALL_TO_CUBE_PILE));
					group.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
					group.addSequential(new TurnCommand(270));
					group.addSequential(new DriveStraightCommand(DIST_FROM_CUBE_PILE_TO_SWITCH));
					group.addSequential(new TurnCommand(0));
					group.addSequential(new DriveStraightCommand(DIST_TO_APPROACH_SWITCH_FROM_FRONT));
					group.addParallel(new ElevatorCommand(Elevator.SWITCH));
					group.addSequential(new AcquireCommand(Modes.Dispose, DISPOSE_TIME));
					group.addSequential(new DriveStraightCommand(-DIST_TO_BACK_AWAY));
					group.addParallel(new ElevatorCommand(Elevator.FLOOR));
				}
				else
				{
					group.addSequential(new DriveStraightCommand(DIST_FROM_DS_WALL_TO_CUBE_PILE));
					group.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
					group.addSequential(new TurnCommand(90));
					group.addSequential(new DriveStraightCommand(DIST_FROM_CUBE_PILE_TO_SWITCH));
					group.addSequential(new TurnCommand(0));
					group.addSequential(new DriveStraightCommand(DIST_TO_APPROACH_SWITCH_FROM_FRONT));
					group.addParallel(new ElevatorCommand(Elevator.SWITCH));
					group.addSequential(new AcquireCommand(Modes.Dispose, DISPOSE_TIME));
					group.addSequential(new DriveStraightCommand(-DIST_TO_BACK_AWAY));
					group.addParallel(new ElevatorCommand(Elevator.FLOOR));
				}
				break;
		}
		return group;
	}
}
