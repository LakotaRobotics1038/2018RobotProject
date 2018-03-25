package org.usfirst.frc.team1038.depricated;

import edu.wpi.first.wpilibj.SerialPort;

@Deprecated
public class LaserRangeFinder {
	private SerialPort ard;
	private double[] lastReading;
	
	public LaserRangeFinder()
	{
		ard = new SerialPort(9600, SerialPort.Port.kOnboard);
	}
	
	public void write()
	{
		ard.writeString("M");
	}
	
	public String read()
	{
		/*String[] data = ard.readString().split("|", 0);
		//ard.reset();
		double[] values = new double[data.length];
		for (int i = 0; i < data.length; i++)
		{
			values[i] = Double.parseDouble(data[i]);
		}
		System.out.println(values.toString());
		lastReading = values;
		return data[0];*/
		return ard.readString();
	}
}
