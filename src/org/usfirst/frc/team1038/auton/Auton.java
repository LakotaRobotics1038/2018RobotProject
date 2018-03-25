package org.usfirst.frc.team1038.auton;

import edu.wpi.first.wpilibj.command.CommandGroup;

public abstract class Auton {
	
	protected String position;
	protected String gameData;
	protected CommandGroup group;
	
	/**
	 * Creates a new auton with data
	 * @param positionIn The position of the robot on the field
	 * @param gameDataIn Game data from FMS
	 */
	public Auton (String positionIn, String gameDataIn){
		position = positionIn;
		gameData = gameDataIn;
		group = new CommandGroup();
	}
	
	/**
	 * Creates a new auton without data
	 */
	public Auton()
	{
		group = new CommandGroup();
	}
	
	/**
	 * Return command group with auton steps
	 * @return command group with auton steps
	 */
	public abstract CommandGroup select();
}
