package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.subsystems.Shooter;

public class TESTShooter extends CommandBase {

    private final Shooter m_shooter;
    private double m_speed;

    public TESTShooter(Shooter shooter){
        m_shooter = shooter;
        SmartDashboard.putNumber("Shooter Jag Speed", 0.0);
    }

    @Override
    protected void initialize() {
        m_shooter.initEncoder();
    }

    @Override
    protected void execute() {
        m_speed = SmartDashboard.getNumber("Shooter Jag Speed", 0.0);
        m_shooter.setJags(m_speed);
        SmartDashboard.putNumber("Shooter Encoder", m_shooter.getEncoderRate());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        m_shooter.stopJags();
        m_shooter.stopEncoder();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
