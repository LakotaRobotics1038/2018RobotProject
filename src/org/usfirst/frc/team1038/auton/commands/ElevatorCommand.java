package org.usfirst.frc.team1038.auton.commands;

import org.usfirst.frc.team1038.robot.Robot;
import org.usfirst.frc.team1038.subsystem.Elevator;

import edu.wpi.first.wpilibj.command.Command;

public class ElevatorCommand extends Command {
	
	private Elevator elevator = Elevator.getInstance();
	private int setpoint;

	/**
	 * Creates an new Elevator Command
	 * @param setpoint to move the elevator to. This value should come from a public final inside of Elevator.java
	 */
	public ElevatorCommand(int setpoint) {
		this.setpoint = setpoint;
		requires(Robot.elevator);
	}
	
	@Override
	public void execute() {
		if (elevator.goingDown(setpoint))
			elevator.elevatorPID.setPID(Elevator.P_DOWN, Elevator.I_DOWN, Elevator.D_DOWN);
		else
			elevator.elevatorPID.setPID(Elevator.P_UP, Elevator.I_UP, Elevator.D_UP);
		elevator.setSetpoint(setpoint);
		//System.out.println(elevator.getSetpoint());
		elevator.enable();
	}
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return elevator.onTarget();
	}
	
	@Override
	public void interrupted() {
		elevator.elevatorPID.setPID(Elevator.P_DOWN, Elevator.I_DOWN, Elevator.D_DOWN);
		elevator.setSetpoint(0);
		end();
		//System.out.println("Elevator Command Interrupted");
	}
	
	@Override
	public void end() {
		System.out.println("Elevator Command over");
	}
}
