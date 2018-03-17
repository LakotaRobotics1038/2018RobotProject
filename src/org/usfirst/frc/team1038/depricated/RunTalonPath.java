package org.usfirst.frc.team1038.depricated;

//import org.usfirst.frc.team1038.subsystem.DriveTrain;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motion.TrajectoryPoint.TrajectoryDuration;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Notifier;

@Deprecated
public class RunTalonPath {
	
//	class PeriodicRunnable implements java.lang.Runnable{
//		public void run() { leftDriveMaster.processMotionProfileBuffer();}
//		Notifier notifier = new Notifier(new PeriodicRunnable());
//	}
//	
//	private DriveTrain driveTrain= DriveTrain.getInstance();
//	private TalonSRX leftDriveMaster = driveTrain.leftDrive1;
//	private TalonSRX rightDriveMaster = driveTrain.rightDrive1;
//	private TrajectoryPoint trajectory = new TrajectoryPoint();
//	private MotionProfileStatus trajectoryStatus = new MotionProfileStatus();
//	private SetValueMotionProfile setValue = SetValueMotionProfile.Disable;
//	double pos = 0, vel = 0, heading = 0;
//	private int motorState = 0;
//	private int loopTimeout = 100;
//	private boolean start = false;
//	private static final int NUM_LOOPS_TIMEOUT = 10;
//	private static final int MIN_POINTS_IN_TALON = 2;
//
//	public void runPath() {
//		leftDriveMaster.processMotionProfileBuffer();
//		rightDriveMaster.processMotionProfileBuffer();
//	}
//	
//	private TrajectoryDuration GetTrajectoryDuration(int durationMs) {
//		TrajectoryDuration retVal = TrajectoryDuration.Trajectory_Duration_0ms;
//		retVal = retVal.valueOf(durationMs);
//		if(retVal.value != durationMs) {
//			System.out.println("Trajectory Duration not suppported");
//		}
//		return retVal;
//	}
//	
//	public void reset() {
//		leftDriveMaster.clearMotionProfileTrajectories();
//		setValue = SetValueMotionProfile.Disable;
//		motorState = 0;
//		start = false;
//	}
//	
//	public void control() {
//		leftDriveMaster.getMotionProfileStatus(trajectoryStatus);
//		
//		if(loopTimeout < 0) {
//			
//		}else {
//			if(loopTimeout == 0) {
//				System.out.println("loop timeout is equal to zero");
//			}else {
//				loopTimeout--;
//			}
//		}
//		
//		if(leftDriveMaster.getControlMode() != ControlMode.MotionProfile) {
//			motorState = 0;
//			loopTimeout = -1;
//		}else {
//			switch(motorState) {
//			case 0:
//				if(start) {
//					start = false;
//					setValue = SetValueMotionProfile.Disable;
//					startFilling();
//					motorState = 1;
//					loopTimeout = NUM_LOOPS_TIMEOUT;
//				}
//				break;
//			case 1:
//				if(trajectoryStatus.btmBufferCnt > MIN_POINTS_IN_TALON) {
//					setValue = SetValueMotionProfile.Enable;
//					motorState = 2;
//					loopTimeout = NUM_LOOPS_TIMEOUT;
//				}
//				break;
//			case 2:
//				if(trajectoryStatus.isUnderrun == false) {
//					loopTimeout = NUM_LOOPS_TIMEOUT;
//				}
//				if(trajectoryStatus.activePointValid && trajectoryStatus.isLast) {
//					setValue = SetValueMotionProfile.Hold;
//					motorState = 0;
//					loopTimeout = -1;
//				}
//				break;
//			}
//			leftDriveMaster.getMotionProfileStatus(trajectoryStatus);
//			heading = leftDriveMaster.getActiveTrajectoryHeading();
//			pos = leftDriveMaster.getActiveTrajectoryPosition();
//			vel = leftDriveMaster.getActiveTrajectoryVelocity();
//		}
//	}
//	
//	private void startFilling() {
//		startFilling(GeneratedMotionProfile.Points);
//	}
//	
//	private void startFilling(double[][] profile) {
//		if(trajectoryStatus.hasUnderrun) {
//			System.out.println("Has Underrun");
//			leftDriveMaster.clearMotionProfileHasUnderrun(0);
//		}
//		
//		leftDriveMaster.clearMotionProfileTrajectories();
//		
//		//0 s need replaced
//		leftDriveMaster.configMotionProfileTrajectoryPeriod(0, 0);
//		
//		for(int i = 0; i < profile.length; i++) {
//			double positionRot = profile[i][0];
//			double velocityRPM = profile[i][1];
//			trajectory.position = positionRot * 220; //Check
//			trajectory.velocity = velocityRPM * 220 / 600.0; //Check
////			trajectory.headingDeg = 
//			trajectory.profileSlotSelect0 = 0;
//			trajectory.profileSlotSelect1 = 0;
//			trajectory.timeDur = GetTrajectoryDuration((int)profile[i][2]);
//			trajectory.zeroPos = false;
//			if(i == 0) {
//				trajectory.zeroPos = true;
//			}
//			trajectory.isLastPoint = false;
//			if(i == profile.length - 1) {
//				trajectory.isLastPoint = true;
//			}
//		}
//		
//		leftDriveMaster.pushMotionProfileTrajectory(trajectory);
//	}
//	
//	void startMotionProfile() {
//		start = true;
//	}
//	
//	SetValueMotionProfile getSetValue() {
//		return setValue;
//	}
}
