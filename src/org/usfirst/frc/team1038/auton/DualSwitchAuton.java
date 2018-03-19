package org.usfirst.frc.team1038.auton;

import org.usfirst.frc.team1038.auton.commands.AcquisitionAngleCommand;
import org.usfirst.frc.team1038.auton.commands.DriveStraightCommand;
import org.usfirst.frc.team1038.subsystem.AcquisitionScoring;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DualSwitchAuton {
	private CommandGroup group;
	private String position;
	private String gameData;
	
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
				group.addSequential(new DriveStraightCommand(11));
				group.addSequential(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
				break;
			case AutonSelector.kRightPosition:
				break;
			case AutonSelector.kCenterPosition:
				break;
		}
		return group;
	}
}
