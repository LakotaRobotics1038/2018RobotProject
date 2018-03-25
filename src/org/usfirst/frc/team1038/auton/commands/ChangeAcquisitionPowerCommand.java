package org.usfirst.frc.team1038.auton.commands;

import org.usfirst.frc.team1038.subsystem.AcquisitionScoring;

import edu.wpi.first.wpilibj.command.Command;

public class ChangeAcquisitionPowerCommand extends Command {
	
	private AcquisitionScoring acqSco = AcquisitionScoring.getInstance();
	private double power;
	
	/**
	 * Creates a new command to change the Acquisition Power setpoint
	 * @param power to change the acquisition to (between .4 and 1)
	 */
	public ChangeAcquisitionPowerCommand (double power) {
		this.power = power;
	}
	
	@Override
	public void execute() {
		if (power > 1)
		{
			power = 1;
		} else if (power < .4)
		{
			power = .4;
		}
		
		acqSco.setAcqSpeed(power);
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public void end() {
	}
}
