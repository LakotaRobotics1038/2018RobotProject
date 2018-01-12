package org.usfirst.frc.team1038.robot;

import edu.wpi.first.wpilibj.Encoder;

public class Encoder1038 {
	
	private Encoder encoder;
	
	public Encoder1038(int channelA, int channelB, boolean isInverted)
	{
		encoder = new Encoder(channelA, channelB, isInverted);
	}
	
	public Encoder1038(int channelA, int channelB, int countsPerRevolution, double wheelDiameter)
	{
		encoder = new Encoder(channelA, channelB);
		encoder.setDistancePerPulse(findDistancePerPulse(countsPerRevolution, wheelDiameter));
	}
	
	public Encoder1038(int channelA, int channelB, boolean isInverted, int countsPerRevolution, double wheelDiameter)
	{
		encoder = new Encoder(channelA, channelB, isInverted);
		encoder.setDistancePerPulse(findDistancePerPulse(countsPerRevolution, wheelDiameter));
	}
	
	public int getCount()
	{
		return encoder.get();
	}
	
	public double getRate()
	{
		return encoder.getRate();
	}
	
	public double getDistance()
	{
		return encoder.getDistance();
	}
	
	private double findDistancePerPulse(int countsPerRevolution, double wheelDiameter)
	{		
		return (1 / countsPerRevolution ) * (wheelDiameter * Math.PI);
	}
}