package org.usfirst.frc.team1038.auton.commands;

import org.usfirst.frc.team1038.subsystem.AcquisitionScoring;

import edu.wpi.first.wpilibj.command.Command;

public class AcquisitonOpenCloseCommand extends Command {
	private AcquisitionScoring acqSco = AcquisitionScoring.getInstance();
	private boolean changeOpen;
	
	public AcquisitonOpenCloseCommand (boolean changeOpen)
	{
		this.changeOpen = changeOpen;
	}
	
	public void execute()
	{
		if (changeOpen)
		{
			acqSco.openArms();
		}
		else
		{
			acqSco.closeArms();
		}
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public void end()
	{
		
	}
}
