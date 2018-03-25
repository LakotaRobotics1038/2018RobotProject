package org.usfirst.frc.team1038.depricated;

import java.io.File;

import org.usfirst.frc.team1038.robot.I2CGyro;
import org.usfirst.frc.team1038.robot.Robot;
import org.usfirst.frc.team1038.subsystem.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.followers.DistanceFollower;
import jaci.pathfinder.followers.EncoderFollower;

@Deprecated
public class Pathfinder1038 extends Command {
	
	//private final double TIME_STEP = .05;
	private final double MAX_VELOCITY = .07;
	private final double MAX_ACC = .25;
	//private final double MAX_JERK = 60.0;
	//private final double WHEELBASE_WIDTH = 20.5 /* Prototype Bot 38.25*/; //inches
	private DistanceFollower left; //EncoderFollower
	private DistanceFollower right;
	private DriveTrain drive = DriveTrain.getInstance();
	private I2CGyro gyro = I2CGyro.getInstance();
	private final static double P = 0.0001;
	private final static double I = 0.000;
	private final static double D = 0.000;
	double angleDifference;
	public File choosenLFile;
	public File choosenRFile;
    
	public Pathfinder1038(File LFile, File RFile) {
		choosenLFile = LFile;
		choosenRFile = RFile;
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
        //Trajectory trajectory = Pathfinder.readFromFile(choosenFile);
    	//Trajectory trajectory = Pathfinder.generate(points, config);
        
    		gyro.reset();
    		drive.resetEncoders();
    		//TankModifier modifier = new TankModifier(trajectory).modify(Conversions.f2m(WHEELBASE_WIDTH / 12.0));
    		left = new DistanceFollower(Pathfinder.readFromCSV(choosenLFile)); /*modifier.getLeftTrajectory()*/
    		right = new DistanceFollower(Pathfinder.readFromCSV(choosenRFile)); /*modifier.getRightTrajectory()*/
    		// Encoder Position is the current, cumulative position of your encoder. If you're using an SRX, this will be the
    		// 'getEncPosition' function.
    		// 1000 is the amount of encoder ticks per full revolution
    		// Wheel Diameter is the diameter of your wheels (or pulley for a track system) in meters
    		//left.configureEncoder(drive.getLeftDriveEncoderCount(), drive.ENCODER_COUNTS_PER_REV, drive.WHEEL_DIAMETER / 12.0);
    		//right.configureEncoder(drive.getRightDriveEncoderCount(), drive.ENCODER_COUNTS_PER_REV, drive.WHEEL_DIAMETER / 12.0);
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
    	
    public void execute() {

    	//double l = left.calculate(drive.getLeftDriveEncoderCount());
    	//double r = right.calculate(drive.getRightDriveEncoderCount());
    	double l = left.calculate(drive.getLeftDriveEncoderDistance() * 2000);
    	double r = right.calculate(drive.getRightDriveEncoderDistance() * 2000);

    	double gyro_heading = gyro.getAngle();   // Assuming the gyro is giving a value in degrees
    	double desired_heading = Pathfinder.r2d(left.getHeading());  // Should also be in degrees

    	double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
    	double turn = 0.4 * (-1.0/80.0) * angleDifference;

    	drive.tankDrive(l - turn, r + turn);
    		System.out.printf("Path Output Calculated: %f,%f,%f,%f\n", l, r, turn, angleDifference);
    }
    
	@Override
	public boolean isFinished() {
		return (left.isFinished() && right.isFinished() && (angleDifference < 2 && angleDifference > - 2));
	}
}
