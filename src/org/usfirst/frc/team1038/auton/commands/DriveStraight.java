package org.usfirst.frc.team1038.auton.commands;

import org.usfirst.frc.team1038.robot.I2CGyro;
import org.usfirst.frc.team1038.robot.Robot;
import org.usfirst.frc.team1038.subsystem.DriveTrain;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.PIDCommand;

public class DriveStraight extends PIDCommand {
	//fields
		private final double END_DRIVE_SPEED = 0.0;
		private final double END_DRIVE_ROTATION = 0.0;
		private final int TOLERANCE = 1;
		private final static double dP = 0.100;
		private final static double dI = 0.000;
		private final static double dD = 0.000;
		private final static double tP = 1;
		private final static double tI = 0.000;
		private final static double tD = 0.000;
//		private final static double P = 0.007;
//		private final static double I = 0.000;
//		private final static double D = 0.000;
		private I2CGyro gyroSensor = I2CGyro.getInstance();
		private DriveTrain drive = DriveTrain.getInstance();
		private PIDController drivePID = getPIDController();
		private PIDController turnPID = new PIDController(tP, tI, tD, gyroSensor, new Spark(9));
		
		//constructor
		public DriveStraight(double setpoint) {
			//Drive
			super(dP, dI, dD);
			setSetpoint(setpoint);
			drivePID.setAbsoluteTolerance(TOLERANCE);
			drivePID.setOutputRange(-.75, .75);
			drivePID.setContinuous(false);
			
			//Angle
			turnPID.setSetpoint(0);
			turnPID.setAbsoluteTolerance(TOLERANCE);
			turnPID.setOutputRange(-.75, .75);
			turnPID.setInputRange(0, 360);
			turnPID.setContinuous(true);
			requires(Robot.robotDrive);
		}
		
		//methods
		public void initialize() {
			gyroSensor.reset();
			drive.resetEncoders();
			//turnPID.setInputRange(0, 359);
		}
		
		public void execute() {
			drivePID.enable();
			double distancePID = drivePID.get();
			double anglePID = turnPID.get();
			System.out.println(distancePID + " " + anglePID);
			usePIDOutput(distancePID, anglePID);
		}
		
		@Override
		public void interrupted()
		{
			end();
			System.out.println("Straight interrupted");
		}
		
		@Override
		public void end() {
			drivePID.reset();
			drive.drive(END_DRIVE_SPEED, END_DRIVE_ROTATION);
			System.out.println("DriveStraight ended");
		}
		
		@Override
		public boolean isFinished() {
			return drivePID.onTarget();
		}

		@Override
		protected double returnPIDInput() {
			return drive.getRightDriveEncoderDistance();
		}

		protected void usePIDOutput(double drivePower, double turnPower) {
			drive.dualArcadeDrive(drivePower, turnPower);		
		}

		@Override
		protected void usePIDOutput(double output) {
			// TODO Auto-generated method stub
			
		}
}
