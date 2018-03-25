package org.usfirst.frc.team1038.subsystem;

import org.usfirst.frc.team1038.robot.I2CGyro;
import org.usfirst.frc.team1038.robot.Robot;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Dashboard extends SmartDashboard {
	
	private String position;
	private String autonChooser;
	private static Dashboard dashboard;
	private Timer timer = new Timer();
	private boolean timerRunning = false;
	//private PowerDistributionPanel pdp = new PowerDistributionPanel(1);
	
	public static Dashboard getInstance() {
		if (dashboard == null) {
			System.out.println("Creating a new Dashboard");
			dashboard = new Dashboard();
		}
		return dashboard;
	}
	
	private Dashboard() {
		putBoolean("Drivers/Reset Arm Encoder", false);
		putBoolean("Drivers/Reset Elevator Encoder", false);
		putBoolean("Controls/Reset Gyro", false);
		putBoolean("Controls/Recal Gyro", false);
	}
	
	/**
	 * updates the dashboard for all values
	 * @param lowPressure value of the low pressure sensor
	 * @param highPressure value of the high pressure sensor
	 */
	public void update(double lowPressure, double highPressure) {
		//putNumber("Gyro", I2CGyro.getInstance().getAngle());
		putData("Drivers/Gyro", I2CGyro.getInstance());
		putNumber("Drivers/Left Encoder", DriveTrain.getInstance().getLeftDriveEncoderDistance());
		putNumber("Drivers/Right Encoder", DriveTrain.getInstance().getRightDriveEncoderDistance());
		putNumber("Drivers/High Pressure", highPressure);
		putNumber("Drivers/Low Pressure", lowPressure);
		putNumber("Drivers/Elevator Encoder", Elevator.getInstance().getEncoderCount());
		putNumber("Drivers/Arm Encoder", AcquisitionScoring.getInstance().getUpDownEncoder());
		//putNumber("Elevator Motor", Elevator.getInstance().getMotorOutput());
		//putNumber("Elevator Setpoint", Elevator.getInstance().getSetpoint());
		putNumber("Drivers/Acq Motor Speed", AcquisitionScoring.getInstance().getAcqSpeed());
		//putNumber("Acq Arms SP", AcquisitionScoring.getInstance().getSetpoint());
		
		if (getBoolean("Drivers/Reset Arm Encoder", false)) {
			AcquisitionScoring.getInstance().armsUpDownSetMotor(-1);
			AcquisitionScoring.getInstance().resetUpDownEncoder();
			if (!timerRunning) {
				timer.start();
				timerRunning = true;
			}
			if (timer.get() > 1) {
				putBoolean("Drivers/Reset Arm Encoder", false);
				AcquisitionScoring.getInstance().armsUpDownSetMotor(-0);
				timer.stop();
				timerRunning = false;
				timer.reset();
			}
			//System.out.println(pdp.getCurrent(7));
		}
		
		if (getBoolean("Drivers/Reset Elevator Encoder", false)) {
			Elevator.getInstance().resetEncoder();
			putBoolean("Drivers/Reset Elevator Encoder", false);
		}
		
		if (getBoolean("Controls/Reset Gyro", false)) {
			I2CGyro.getInstance().reset();
			putBoolean("Controls/Reset Gyro", false);
		}
		
		if (getBoolean("Controls/Recal Gyro", false)) {
			I2CGyro.getInstance().calibrate();
			putBoolean("Controls/Recal Gyro", false);
		}
		
		position = Robot.startPosition.getSelected();
		autonChooser = Robot.autoChooser.getSelected();
	}
	
	/**
	 * Get the position data from the dashboard
	 * @return the position data from the dashboard
	 */
	public String getPosition() {
		return position;
	}
	
	/**
	 * Get the auton choice from the dashboard
	 * @return the auton choice from the dashboard
	 */
	public String getAutonChooser() {
		return autonChooser;
	}
}
