// RobotBuilder Version: 3.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package com.gos.outreach2021.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.ControlType;
import com.revrobotics.SparkMaxRelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SimableCANSparkMax;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.FlywheelSimWrapper;
import org.snobotv2.sim_wrappers.ISimWrapper;
import org.snobotv2.sim_wrappers.InstantaneousMotorSim;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class Shooter extends SubsystemBase {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private static final double FEED_SPEED = 0.1;
    private static final double ALLOWED_SHOOTER_ERROR = 0.02;
    private static final double MAX_SPEED_HOOD = 10;
    private static final double ALLOWED_ANGLE_ERROR = 2;

    private final RelativeEncoder m_wheelEncoder;
    private final SparkMaxPIDController m_wheelPidController;

    private final SimableCANSparkMax m_feederWheel;
    private final SimableCANSparkMax m_shooterWheel;
    private final SimableCANSparkMax m_shooterHood;

    private double m_rpmGoal;
    private double m_goalAngle;
    private InstantaneousMotorSim m_hoodMotorSim;
    private final RelativeEncoder m_hoodEncoder;
    private final SparkMaxPIDController m_hoodPidController;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    private ISimWrapper m_shooterWheelSimulator;

    /**
     *
     */
    public Shooter() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        m_feederWheel = new SimableCANSparkMax(8, MotorType.kBrushed);
        m_feederWheel.setInverted(false);

        m_shooterWheel = new SimableCANSparkMax(9, MotorType.kBrushed);
        m_shooterWheel.setInverted(false);

        m_shooterHood = new SimableCANSparkMax(10, MotorType.kBrushed);
        m_shooterHood.setInverted(false);

        m_wheelEncoder = m_shooterWheel.getEncoder(SparkMaxRelativeEncoder.Type.kQuadrature, 8192);
        m_wheelPidController = m_shooterWheel.getPIDController();

        m_wheelPidController.setFF(0.4);
        m_wheelPidController.setP(0.4);

        m_hoodEncoder = m_shooterHood.getEncoder(SparkMaxRelativeEncoder.Type.kQuadrature, 8192);
        m_hoodPidController = m_shooterHood.getPIDController();

        m_hoodPidController.setP(0.4);

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        if (RobotBase.isSimulation()) {

            FlywheelSim flywheelSim = new FlywheelSim(DCMotor.getVex775Pro(2), 1.66, .008);
            m_shooterWheelSimulator = new FlywheelSimWrapper(flywheelSim,
                    new RevMotorControllerSimWrapper(m_shooterWheel),
                    RevEncoderSimWrapper.create(m_shooterWheel));
            m_hoodMotorSim = new InstantaneousMotorSim(new RevMotorControllerSimWrapper(m_shooterHood), RevEncoderSimWrapper.create(m_shooterHood), MAX_SPEED_HOOD);

        }
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

    }

    public void angleStop() {
        m_shooterHood.stopMotor();
    }

    public void setHoodAngle(double setHoodAngle) {
        m_hoodPidController.setReference(setHoodAngle, CANSparkMax.ControlType.kPosition);
        m_goalAngle = setHoodAngle;
    }

    @Override
    public void simulationPeriodic() {
        m_shooterWheelSimulator.update();
        m_shooterHood.updateSim();
        m_hoodMotorSim.update();
    }

    public void runShooter(double rpm) {
        m_wheelPidController.setReference(rpm, CANSparkMax.ControlType.kVelocity);
        m_rpmGoal = rpm;
    }

    public void stopShooter() {
        m_shooterWheel.set(0);
    }

    public void feedIn() {
        m_feederWheel.set(FEED_SPEED);
    }

    public void feedOut() {
        m_feederWheel.set(-FEED_SPEED);
    }

    public void feedStop() {
        m_feederWheel.stopMotor();
    }

    public boolean isShooterAtSpeed() {
        return Math.abs(m_rpmGoal - m_wheelEncoder.getVelocity()) / m_rpmGoal <= ALLOWED_SHOOTER_ERROR;
    }

    public boolean isHoodAtAngle() {
        return Math.abs(m_goalAngle - m_hoodEncoder.getPosition()) <= ALLOWED_ANGLE_ERROR;
    }

}
