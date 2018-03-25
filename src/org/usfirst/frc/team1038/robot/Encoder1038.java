package org.usfirst.frc.team1038.robot;

import edu.wpi.first.wpilibj.Encoder;

public class Encoder1038 extends Encoder {
	
	/**
	 * Creates a new encoder
	 * @param channelA encoder channel A
	 * @param channelB encoder channel B
	 * @param countsPerRevolution encoder counts per wheel revolution
	 * @param wheelDiameter diameter of the wheel in any units. The units given here will determine the units of getDistance()
	 */
	public Encoder1038(int channelA, int channelB, int countsPerRevolution, double wheelDiameter) {
		super(channelA, channelB);
		setDistancePerPulse(findDistancePerPulse(countsPerRevolution, wheelDiameter));
	}
	
	/**
	 * 
	 * @param channelA
	 * @param channelB
	 * @param isInverted
	 * @param countsPerRevolution
	 * @param wheelDiameter
	 */
	public Encoder1038(int channelA, int channelB, boolean isInverted, int countsPerRevolution, double wheelDiameter) {
		super(channelA, channelB, isInverted);
		setDistancePerPulse(findDistancePerPulse(countsPerRevolution, wheelDiameter));
	}
	
	/**
	 * returns the counts of the encoder
	 * @return the counts of the encoder
	 */
	public int getCount() {
		return get();
	}
	
	/**
	 * Calculate the coefficient to convert encoder counts into distance
	 * @param countsPerRevolution Encoder counts in one revolution of the wheel
	 * @param wheelDiameter diameter of the wheel in any units. The units given here will determine the units of getDistance()
	 * @return the coefficient to convert encoder counts into distance
	 */
	public static double findDistancePerPulse(double countsPerRevolution, double wheelDiameter) {		
		return (1 / countsPerRevolution ) * (wheelDiameter * Math.PI);
	}
}