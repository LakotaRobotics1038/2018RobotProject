package org.usfirst.frc.team1038.auton;

import org.usfirst.frc.team1038.auton.commands.AcquisitionAngleCommand;
import org.usfirst.frc.team1038.auton.commands.DriveStraightCommand;
import org.usfirst.frc.team1038.subsystem.AcquisitionScoring;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ForwardAuton {
	
	private CommandGroup group;
	
	public ForwardAuton()
	{
		group = new CommandGroup();
	}
	
	public CommandGroup select()
	{
		group.addSequential(new DriveStraightCommand(11));
		group.addSequential(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
		return group;
	}
}
