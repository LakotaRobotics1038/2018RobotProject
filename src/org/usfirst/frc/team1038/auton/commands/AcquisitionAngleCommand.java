package org.usfirst.frc.team1038.auton.commands;

import org.usfirst.frc.team1038.subsystem.AcquisitionScoring;

import edu.wpi.first.wpilibj.command.Command;

public class AcquisitionAngleCommand extends Command{

	private AcquisitionScoring acqSco = AcquisitionScoring.getInstance();
	
	public AcquisitionAngleCommand(int setpoint)
	{
		acqSco.setSetpoint(setpoint);
	}
	
	public void execute()
	{
		acqSco.enable();
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return acqSco.onTarget();
	}
	
	public void end()
	{
		
	}
}
