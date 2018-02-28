package org.usfirst.frc.team1038.robot;

import edu.wpi.first.wpilibj.DigitalInput;

public class Prox {
	private DigitalInput prox;
	
	public Prox(int port)
	{
		prox = new DigitalInput(port);
	}
	
	public boolean get()
	{
		return !prox.get();
	}
}
