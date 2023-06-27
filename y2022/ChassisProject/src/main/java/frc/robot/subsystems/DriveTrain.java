// RobotBuilder Version: 3.1
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */
public class DriveTrain extends SubsystemBase {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final WPI_TalonSRX m_leftLeaderMotor;
    private final WPI_TalonSRX m_leftFollowerMotor;
    private final WPI_TalonSRX m_rightLeaderMotor;
    private final WPI_TalonSRX m_rightFollowerMotor;
    private final DifferentialDrive m_differentialDrive;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    /**
     *
     */
    public DriveTrain() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        m_leftLeaderMotor = new WPI_TalonSRX(1);
        addChild("LeftLeaderMotor", m_leftLeaderMotor);
        m_leftLeaderMotor.setInverted(false);

        m_leftFollowerMotor = new WPI_TalonSRX(2);
        addChild("LeftFollowerMotor", m_leftFollowerMotor);
        m_leftFollowerMotor.follow(m_leftLeaderMotor);

        m_rightLeaderMotor = new WPI_TalonSRX(3);
        addChild("RightLeaderMotor", m_rightLeaderMotor);
        m_rightLeaderMotor.setInverted(true);

        m_rightFollowerMotor = new WPI_TalonSRX(4);
        addChild("RightFollowerMotor", m_rightFollowerMotor);
        m_rightFollowerMotor.follow(m_rightLeaderMotor);
        m_rightFollowerMotor.setInverted(true);

        m_differentialDrive = new DifferentialDrive(m_leftLeaderMotor, m_rightLeaderMotor);
        addChild("DifferentialDrive", m_differentialDrive);
        m_differentialDrive.setSafetyEnabled(true);
        m_differentialDrive.setExpiration(0.1);
        m_differentialDrive.setMaxOutput(0.75);


        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public void driveByController(double controllerSpeed, double controllerSteer) {
        m_differentialDrive.arcadeDrive(controllerSpeed, controllerSteer);
    }
}
