package org.usfirst.frc.team1038.robot;

public class Conversions {
    /**
     * Convert feet to meters. This is included here for static imports.
     */
    public static double f2m(double feet)
    {
    		return 0.3048 * feet;
    }
    
    /**
     * Convert meters to feet. This is included here for static imports.
     */
    public static double m2f(double meters)
    {
    		return 3.28084 * meters;
    }
}
