package org.usfirst.frc.team1038.auton;

import org.usfirst.frc.team1038.subsystem.DriveTrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class MotionMagicProfiler {
	private DriveTrain driveTrain= DriveTrain.getInstance();
	private TalonSRX leftDriveMaster = driveTrain.leftDrive1;
	private TalonSRX rightDriveMaster = driveTrain.rightDrive1;
	StringBuilder printMessage = new StringBuilder();
	private double targetPos;
	private static final int PID_LOOP = 0;
	private static final int SLOT = 0;
	private static final int TIMEOUT_MS = 50;
	private static final int CRUISE_VELOCITY = 15000;
	private static final int ACCELERATION = 6000;
	
	
	public MotionMagicProfiler(double setPoint) {
		targetPos = setPoint;
		leftDriveMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, PID_LOOP, TIMEOUT_MS);
		leftDriveMaster.setSensorPhase(true);
		leftDriveMaster.setInverted(false);
		leftDriveMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, TIMEOUT_MS);
		leftDriveMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, TIMEOUT_MS);
		leftDriveMaster.configNominalOutputForward(0, TIMEOUT_MS);
		leftDriveMaster.configNominalOutputReverse(0, TIMEOUT_MS);
		leftDriveMaster.configPeakOutputForward(1, TIMEOUT_MS);
		leftDriveMaster.configPeakOutputReverse(-1, TIMEOUT_MS);
		leftDriveMaster.selectProfileSlot(SLOT, PID_LOOP);
		leftDriveMaster.config_kF(0, 0.2, TIMEOUT_MS);
		leftDriveMaster.config_kP(0, 0.2, TIMEOUT_MS);
		leftDriveMaster.config_kI(0, 0, TIMEOUT_MS);
		leftDriveMaster.config_kD(0, 0, TIMEOUT_MS);
		leftDriveMaster.configMotionCruiseVelocity(CRUISE_VELOCITY, TIMEOUT_MS);
		leftDriveMaster.configMotionAcceleration(ACCELERATION, TIMEOUT_MS);
		leftDriveMaster.setSelectedSensorPosition(0, PID_LOOP, TIMEOUT_MS);
	}
	
	public void periodicProfileRunner() {
		double motorOutput = leftDriveMaster.getMotorOutputPercent();
		printMessage.append("\tOut%:");
		printMessage.append(motorOutput);
		printMessage.append("\tVel:");
		printMessage.append(leftDriveMaster.getSelectedSensorVelocity(PID_LOOP));
		
		leftDriveMaster.set(ControlMode.MotionMagic, targetPos);
		printMessage.append("\tErr:");
		printMessage.append(leftDriveMaster.getClosedLoopError(PID_LOOP));
		printMessage.append("\tTrg:");
		printMessage.append(targetPos);
		
		System.out.println(printMessage.toString());
	}
}