package org.usfirst.frc.team1038.auton.commands;

import org.usfirst.frc.team1038.subsystem.AcquisitionScoring;

import edu.wpi.first.wpilibj.command.Command;

public class AcquisitonOpenCloseCommand extends Command {
	private AcquisitionScoring acqSco = AcquisitionScoring.getInstance();
	public enum States { Open, Close };
	private States state;
	
	/**
	 * Creates a new command to open or close the acquisition
	 * @param state to change the acquisition arms to
	 */
	public AcquisitonOpenCloseCommand (States state) {
		this.state = state;
	}
	
	@Override
	public void execute() {
		switch (state) {
			case Open:
				acqSco.openArms();
				break;
			case Close:
				acqSco.closeArms();
				break;
		}
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
