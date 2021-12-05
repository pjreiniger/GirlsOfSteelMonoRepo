package org.usfirst.frc.team3504.robot.commands.autonomous;

import org.usfirst.frc.team3504.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoTurn extends Command {

    private final double turnAmt;
    private final double speed;

    public AutoTurn(double turnAmt, double speed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.chassis);
        this.turnAmt = turnAmt;
        this.speed = speed;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.chassis.resetEncoderDistance();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.chassis.drive(speed, -1);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return Math.abs(Robot.chassis.getEncoderDistance()) >= turnAmt; //competition bot
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.chassis.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
