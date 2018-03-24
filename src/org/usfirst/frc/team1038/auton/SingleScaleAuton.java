





package org.usfirst.frc.team1038.auton;

import org.usfirst.frc.team1038.auton.commands.AcquireCommand;
import org.usfirst.frc.team1038.auton.commands.AcquireCommand.Modes;
import org.usfirst.frc.team1038.auton.commands.AcquisitionAngleCommand;
import org.usfirst.frc.team1038.auton.commands.DriveStraightCommand;
import org.usfirst.frc.team1038.auton.commands.ElevatorCommand;
import org.usfirst.frc.team1038.auton.commands.TurnCommand;
import org.usfirst.frc.team1038.subsystem.AcquisitionScoring;
import org.usfirst.frc.team1038.subsystem.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SingleScaleAuton {
	
	private CommandGroup group;
	private String position;
	private String gameData;
	
	private final double DIST_TO_SCALE_FROM_DS_WALL = 23.2;
	private final double DIST_TO_BW_SW_AND_SCALE_FROM_DS_WALL = 18.7;
	private final double DIST_TO_CROSS_BW_SW_AND_SCALE = 15.5;
	private final double DIST_FROM_CROSS_TO_SCALE = 4.5;
	private final double DISPOSE_TIME = 1.5;
	
	public SingleScaleAuton(String positionIn, String gameDataIn)
	{
		group = new CommandGroup();
		position = positionIn;
		gameData = gameDataIn;
	}
	
	public CommandGroup select()
	{
		switch (position)
		{
			case AutonSelector.kLeftPosition:
				if (gameData.substring(1, 2).equals("L"))
				{
					group.addSequential(new DriveStraightCommand(DIST_TO_SCALE_FROM_DS_WALL));
					group.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
					group.addSequential(new TurnCommand(45));
//					group.addSequential(new ElevatorCommand(Elevator.SCALE_HIGH));
//					group.addSequential(new AcquireCommand(Modes.Dispose, DISPOSE_TIME));
//					group.addSequential(new ElevatorCommand(Elevator.FLOOR));
				}
				else
				{
					group.addSequential(new DriveStraightCommand(DIST_TO_BW_SW_AND_SCALE_FROM_DS_WALL));
					group.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
					group.addSequential(new TurnCommand(90));
					group.addSequential(new DriveStraightCommand(DIST_TO_CROSS_BW_SW_AND_SCALE));
					group.addSequential(new TurnCommand(0));
					group.addSequential(new DriveStraightCommand(DIST_FROM_CROSS_TO_SCALE));
				}
				group.addSequential(new ElevatorCommand(Elevator.SCALE_HIGH));
				group.addSequential(new AcquireCommand(Modes.Dispose, DISPOSE_TIME));
				group.addSequential(new ElevatorCommand(Elevator.FLOOR));
				break;
			case AutonSelector.kRightPosition:			
				if (gameData.substring(1, 2).equals("R"))
				{
					System.out.println(gameData.substring(1, 2) + "Straight Case");
					group.addSequential(new DriveStraightCommand(DIST_TO_SCALE_FROM_DS_WALL));
					group.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
					group.addSequential(new TurnCommand(315));
//					group.addSequential(new ElevatorCommand(Elevator.SCALE_HIGH));
//					group.addSequential(new AcquireCommand(Modes.Dispose, DISPOSE_TIME));
//					group.addSequential(new ElevatorCommand(Elevator.FLOOR));
				}
				else
				{
					System.out.println("Cross Case");
					group.addSequential(new DriveStraightCommand(DIST_TO_BW_SW_AND_SCALE_FROM_DS_WALL));
					group.addParallel(new AcquisitionAngleCommand(AcquisitionScoring.UP_DOWN_HALF));
					group.addSequential(new TurnCommand(270));
					group.addSequential(new DriveStraightCommand(DIST_TO_CROSS_BW_SW_AND_SCALE));
					group.addSequential(new TurnCommand(0));
					group.addSequential(new DriveStraightCommand(DIST_FROM_CROSS_TO_SCALE));
				}
				System.out.println("Continues");
				group.addSequential(new ElevatorCommand(Elevator.SCALE_HIGH));
				group.addSequential(new AcquireCommand(Modes.Dispose, DISPOSE_TIME));
				group.addSequential(new ElevatorCommand(Elevator.FLOOR));
				break;
			case AutonSelector.kCenterPosition:
				group = new ForwardAuton().select();
				break;
		}
		return group;
	}
}
