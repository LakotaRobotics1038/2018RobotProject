package org.usfirst.frc.team1038.auton.commands;

import org.usfirst.frc.team1038.subsystem.AcquisitionScoring;

import edu.wpi.first.wpilibj.command.Command;

public class ResetArmEncoderCommand extends Command {

//	private static ResetArmEncoderCommand command;
//	
//	public static ResetArmEncoderCommand getInstance()
//	{
//		if (command == null) {
//			System.out.println("Creating a new ResetArmEncoderCommand");
//			command = new ResetArmEncoderCommand();
//		}
//		return command;
//	}
	
	public ResetArmEncoderCommand()
	{
		execute();
	}
	
	public void excecute()
	{
		AcquisitionScoring.getInstance().resetUpDownEncoder();
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return AcquisitionScoring.getInstance().getUpDownEncoder() == 0;
	}

}
