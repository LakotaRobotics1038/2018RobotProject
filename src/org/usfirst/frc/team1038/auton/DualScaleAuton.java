package org.usfirst.frc.team1038.auton;

import org.usfirst.frc.team1038.auton.commands.AcquisitionAngleCommand;
import org.usfirst.frc.team1038.auton.commands.AcquisitonOpenCloseCommand;
import org.usfirst.frc.team1038.auton.commands.AcquisitonOpenCloseCommand.States;
import org.usfirst.frc.team1038.auton.commands.DriveStraightCommand;
import org.usfirst.frc.team1038.auton.commands.ElevatorCommand;
import org.usfirst.frc.team1038.auton.commands.TurnCommand;
import org.usfirst.frc.team1038.auton.commands.TurnCommandVision;
import org.usfirst.frc.team1038.auton.commands.AcquireCommand;
import org.usfirst.frc.team1038.auton.commands.AcquireCommand.Modes;
import org.usfirst.frc.team1038.subsystem.AcquisitionScoring;
import org.usfirst.frc.team1038.subsystem.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DualScaleAuton {
	
	private CommandGroup group;
	private String position;
	private String gameData;
	
	private final double DIST_TO_CUBE_FROM_SCALE = 6; //TODO Check this value
	private final double DIST_TO_ACQUIRE = 1;
	private final double ACQUIRE_TIME = 1.5;
	private final double DISPOSE_TIME = 1.5;
	
	public DualScaleAuton(String positionIn, String gameDataIn)
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
				group = new SingleScaleAuton(position, gameData).select();
				if (gameData.substring(1, 2).equals("L"))
				{
					group.addSequential(new TurnCommand(135));
					group.addSequential(new DriveStraightCommand(DIST_TO_CUBE_FROM_SCALE));
					group.addParallel(new AcquisitonOpenCloseCommand(States.Open));
					group.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_ZERO));
					group.addSequential(new TurnCommandVision());
					group.addSequential(new DriveStraightCommand(DIST_TO_ACQUIRE));
					group.addSequential(new AcquisitonOpenCloseCommand(States.Close));
					group.addParallel(new AcquireCommand(Modes.Acquire, ACQUIRE_TIME));
					group.addSequential(new DriveStraightCommand(-DIST_TO_ACQUIRE));
					group.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
					group.addSequential(new TurnCommand(0));
					group.addSequential(new DriveStraightCommand(DIST_TO_CUBE_FROM_SCALE));
					group.addSequential(new TurnCommand(45));
				}
				else
				{
					group.addSequential(new TurnCommand(215));
					group.addSequential(new DriveStraightCommand(DIST_TO_CUBE_FROM_SCALE));
					group.addParallel(new AcquisitonOpenCloseCommand(States.Open));
					group.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_ZERO));
					group.addSequential(new TurnCommandVision());
					group.addSequential(new DriveStraightCommand(DIST_TO_ACQUIRE));
					group.addSequential(new AcquisitonOpenCloseCommand(States.Close));
					group.addParallel(new AcquireCommand(Modes.Acquire, ACQUIRE_TIME));
					group.addSequential(new DriveStraightCommand(-DIST_TO_ACQUIRE));
					group.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
					group.addSequential(new TurnCommand(0));
					group.addSequential(new DriveStraightCommand(DIST_TO_CUBE_FROM_SCALE));
					group.addSequential(new TurnCommand(315));	
				}
				group.addSequential(new ElevatorCommand(Elevator.SCALE_HIGH));
				group.addSequential(new AcquireCommand(Modes.Dispose, DISPOSE_TIME));
				group.addSequential(new ElevatorCommand(Elevator.FLOOR));
				break;
			case AutonSelector.kRightPosition:
				group = new SingleScaleAuton(position, gameData).select();
				if (gameData.substring(1, 2).equals("R"))
				{
					group.addSequential(new TurnCommand(215));
					group.addSequential(new DriveStraightCommand(DIST_TO_CUBE_FROM_SCALE));
					group.addParallel(new AcquisitonOpenCloseCommand(States.Open));
					group.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_ZERO));
					group.addSequential(new TurnCommandVision());
					group.addSequential(new DriveStraightCommand(DIST_TO_ACQUIRE));
					group.addSequential(new AcquisitonOpenCloseCommand(States.Close));
					group.addParallel(new AcquireCommand(Modes.Acquire, ACQUIRE_TIME));
					group.addSequential(new DriveStraightCommand(-DIST_TO_ACQUIRE));
					group.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
					group.addSequential(new TurnCommand(0));
					group.addSequential(new DriveStraightCommand(DIST_TO_CUBE_FROM_SCALE));
					group.addSequential(new TurnCommand(315));
				}
				else
				{
					group.addSequential(new TurnCommand(135));
					group.addSequential(new DriveStraightCommand(DIST_TO_CUBE_FROM_SCALE));
					group.addParallel(new AcquisitonOpenCloseCommand(States.Open));
					group.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_ZERO));
					group.addSequential(new TurnCommandVision());
					group.addSequential(new DriveStraightCommand(DIST_TO_ACQUIRE));
					group.addSequential(new AcquisitonOpenCloseCommand(States.Close));
					group.addParallel(new AcquireCommand(Modes.Acquire, ACQUIRE_TIME));
					group.addSequential(new DriveStraightCommand(-DIST_TO_ACQUIRE));
					group.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
					group.addSequential(new TurnCommand(0));
					group.addSequential(new DriveStraightCommand(DIST_TO_CUBE_FROM_SCALE));
					group.addSequential(new TurnCommand(45));	
				}
				group.addSequential(new ElevatorCommand(Elevator.SCALE_HIGH));
				group.addSequential(new AcquireCommand(Modes.Dispose, DISPOSE_TIME));
				group.addSequential(new ElevatorCommand(Elevator.FLOOR));
				break;
			case AutonSelector.kCenterPosition:
				group = new ForwardAuton().select();
				break;
		}
		return group;
	}
}
