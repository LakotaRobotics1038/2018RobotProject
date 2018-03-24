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
	private final double DIST_FROM_DS_WALL_TO_CUBE_PILE = 4.5;
	private final double DIST_FROM_CUBE_PILE_TO_SWITCH = 5;
	private final double DIST_TO_APPROACH_SWITCH_FROM_FRONT = 3.5;
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
				if (gameData.substring(0, 1).equals("L"))
				{
					group.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
					group.addSequential(new DriveStraightCommand(DIST_FROM_DS_WALL_TO_SWITCH));
					group.addSequential(new TurnCommand(90));
					group.addParallel(new ElevatorCommand(Elevator.SWITCH));
					group.addSequential(new DriveStraightCommand(DIST_TO_APPROACH_SWITCH_FROM_SIDE));
					group.addSequential(new AcquireCommand(Modes.Dispose, DISPOSE_TIME));
					group.addSequential(new DriveStraightCommand(-DIST_TO_BACK_AWAY));
					group.addParallel(new ElevatorCommand(Elevator.FLOOR));
					group.addSequential(new TurnCommand(0));					
				}
				else
				{
					group = new ForwardAuton().select();
				}
				break;
			case AutonSelector.kRightPosition:
				if (gameData.substring(0, 1).equals("R"))
				{
					group.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
					group.addSequential(new DriveStraightCommand(DIST_FROM_DS_WALL_TO_SWITCH));
					group.addSequential(new TurnCommand(270));
					group.addParallel(new ElevatorCommand(Elevator.SWITCH));
					group.addSequential(new DriveStraightCommand(DIST_TO_APPROACH_SWITCH_FROM_SIDE));
					group.addSequential(new AcquireCommand(Modes.Dispose, DISPOSE_TIME));
					group.addSequential(new DriveStraightCommand(-DIST_TO_BACK_AWAY));
					group.addParallel(new ElevatorCommand(Elevator.FLOOR));
					group.addSequential(new TurnCommand(0));
				}
				else
				{
					group = new ForwardAuton().select();
				}
				break;
			case AutonSelector.kCenterPosition:
				if (gameData.substring(0, 1).equals("L"))
				{
					group.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
					group.addSequential(new DriveStraightCommand(DIST_FROM_DS_WALL_TO_CUBE_PILE));				
					group.addSequential(new TurnCommand(270));
					group.addSequential(new DriveStraightCommand(DIST_FROM_CUBE_PILE_TO_SWITCH));
					group.addSequential(new TurnCommand(0));
					group.addParallel(new ElevatorCommand(Elevator.SWITCH));
					group.addSequential(new DriveStraightCommand(DIST_TO_APPROACH_SWITCH_FROM_FRONT));
					group.addSequential(new AcquireCommand(Modes.Dispose, DISPOSE_TIME));
					group.addParallel(new ElevatorCommand(Elevator.FLOOR));
					group.addSequential(new DriveStraightCommand(-DIST_TO_BACK_AWAY));
				}
				else
				{
					group.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
					group.addSequential(new DriveStraightCommand(DIST_FROM_DS_WALL_TO_CUBE_PILE));
					group.addSequential(new TurnCommand(90));
					group.addSequential(new DriveStraightCommand(DIST_FROM_CUBE_PILE_TO_SWITCH));
					group.addSequential(new TurnCommand(0));
					group.addParallel(new ElevatorCommand(Elevator.SWITCH));
					group.addSequential(new DriveStraightCommand(DIST_TO_APPROACH_SWITCH_FROM_FRONT));
					group.addSequential(new AcquireCommand(Modes.Dispose, DISPOSE_TIME));
					group.addParallel(new ElevatorCommand(Elevator.FLOOR));
					group.addSequential(new DriveStraightCommand(-DIST_TO_BACK_AWAY));
				}
				break;
		}
		return group;
	}
}
