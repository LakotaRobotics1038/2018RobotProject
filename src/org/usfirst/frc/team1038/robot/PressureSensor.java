package org.usfirst.frc.team1038.robot;

import edu.wpi.first.wpilibj.AnalogInput;

public class PressureSensor extends AnalogInput {

	private final double RIO_VOLTAGE = 5;

	public PressureSensor(int port) {
		super(port);
	}

	/**
	 * Calculate the pressure of the system
	 * @return the pressure of the system
	 */
	public double getPressure() {
		return Math.round(250 * (getVoltage() / RIO_VOLTAGE) - 20);
	}
}
