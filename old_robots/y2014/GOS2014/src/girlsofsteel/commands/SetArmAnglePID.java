/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

import girlsofsteel.Configuration;
import girlsofsteel.subsystems.Manipulator;

/**
 *
 * @author Sylvie
 *
 * Angle 0 is considered to be the horizontal
 * -18.3 degrees is the actual bottom
 * Top limit of the arm unknown (probably around 100?)
 *
 */
public class SetArmAnglePID extends CommandBase {

    private final Manipulator m_manipulator;
    private final double m_desiredAngle;
    private double m_angle;
    private final double m_allowedAngleError;


    public SetArmAnglePID(Manipulator manipulator, double desiredAngle) {
        m_manipulator = manipulator;
        requires(m_manipulator);
        m_desiredAngle = desiredAngle;
        m_allowedAngleError = 4; //2 degrees of error on both sides allowed
    }

    @Override
    protected void initialize() {
        m_angle = m_desiredAngle * Configuration.desiredAnglePivotArmSign;
    }

    @Override
    protected void execute() {
        if (m_angle < -18.2) {
            m_angle = -18.2;
        }
        else if(m_angle > 113) {
            m_angle = 110;
        }
        m_manipulator.setSetPoint(m_angle);
        //System.out.println("SSEENNNTTTT AARRRMMMMMMMMM AANNNGGLLLLEEE ----- "+angle);
        //System.out.println("AARRRMMMMMMMMM AANNNGGLLLLEEE ----- "+manipulator.getDistance());
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(m_desiredAngle - m_manipulator.getAbsoluteDistance()) < m_allowedAngleError;
    }

    @Override
    protected void end() {
        m_manipulator.holdAngle();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
