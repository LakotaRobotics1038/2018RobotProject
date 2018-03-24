package org.usfirst.frc.team1038.auton;

import org.usfirst.frc.team1038.auton.commands.AcquisitionAngleCommand;
import org.usfirst.frc.team1038.auton.commands.DriveStraightCommand;
import org.usfirst.frc.team1038.subsystem.AcquisitionScoring;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ForwardAuton {
	
	private CommandGroup group;
	
	private final double DIST_TO_BASELINE_FROM_DS_WALL = 11;
	
	public ForwardAuton()
	{
		group = new CommandGroup();
	}
	
	public CommandGroup select()
	{
		group.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
		group.addSequential(new DriveStraightCommand(DIST_TO_BASELINE_FROM_DS_WALL));
		return group;
	}
}
