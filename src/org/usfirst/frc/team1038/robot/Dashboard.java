package org.usfirst.frc.team1038.robot;

import org.usfirst.frc.team1038.subsystem.AcquisitionScoring;
import org.usfirst.frc.team1038.subsystem.DriveTrain;
import org.usfirst.frc.team1038.subsystem.Elevator;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.hal.PDPJNI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Dashboard {
	
	private String position;
	private String autonChooser;
	private static Dashboard dashboard;
	//private PowerDistributionPanel pdp = new PowerDistributionPanel(0);
	
	public static Dashboard getInstance() {
		if (dashboard == null) {
			System.out.println("Creating a new Dashboard");
			dashboard = new Dashboard();
		}
		return dashboard;
	}
	
	private Dashboard()
	{
		SmartDashboard.putBoolean("Reset Arm Encoder", false);
		SmartDashboard.putBoolean("Reset Elevator Encoder", false);
	}
	
	public void update(double lowPressure, double highPressure)
	{
		SmartDashboard.putNumber("Gyro", I2CGyro.getInstance().getAngle());
		SmartDashboard.putNumber("Left Encoder", DriveTrain.getInstance().getLeftDriveEncoderDistance());
		SmartDashboard.putNumber("Right Encoder", DriveTrain.getInstance().getRightDriveEncoderDistance());
		SmartDashboard.putNumber("High Pressure", highPressure);
		SmartDashboard.putNumber("Low Pressure", lowPressure);
		SmartDashboard.putNumber("Elevator Encoder", Elevator.getInstance().getEncoderCount());
		SmartDashboard.putNumber("Elevator Motor", Elevator.getInstance().getMotorOutput());
		SmartDashboard.putNumber("Elevator Setpoint", Elevator.getInstance().getSetpoint());
		SmartDashboard.putNumber("Acq Motor Speed", AcquisitionScoring.getInstance().getAcqSpeed());
		SmartDashboard.putNumber("Acq Arms SP", AcquisitionScoring.getInstance().getSetpoint());
		
		if (SmartDashboard.getBoolean("Reset Arm Encoder", false))
		{
			AcquisitionScoring.getInstance().armsUpDownSetMotor(-1);
			AcquisitionScoring.getInstance().resetUpDownEncoder();
			//System.out.println(pdp.getCurrent(7));
			//SmartDashboard.putBoolean("Reset Arm Encoder", false);
		}
		
		if (SmartDashboard.getBoolean("Reset Elevator Encoder", false))
		{
			Elevator.getInstance().resetEncoder();
			SmartDashboard.putBoolean("Reset Elevator Encoder", false);
		}
		
		SmartDashboard.putNumber("Arm Encoder", AcquisitionScoring.getInstance().getUpDownEncoder());
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
