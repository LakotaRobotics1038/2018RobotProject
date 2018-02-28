package org.usfirst.frc.team1038.auton.commands;

import org.usfirst.frc.team1038.robot.I2CGyro;
import org.usfirst.frc.team1038.subsystem.DriveTrain;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDCommand;

public class TurnCommandPID extends PIDCommand {
	
	private double driveSpeed = 0.0;
	private I2CGyro gyroSensor = I2CGyro.getInstance();
	private DriveTrain robotDrive = DriveTrain.getInstance();
	private PIDController autonTurnAdjust = getPIDController();

	public TurnCommandPID(double p, double i, double d) {
		super(p, i, d);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return gyroSensor.getAngle();
	}

	@Override
	protected void usePIDOutput(double arg0) {
		// TODO Auto-generated method stub
		robotDrive.drive(0, arg0);
	}
	
	protected void execute() {
	
	getPIDController().enable();
		
		/*goalAngle = 90;
		if(gyroSensor.getAngle() < goalAngle) {
			driveRotationInit = 0.35;
		}
		else if(gyroSensor.getAngle() > goalAngle) {
			driveRotationInit = -0.35;
		}else {
			driveRotationInit = -0.0;
		}*/
		
		robotDrive.singleArcadeDrive(driveSpeed, autonTurnAdjust.get());
		System.out.println("Current Angle: " + gyroSensor.getAngle());
		System.out.println("autonTurnAdjust: " + autonTurnAdjust.get());
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return getPIDController().onTarget();
	}

}
