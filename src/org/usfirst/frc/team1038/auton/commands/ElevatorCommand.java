package org.usfirst.frc.team1038.auton.commands;

import org.usfirst.frc.team1038.robot.Robot;
import org.usfirst.frc.team1038.subsystem.Elevator;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorCommand extends Command {
	
	private Elevator elevator = Elevator.getInstance();

	public ElevatorCommand(int setpoint)
	{
		if (elevator.goingDown(setpoint))
			elevator.elevatorPID.setPID(Elevator.P_DOWN, Elevator.I_DOWN, Elevator.D_DOWN);
		else
			elevator.elevatorPID.setPID(Elevator.P_UP, Elevator.I_UP, Elevator.D_UP);
		elevator.setSetpoint(setpoint);
		requires(Robot.elevator);
	}
	
	public void execute()
	{
		elevator.enable();
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return elevator.onTarget();
	}
	
	public void interrupted()
	{
		end();
	}
	
	public void end()
	{
		elevator.elevatorPID.setPID(Elevator.P_DOWN, Elevator.I_DOWN, Elevator.D_DOWN);
		elevator.setSetpoint(0);
	}
}
