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
	
	public AcquireCommand(Modes mode, double acqTime)
	{
		
		this.mode = mode;
		this.acqTime = acqTime;
	}
	
	public void execute()
	{
		switch (mode)
		{
			case Acquire:
				acqSco.aquire();
			case Dispose:
				acqSco.disable();
		}
		
		if (!timerRunning)
		{
			timerRunning = true;
			timer.start();
		}
		//System.out.println(timer.get());
	}
	
	@Override
	protected boolean isFinished() {
		return timer.get() > acqTime;
	}
	
	public void interrupted()
	{
		end();
	}
	
	public void end()
	{
		acqSco.stop();
	}
}
