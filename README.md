# 2018RobotProject
Robot Project for the 2018 FRC game, FIRST POWER UP
# team1038.auton
## Auton.java
Abstract class used to group auton commands and create an auton
## AutonSelector.java
Reads the data from the dashboard and FMS to select an auton
## DualScaleAuton.java
Adds to the end of the group created by SingleScaleAuton.java to score a second cube
## DualSwitchAuton.java
Adds to the end of the group created by SingleSwitchAution,java to score a second cube
## ForwardAuton.java
Drives the robot forward to cross the baseline
## SingleScaleAuon.java
Runs a series of commands to score a cube in the scale
## SingleSwitchAuton.java
Runs a series of commands to score a sub in the switch
# team1038.auton.commands
## AcquireCommand.java
Runs the acquisition for a specified period of time
## AcquisitionAngleCommand.java
Changes the angle of the acquisition arms. The setpoints are defined in AcquisitionScoring.java
## AcquisitionOpenCloseCommand.java
Opens or closes the acquisition arms
## ChangeAcquisitionPowerCommand.java
Changes the power setpoint for the acqusition wheels. This affects AcquireCommand.java
## DriveStraightCommand.java
Drives the robot straight for a given distance in feet
## ElevatorCommand.java
Changes the setpoint of the elevator. The setpoints are defined in Elevator.java
## TurnCommand.java
Turns the robot to a given angle in degrees
## TurnCommandVision.java
Turns the robot to a given angle by reading the angle in degrees of a cube in front of the camera
# team1038.deprecated
## Pathfinder
Send the pathfinder libraries to /usr/local/frc/lib (ssh as admin)
# team1038.robot
## Encoder1038.java
Extends the WPI Encoder class and adds formula for encoder counts to distance
## I2CGyro.java
Extedns the WPI GyroBase class and reads the modern robotics I2C Integrating Gyro (http://modernroboticsinc.com/integrating-3-axis-gyro)
## Joystick1038.java
Extend the WPI Joystick class and create methods to get each button and joytick index from the Logitech Gamepad F310 Controller (http://www.andymark.com/product-p/am-2064.htm)
## PressureSensor.java
Extends the WPI AnalogInput class to read the Rev Robotics Analog Pressure Sensor (http://www.revrobotics.com/rev-11-1107/)
## Prox.java
Extends the WPI DigitalInput class to read a prox sensor
## Robot.java
Main class from WPI. Starting point for all robot code
## TalonSRX1038.java
Extends TalonSRX from CTREPheonix and implements SpeedController from WPI to allow a TalonSRX to be placed into a WPI robot drive
# team1038.subsystem
## AcquisitionScoring.java
Extends Subsystem from and runs the Acquisition/Scoring mechanism for Team 1038's 2018 robot, Power Up Paul George
## Climb.java
Extends Subsystem from WPI and runs the Climb Arm mechanism for Team 1038's 2018 robot, Power Up Paul George
## Dashboard.java
Extends SmartDashboard from WPI and places all items on the Shuffleboard for competition
## DriveTrain.java
Extends Subsytem from WPI and runs the Drive Train and PTO for Team 1038's 2018 robot, Power Up Paul George
## Elevator.java
Extends Subsystem from WPI and runs the Elevator mechanism for Team 1038's 2018 robot, Power Up Paul George
## SwagLights.java
Extends Subsystem from WPI and runs the LED Swag Lights for Team 1038's 2018 robot, Power Up Paul George
## Vision.java
Extends Subsystem from WPI and reads the Vision information from the raspberry pi for Team 1038's 2018 robot, Power Up Paul George (See 2018Vision repo)
# shuffleboard.json
Saved locations of items from Dashboard.java for Team 1038's 2018 robot, Power Up Paul George
# Drive Controller.png
Joystick layout and function map for joystick at USB 0
# Operator Controller.png
Joystick layout and function map for joystick at USB 1
# dependencies
## CTRE_Phoenix-sources.jar
TalonSRX is dependent on this jar
## CTRE_Phoenix.jar
TalonSRX is dependent on this jar
## libCTRE_PhoenixCCI.so
TalonSRX is dependent on this so file
## libpathfinderjava.so
Pathfinder1038.java is dependent on this, but is deprecated
## Motion_Profile_Generator_2.3.0.jar
Used to calculate trajectories for pathfinder (files located in origin/pathTrajectories)
## pathfinderjava.jar
Pathfinder1038.java is dependent on this, but is deprecated
## TalonSrx-Application-3.8-FRC2018.crf
Firmware for TalonSRX. Must be running this version to function