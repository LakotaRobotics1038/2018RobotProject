package org.usfirst.frc.team1038.auton;

import org.usfirst.frc.team1038.auton.commands.AcquisitionAngleCommand;
import org.usfirst.frc.team1038.auton.commands.AcquisitonOpenCloseCommand;
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
				group.addSequential(new DriveStraightCommand(14));
				group.addSequential(new TurnCommand(90));
				group.addSequential(new DriveStraightCommand(2));
				group.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
				group.addParallel(new ElevatorCommand(Elevator.SWITCH));
				group.addSequential(new AcquisitonOpenCloseCommand(true));
				group.addSequential(new DriveStraightCommand(-2));
				group.addSequential(new TurnCommand(270));
				group.addSequential(new DriveStraightCommand(5));
				group.addParallel(new AcquisitionAngleCommand(0));
				
				break;
			case AutonSelector.kRightPosition:
				break;
			case AutonSelector.kCenterPosition:
				break;
		}
		return group;
	}
}
