package org.usfirst.frc.team1038.auton.commands;

import org.usfirst.frc.team1038.subsystem.AcquisitionScoring;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class AcquireCommand extends Command {
	private AcquisitionScoring acqSco = AcquisitionScoring.getInstance();
	public enum Modes {Acquire, Dispose};
	private Modes mode;
	private double acqTime;
	private boolean timerRunning = false;
	private Timer timer = new Timer();
	
	/**
	 * Creates a new Acquire Command
	 * @param mode Determines whether the acquisition should acquire or dispose
	 * @param acqTime time to run the wheels for
	 */
	public AcquireCommand(Modes mode, double acqTime) {
		
		this.mode = mode;
		this.acqTime = acqTime;
	}
	
	@Override
	public void execute() {
		switch (mode) {
			case Acquire:
				acqSco.aquire();
			case Dispose:
				acqSco.dispose();
		}
		
		if (!timerRunning) {
			timerRunning = true;
			timer.start();
		}
		//System.out.println(timer.get());
	}
	
	@Override
	protected boolean isFinished() {
		return timer.get() > acqTime;
	}
	
	@Override
	public void interrupted() {
		end();
	}
	
	@Override
	public void end() {
		acqSco.stop();
	}
}
