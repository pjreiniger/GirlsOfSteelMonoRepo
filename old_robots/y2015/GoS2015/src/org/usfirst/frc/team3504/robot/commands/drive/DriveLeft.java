package org.usfirst.frc.team3504.robot.commands.drive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3504.robot.OI;
import org.usfirst.frc.team3504.robot.subsystems.Chassis;

/*
 *
 */
public class DriveLeft extends Command {
    private final Joystick m_joystick;
    private final Chassis m_chassis;

    public DriveLeft(OI oi, Chassis chassis) {
        m_chassis = chassis;
        m_joystick = oi.getChassisJoystick();
        requires(m_chassis);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        m_chassis.driveLeft(m_joystick);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        m_chassis.stop();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
