package com.gos.steam_works.commands;

import com.gos.steam_works.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class Agitate extends Command {

    private int m_loopCounter;

    public Agitate() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.m_agitator);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        System.out.println("Agitate Initialzed");
        m_loopCounter = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (m_loopCounter % 10 == 0) {
            Robot.m_agitator.agitateBackwards();
        } else if (m_loopCounter % 5 == 0) {
            Robot.m_agitator.agitateForwards();
        }

        m_loopCounter++;
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        System.out.println("Agitate Finished");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
