package org.usfirst.frc.team1038.robot;

import edu.wpi.first.wpilibj.I2C;

public class I2CGyro {
	//VARIABLES
	private final int SENSOR_ID_CODE = 0x02;
	private final int COMMAND = 0x03;
	private final int HEADING_DATA = 0x04;
	private final int INTEGRATED_Z_VALUE = 0x06;
	private final int RAW_X_VALUE = 0x08;
	private final int RAW_Y_VALUE = 0x0A;
	private final int RAW_Z_VALUE = 0x0C;
	private final int Z_AXIS_OFFSET = 0x0E;
	private final int DEVICE_ADDRESS = 0x10;
	private final int NORMAL_MEASUREMENT_MODE = 0x00;
	private final int GYRO_RECALIBRATE = 0x4E;
	private final int RESET_Z_AXIS_INTEGRATOR = 0x52;
	private I2C I2CBus;
	private static I2CGyro gyroSensor = new I2CGyro();
	private double gyroVal;
	
	//Class constructor
	public I2CGyro()
	{
		I2CBus = new I2C(I2C.Port.kOnboard, DEVICE_ADDRESS);
		//I2CBus.write(0x10, 22);
		I2CBus.write(COMMAND, GYRO_RECALIBRATE);
		I2CBus.write(SENSOR_ID_CODE, NORMAL_MEASUREMENT_MODE);
		System.out.println("Gyro Calibrated");
	}
	
	public static I2CGyro getInstance() {
		if (gyroSensor == null) {
			System.out.println("Creating a new gyroSensor");
			gyroSensor = new I2CGyro();
		}
		return gyroSensor;
	}
	
	//Reads and translates input from the Gyro into a value from 0-359	
	public double getAngle() {
		byte[] dataBuffer = new byte[6];
		
	    if (I2CBus == null) {
	      return 102.7;
	    }
	    I2CBus.read(COMMAND, 6, dataBuffer);
		if (dataBuffer[1] >= 0) {
	    	gyroVal = dataBuffer[1];
		} else {
			gyroVal = 256 + dataBuffer[1];
		}
		if (dataBuffer[2] > 0) {
			gyroVal = 256 + gyroVal;
		}
		
		while (gyroVal < 0) {
			gyroVal = gyroVal + 360;
		}
		while (gyroVal > 359) {
		   gyroVal = gyroVal - 360;	
		}
		
	    return gyroVal;
	  }
	
	//Sets current gyro value to 0
	public void resetGyro()
	{
		I2CBus.write(COMMAND, RESET_Z_AXIS_INTEGRATOR);
		I2CBus.write(SENSOR_ID_CODE, NORMAL_MEASUREMENT_MODE);
	}
	
	public void recalibrateGyro()
	{
		I2CBus.write(COMMAND, GYRO_RECALIBRATE);
		I2CBus.write(SENSOR_ID_CODE, NORMAL_MEASUREMENT_MODE);
	}
}
