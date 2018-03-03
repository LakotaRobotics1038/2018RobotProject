package org.usfirst.frc.team1038.auton.commands;

import java.io.File;

import org.usfirst.frc.team1038.robot.Conversions;
import org.usfirst.frc.team1038.robot.I2CGyro;
import org.usfirst.frc.team1038.robot.Robot;
import org.usfirst.frc.team1038.subsystem.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class Pathfinder1038 extends Command {
	
	private final double WHEEL_DIAMETER = 6;
	private final double TIME_STEP = .05;
	private final double MAX_VELOCITY = .85;
	private final double MAX_ACC = 1.0;
	private final double MAX_JERK = 60.0;
	private final double WHEELBASE_WIDTH = 20.5 /* Prototype Bot 38.25*/; //inches
	private EncoderFollower left;
	private EncoderFollower right;
	private DriveTrain drive = DriveTrain.getInstance();
	private I2CGyro gyro = I2CGyro.getInstance();
	private final static double P = .05;
	private final static double I = 0.000;
	private final static double D = 0.005;
	double angleDifference;
	public File choosenFile;
	public File File1038 = new File("/home/lvuser/Paths/1038File.traj");
	public static File NFile = new File("/home/lvuser/Paths/NFile.traj");
	public static File LToLSwitchFile = new File("/home/lvuser/Paths/LToLSwitchFile.traj");
	public static File RToRSwitchFile = new File("/home/lvuser/Paths/RToRSwitchFile.traj");
	public static File CToLSwitchFile = new File("/home/lvuser/Paths/CToLSwitchFile.traj");
	public static File CToRSwitchFile = new File("/home/lvuser/Paths/CToRSwitchFile.traj");
	public static File LSwitchToLCubeFile = new File("/home/lvuser/Paths/LSwitchToLCubeFile.traj");
	public static File RSwitchToRCubeFile = new File("/home/lvuser/Paths/RSwitchToRCubeFile.traj");
	public static File LSwitchToRCubeFile = new File("/home/lvuser/Paths/LSwitchtoRCubeFile.traj");
	public static File RSwitchToLCubeFile = new File("/home/lvuser/Paths/RSwitchToLCubeFile.traj");
	public static File LCubeToLScaleFile = new File("/home/lvuser/Paths/LCubeToLScaleFile.traj");
	public static File LCubeToRScaleFile = new File("/home/lvuser/Paths/LCubeToRScaleFile.traj");
	public static File RCubeToRScaleFile = new File("/home/lvuser/Paths/RCubeToRScaleFile.traj");
	public static File RCubeToLScaleFile = new File("/home/lvuser/Paths/RCubeToLScaleFile.traj");
	public static File RSwitchToRScaleFile = new File("/home/lvuser/Paths/RSwitchToRScaleFile.traj");
	public static File LSwitchToLScaleFile = new File("/home/lvuser/Paths/LSwitchToLScaleFile.traj");
    
	public Pathfinder1038(File cFile) {
		choosenFile = cFile;
		requires(Robot.robotDrive);
	}
    @Override
    public void initialize() {
    	System.out.println("Pathfinder Started");
    		// Create the Trajectory Configuration
    		//
    		// Arguments:
    		// Fit Method:          HERMITE_CUBIC or HERMITE_QUINTIC
    		// Sample Count:        SAMPLES_HIGH (100 000)
    		//	                      SAMPLES_LOW  (10 000)
    		//	                      SAMPLES_FAST (1 000)
    		// Time Step:           0.05 Seconds
    		// Max Velocity:        1.7 m/s
    		// Max Acceleration:    2.0 m/s/s
    		// Max Jerk:            60.0 m/s/s/s
        //Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_LOW, TIME_STEP, MAX_VELOCITY, MAX_ACC, MAX_JERK);
        
        //Read from file 
        Trajectory trajectory = Pathfinder.readFromFile(choosenFile);

        //Trajectory trajectory = Pathfinder.generate(points, config);
        
    		gyro.reset();
    		drive.resetEncoders();
    		TankModifier modifier = new TankModifier(trajectory).modify(Conversions.f2m(WHEELBASE_WIDTH / 12.0));
    		left = new EncoderFollower(modifier.getLeftTrajectory());
    		right = new EncoderFollower(modifier.getRightTrajectory());
    		// Encoder Position is the current, cumulative position of your encoder. If you're using an SRX, this will be the
    		// 'getEncPosition' function.
    		// 1000 is the amount of encoder ticks per full revolution
    		// Wheel Diameter is the diameter of your wheels (or pulley for a track system) in meters
    		left.configureEncoder(drive.getLeftDriveEncoderCount(), 220, Conversions.f2m(WHEEL_DIAMETER / 12.0));
    		right.configureEncoder(drive.getRightDriveEncoderCount(), 220, Conversions.f2m(WHEEL_DIAMETER / 12.0));
    		// The first argument is the proportional gain. Usually this will be quite high
    		// The second argument is the integral gain. This is unused for motion profiling
    		// The third argument is the derivative gain. Tweak this if you are unhappy with the tracking of the trajectory
    		// The fourth argument is the velocity ratio. This is 1 over the maximum velocity you provided in the 
    		// trajectory configuration (it translates m/s to a -1 to 1 scale that your motors can read)
    		// The fifth argument is your acceleration gain. Tweak this if you want to get to a higher or lower speed quicker
    		left.configurePIDVA(P, I, D, 1 / MAX_VELOCITY, MAX_ACC);
    		right.configurePIDVA(P, I, D, 1 / MAX_VELOCITY, MAX_ACC);
    		System.out.println("Pathfinder Configured");
    	}
    	
    public void execute()
    {
//    		// Do something with the new Trajectory...
//    		double l = left.calculate(drive.getLeftDriveEncoderCount());
//    		double r = right.calculate(drive.getRightDriveEncoderCount());
//
//    		double gyro_heading = gyro.getAngle();    // Assuming the gyro is giving a value in degrees
//    		double desired_heading = Pathfinder.r2d(left.getHeading());  // Should also be in degrees
//
//    		angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
//    		System.out.println("Desired: " + desired_heading + " Current:" + gyro_heading + " Angle Difference: " + angleDifference);
//    		double turn = 0;
//    		if(left.isFinished() && right.isFinished()) {
//	    		turn = 0.05 * angleDifference; // 0.8 * (-1/80) = -0.01
//	    		System.out.println("Default turn: " + turn);
//	    		
//	    		if(angleDifference < 2 && angleDifference > -2) {
//	    			turn = 0;
//	    		}else if(angleDifference < 20 && angleDifference > 2) {
//	    			turn = 0.5;
//	    		}else if(angleDifference > -20 && angleDifference < -2) {
//	    			turn = 0.5;
//	    		}
//	    		
//	    		if(turn > 0.8) {
//	    			turn = 0.8;
//	    		}else if(turn < -0.8) {
//	    			turn = -0.8;
//	    		}
//    		}
//    		
//    		if(l > 0.8) {
//    			l = 0.8;
//    		}else if(l < -0.8) {
//    			l = -0.8;
//    		}
//    		
//    		if(r > 0.8) {
//    			r = 0.8;
//    		}else if(r < -0.8) {
//    			r = -0.8;
//    		}
//    		
//    		double leftTurn = (l + turn);
//    		if(leftTurn > 0.9) {
//    			leftTurn = 0.9;
//    		}else if(leftTurn < -0.9) {
//    			leftTurn = -0.9;
//    		}
//    		double rightTurn = (r - turn);
//    		if(rightTurn > 0.9) {
//    			rightTurn = 0.9;
//    		}else if(rightTurn < -0.9) {
//    			rightTurn = -0.9;
//    		}
//    		
//    		drive.tankDrive(leftTurn, rightTurn);
    	double l = left.calculate(drive.getLeftDriveEncoderCount());
    	double r = right.calculate(drive.getRightDriveEncoderCount());

    	double gyro_heading = gyro.getAngle();   // Assuming the gyro is giving a value in degrees
    	double desired_heading = Pathfinder.r2d(left.getHeading());  // Should also be in degrees

    	double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
    	double turn = 0.4 * (-1.0/80.0) * angleDifference;

    	drive.tankDrive(l + turn, r - turn);
    		System.out.printf("Path Output Calculated: %f,%f,%f,%f"/*,%f,%f*/+"\n", l, r, turn, /*leftTurn, rightTurn,*/ angleDifference);
    }
    
	@Override
	public boolean isFinished() {
		return (left.isFinished() && right.isFinished() && (angleDifference < 2 && angleDifference > - 2));
	}
}
