package org.usfirst.frc.team3504.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3504.robot.OI;
import org.usfirst.frc.team3504.robot.OI.DriveDirection;

/**
 *
 */
public class DriveForward extends Command {

    private final OI m_oi;

    public DriveForward(OI oi) {
        m_oi = oi;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        m_oi.setDriveDirection(DriveDirection.kFWD);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
