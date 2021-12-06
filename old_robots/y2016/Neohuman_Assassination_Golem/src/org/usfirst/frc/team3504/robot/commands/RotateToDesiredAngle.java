package org.usfirst.frc.team3504.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3504.robot.subsystems.Chassis;

/**
 *
 */
public class RotateToDesiredAngle extends Command {
    private final Chassis m_chassis;
    private final double m_move;
    private final double m_desiredAngle; // NOPMD

    public RotateToDesiredAngle(Chassis chassis, double moveValue, double angle) {
        m_chassis = chassis;
        requires(m_chassis);
        m_move = moveValue;
        m_desiredAngle = angle;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        try {
            // Use the joystick X axis for lateral movement,
            // Y axis for forward movement, and the current
            // calculated rotation rate (or joystick Z axis),
            // depending upon whether "rotate to angle" is active.
            m_chassis.drive(m_move, m_chassis.getRotationAngleRate());
        } catch( RuntimeException ex ) { // NOPMD
            DriverStation.reportError("Error communicating with drive system:  " + ex.getMessage(), true);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
                //Math.abs(Robot.chassis.getGyroAngle()) >= Math.abs(desiredAngle);
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        m_chassis.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
