package org.usfirst.frc.team1038.auton.commands;

import org.usfirst.frc.team1038.subsystem.AcquisitionScoring;

import edu.wpi.first.wpilibj.command.Command;

public class AcquisitionAngleCommand extends Command{

	private AcquisitionScoring acqSco = AcquisitionScoring.getInstance();
	
	/**
	 * Creates a new command to angle the acquisition arms
	 * @param setpoint to change the angle to (This should come from a public final in AcquisitionScoring.java)
	 */
	public AcquisitionAngleCommand(int setpoint) {
		acqSco.setSetpoint(setpoint);
	}
	
	@Override
	public void execute() {
		acqSco.enable();
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return acqSco.onTarget();
	}
	
	@Override
	public void end() {
		
	}
}
