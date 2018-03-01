package org.usfirst.frc.team1038.robot;

import edu.wpi.first.wpilibj.AnalogInput;

public class PressureSensor {
//	private final static int PRESSURE_SENSOR_PORT = 0;
	private final double RIO_VOLTAGE = 5;
	private AnalogInput analogInput;
//	private static PressureSensor pressureSensor;
	
	public PressureSensor(int port)
	{
		analogInput = new AnalogInput(port);
	}
	
//	public static PressureSensor getInstance() {
//		if (pressureSensor == null) {
//			System.out.println("Creating a new pressureSensor");
//			pressureSensor = new PressureSensor(PRESSURE_SENSOR_PORT);
//		}
//		return pressureSensor;
//	}
	
	public double getPressure()
	{
		
		return 250 * (analogInput.getVoltage() / RIO_VOLTAGE) - 20;
	}
	
	public double getVoltage()
	{
		return analogInput.getVoltage();
	}
}
