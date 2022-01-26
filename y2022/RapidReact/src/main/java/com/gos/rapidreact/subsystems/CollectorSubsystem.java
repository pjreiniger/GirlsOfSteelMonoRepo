package com.gos.rapidreact.subsystems;

import com.gos.lib.properties.PidProperty;
import com.gos.lib.rev.RevPidPropertyBuilder;
import com.gos.rapidreact.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SimableCANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.snobotv2.module_wrappers.rev.RevEncoderSimWrapper;
import org.snobotv2.module_wrappers.rev.RevMotorControllerSimWrapper;
import org.snobotv2.sim_wrappers.SingleJointedArmSimWrapper;

public class CollectorSubsystem extends SubsystemBase {
    private static final double ROLLER_SPEED = 0.5;
    private static final double PIVOT_SPEED = 0.5;

    private static final double GEARING = 350;
    private static final double J_KG_METERS_SQUARED = 1;
    private static final double ARM_LENGTH_METERS = Units.inchesToMeters(16);
    private static final double MIN_ANGLE_RADS = 0;
    private static final double MAX_ANGLE_RADS = Math.PI / 2;
    private static final double ARM_MASS_KG = Units.lbsToKilograms(5);
    private static final boolean SIMULATE_GRAVITY = true;

    private final SimableCANSparkMax m_roller;
    private final SimableCANSparkMax m_pivot;

    private final RelativeEncoder m_pivotEncoder;

    private SingleJointedArmSimWrapper m_simulator;

    private PidProperty m_pivotPID;

    private SparkMaxPIDController m_pidController;

    public CollectorSubsystem() {
        m_roller = new SimableCANSparkMax(Constants.COLLECTOR_ROLLER, CANSparkMaxLowLevel.MotorType.kBrushless);
        m_pivot = new SimableCANSparkMax(Constants.COLLECTOR_PIVOT, CANSparkMaxLowLevel.MotorType.kBrushless);

        m_pivotEncoder = m_pivot.getEncoder();

        m_pidController = m_pivot.getPIDController();

        CANSparkMax.IdleMode idleModeBreak = CANSparkMax.IdleMode.kBrake;
        CANSparkMax.IdleMode idleModeCoast = CANSparkMax.IdleMode.kCoast;
        m_pivot.setIdleMode(idleModeBreak);
        m_roller.setIdleMode(idleModeCoast);

        m_pivotPID = new RevPidPropertyBuilder("Pivot PID", false, m_pidController, 0)
            .addP(0)
            .addD(0)
            .addMaxAcceleration(0)
            .addMaxVelocity(0)
            .build();

        if (RobotBase.isSimulation()) {
            SingleJointedArmSim armSim = new SingleJointedArmSim(DCMotor.getNeo550(1), GEARING, J_KG_METERS_SQUARED,
                ARM_LENGTH_METERS, MIN_ANGLE_RADS, MAX_ANGLE_RADS, ARM_MASS_KG, SIMULATE_GRAVITY);
            m_simulator = new SingleJointedArmSimWrapper(armSim, new RevMotorControllerSimWrapper(m_pivot),
                RevEncoderSimWrapper.create(m_pivot));
        }
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Pivot Encoder", m_pivotEncoder.getPosition());
        m_pivotPID.updateIfChanged();
    }

    public void collectorDown() {
        m_pivot.set(PIVOT_SPEED);
    }

    public void collectorUp() {
        m_pivot.set(-PIVOT_SPEED);
    }

    public void rollerIn() {
        m_roller.set(ROLLER_SPEED);
    }

    public void rollerOut() {
        m_roller.set(-ROLLER_SPEED);
    }

    public void rollerStop() {
        m_roller.set(0);
    }

    public void pivotStop() {
        m_pivot.set(0);
    }

    public void collectorDownPID() {
        m_pidController.setReference(0, CANSparkMax.ControlType.kPosition);
    }

    public void collectorUpPID() {
        m_pidController.setReference(Math.PI / 2, CANSparkMax.ControlType.kPosition);
    }

    @Override
    public void simulationPeriodic() {
        m_simulator.update();
    }
}

