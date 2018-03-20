package org.usfirst.frc.team1038.robot;

import org.usfirst.frc.team1038.subsystem.AcquisitionScoring;
import org.usfirst.frc.team1038.subsystem.DriveTrain;
import org.usfirst.frc.team1038.subsystem.Elevator;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Dashboard {
	
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
	
	private Dashboard()
	{
		SmartDashboard.putBoolean("Drivers/Reset Arm Encoder", false);
		SmartDashboard.putBoolean("Drivers/Reset Elevator Encoder", false);
	}
	
	public void update(double lowPressure, double highPressure)
	{
		//SmartDashboard.putNumber("Gyro", I2CGyro.getInstance().getAngle());
		SmartDashboard.putData("Drivers/Gyro", I2CGyro.getInstance());
		SmartDashboard.putNumber("Drivers/Left Encoder", DriveTrain.getInstance().getLeftDriveEncoderDistance());
		SmartDashboard.putNumber("Drivers/Right Encoder", DriveTrain.getInstance().getRightDriveEncoderDistance());
		SmartDashboard.putNumber("Drivers/High Pressure", highPressure);
		SmartDashboard.putNumber("Drivers/Low Pressure", lowPressure);
		SmartDashboard.putNumber("Drivers/Elevator Encoder", Elevator.getInstance().getEncoderCount());
		SmartDashboard.putNumber("Drivers/Arm Encoder", AcquisitionScoring.getInstance().getUpDownEncoder());
		//SmartDashboard.putNumber("Elevator Motor", Elevator.getInstance().getMotorOutput());
		//SmartDashboard.putNumber("Elevator Setpoint", Elevator.getInstance().getSetpoint());
		SmartDashboard.putNumber("Drivers/Acq Motor Speed", AcquisitionScoring.getInstance().getAcqSpeed());
		//SmartDashboard.putNumber("Acq Arms SP", AcquisitionScoring.getInstance().getSetpoint());
		
		if (SmartDashboard.getBoolean("Drivers/Reset Arm Encoder", false))
		{
			AcquisitionScoring.getInstance().armsUpDownSetMotor(-1);
			AcquisitionScoring.getInstance().resetUpDownEncoder();
			if (!timerRunning)
			{
				timer.start();
				timerRunning = true;
			}
			if (timer.get() > 1)
			{
				SmartDashboard.putBoolean("Drivers/Reset Arm Encoder", false);
				AcquisitionScoring.getInstance().armsUpDownSetMotor(-0);
				timer.stop();
				timerRunning = false;
				timer.reset();
			}
			//System.out.println(pdp.getCurrent(7));
		}
		
		if (SmartDashboard.getBoolean("Drivers/Reset Elevator Encoder", false))
		{
			Elevator.getInstance().resetEncoder();
			SmartDashboard.putBoolean("Drivers/Reset Elevator Encoder", false);
		}
		
		position = Robot.startPosition.getSelected();
		autonChooser = Robot.autoChooser.getSelected();
	}
	
	public String getPosition() {
		return position;
	}
	
	public String getAutonChooser() {
		return autonChooser;
	}
}
