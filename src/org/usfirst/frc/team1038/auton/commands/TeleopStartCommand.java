package org.usfirst.frc.team1038.auton.commands;

import org.usfirst.frc.team1038.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TeleopStartCommand extends Command {

	public TeleopStartCommand() {
		requires(Robot.robotDrive);
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

}
