package org.usfirst.frc.team1038.robot;

import edu.wpi.first.wpilibj.DigitalInput;

public class Prox extends DigitalInput{
	
	public Prox(int port) {
		super(port);
	}
	
	/**
	 * get the state of the prox
	 * @return is there metal in front of the prox
	 */
	public boolean get() {
		return !super.get();
	}
}
