package com.gos.rapidreact.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.ShooterSubsystem;


public class ShooterRpmPIDCommand extends CommandBase {
    private final ShooterSubsystem m_shooter;
    private final double m_goalRPM;

    public ShooterRpmPIDCommand(ShooterSubsystem shooterSubsystem, double goalRPM) {
        this.m_shooter = shooterSubsystem;
        m_goalRPM = goalRPM;

        addRequirements(this.m_shooter);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_shooter.setShooterRpmPIDSpeed(m_goalRPM);
    }

    @Override
    public boolean isFinished() {
        return m_shooter.isShooterAtSpeed() && m_shooter.isRollerAtSpeed();
    }

    @Override
    public void end(boolean interrupted) {
    }
}
