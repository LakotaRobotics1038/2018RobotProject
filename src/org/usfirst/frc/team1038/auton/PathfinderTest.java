package org.usfirst.frc.team1038.auton;

import org.usfirst.frc.team1038.robot.Conversions;
import org.usfirst.frc.team1038.robot.I2CGyro;
import org.usfirst.frc.team1038.robot.Robot;
import org.usfirst.frc.team1038.subsystem.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class PathfinderTest extends Command {
	
	private final double WHEEL_DIAMETER = 6;
	private final double TIME_STEP = .05;
	private final double MAX_VELOCITY = .85;
	private final double MAX_ACC = 1.0;
	private final double MAX_JERK = 60.0;
	private final double WHEELBASE_WIDTH = 38.25;
	private EncoderFollower left;
	private EncoderFollower right;
	private DriveTrain drive = DriveTrain.getInstance();
	private I2CGyro gyro = I2CGyro.getInstance();
	private final static double P = 0.015;
	private final static double I = 0.015;
	private final static double D = 0.005;
    
	public PathfinderTest() {
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
        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_LOW, TIME_STEP, MAX_VELOCITY, MAX_ACC, MAX_JERK);
        Waypoint[] points = new Waypoint[] {
        		
        		/*
        		 * input * 10 = destination in inches
        		 */
        		
        		new Waypoint(0, 0, Pathfinder.d2r(0)),    //Waypoint @ x= 0, y= 0, exit angle= 0 degrees
        		new Waypoint(3, 0, Pathfinder.d2r(45)),   //Waypoint @ x= 30in, y= 0, exit angle= 45 degrees
        		new Waypoint(4, 3, Pathfinder.d2r(90)),   //Waypoint @ x= 40in, y= 30in, exit angle= 90 degrees
        		new Waypoint(4, 9, Pathfinder.d2r(45)),   //Waypoint @ x= 40in, y= 90in, exit angle= 45 degrees
        		new Waypoint(8, 10, Pathfinder.d2r(0))    //Waypoint @ x= 80in, y= 10in, exit angle= 0 degrees
        };

        Trajectory trajectory = Pathfinder.generate(points, config);
        
    		gyro.resetGyro();
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
    	
    public void excecute()
    {
    		// Do something with the new Trajectory...
    		double l = left.calculate(drive.getLeftDriveEncoderCount());
    		double r = right.calculate(drive.getRightDriveEncoderCount());

    		double gyro_heading = gyro.getAngle();    // Assuming the gyro is giving a value in degrees
    		double desired_heading = Pathfinder.r2d(left.getHeading());  // Should also be in degrees

    		double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
    		double turn /*= 0.8 * (-1/80) * angleDifference*/; // 0.8 * (-1/80) = -0.01
    		
    		if(angleDifference > 1) {
    			turn = -.75;
    		}else if(angleDifference < -1) {
    			turn = 0.75;
    		}else {
    			turn = 0;
    		}
    		
    		if(l > 0.75) {
    			l = 0.75;
    		}else if(l < -0.75) {
    			l = -0.75;
    		}
    		
    		if(r > 0.75) {
    			r = 0.75;
    		}else if(r < -0.75) {
    			r = -0.75;
    		}
    		
    		double leftTurn = (l + turn);
    		if(leftTurn > 0.8) {
    			leftTurn = 0.8;
    		}else if(leftTurn < -0.8) {
    			leftTurn = -0.8;
    		}
    		double rightTurn = (r - turn);
    		if(rightTurn > 0.8) {
    			rightTurn = 0.8;
    		}else if(rightTurn < -0.8) {
    			rightTurn = -0.8;
    		}
    		
    		drive.tankDrive(leftTurn, rightTurn);
    		System.out.printf("Path Output Calculated: %f,%f,%f,%f,%f\n", l, r, turn, leftTurn, rightTurn);
    }
    
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
}
