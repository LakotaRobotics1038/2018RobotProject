package org.usfirst.frc.team1038.robot;

import edu.wpi.first.wpilibj.command.PIDCommand;

public class TurnCommandPID extends PIDCommand {

	public TurnCommandPID(double p, double i, double d) {
		super(p, i, d);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return I2CGyro.getInstance().getAngle();
	}

	@Override
	protected void usePIDOutput(double arg0) {
		// TODO Auto-generated method stub
		DriveTrain.getInstance().drive(0, arg0);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return getPIDController().onTarget();
	}

}
