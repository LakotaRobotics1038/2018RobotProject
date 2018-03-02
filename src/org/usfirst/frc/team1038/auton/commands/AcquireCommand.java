package org.usfirst.frc.team1038.auton.commands;

import org.usfirst.frc.team1038.subsystem.AcquisitionScoring;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class AcquireCommand extends Command {
	private AcquisitionScoring acqSco = AcquisitionScoring.getInstance();
	private boolean acquiring;
	private double acqTime;
	private boolean timerRunning = false;
	private Timer timer = new Timer();
	
	public AcquireCommand(boolean acquiring, double acqTime)
	{
		this.acquiring = acquiring;
		this.acqTime = acqTime;
	}
	
	public void execute()
	{
		if (acquiring)
		{
			acqSco.aquire();
		}
		else
		{
			acqSco.dispose();
		}
		
		if (!timerRunning)
		{
			timerRunning = true;
			timer.start();
		}
		System.out.println(timer.get());
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
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
