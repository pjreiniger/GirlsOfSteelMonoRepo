package org.usfirst.frc.team3504.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3504.robot.RobotMap;

/**
 *
 */
public class DriveSystem extends Subsystem {
    private final CANTalon m_driveLeftA;
    private final CANTalon m_driveLeftB;
    private final CANTalon m_driveLeftC;

    private final CANTalon m_driveRightA;
    private final CANTalon m_driveRightB;
    private final CANTalon m_driveRightC;

    private final RobotDrive m_robotDrive;

    private double m_encOffsetValue;

    public DriveSystem() {
        m_driveLeftA = new CANTalon(RobotMap.DRIVE_LEFT_A_CAN_ID);
        //addChild("Drive Left A", driveLeftA);
        m_driveLeftB = new CANTalon(RobotMap.DRIVE_LEFT_B_CAN_ID);
        //addChild("Drive Left B", driveLeftB);
        m_driveLeftC = new CANTalon(RobotMap.DRIVE_LEFT_C_CAN_ID);
        //addChild("Drive Left C", driveLeftC);

        m_driveRightA = new CANTalon(RobotMap.DRIVE_RIGHT_A_CAN_ID);
        //addChild("Drive Right A", driveRightA);
        m_driveRightB = new CANTalon(RobotMap.DRIVE_RIGHT_B_CAN_ID);
        //addChild("Drive Right B", driveRightB);
        m_driveRightC = new CANTalon(RobotMap.DRIVE_RIGHT_C_CAN_ID);
        //addChild("Drive Right C", driveRightC);

        // On each side, all three drive motors MUST run at the same speed.
        // Use the CAN Talon Follower mode to set the speed of B and C,
        // making always run at the same speed as A.
        m_driveLeftB.changeControlMode(ControlMode.Follower);
        m_driveLeftC.changeControlMode(ControlMode.Follower);
        m_driveRightB.changeControlMode(ControlMode.Follower);
        m_driveRightC.changeControlMode(ControlMode.Follower);
        m_driveLeftB.set(m_driveLeftA.getDeviceID());
        m_driveLeftC.set(m_driveLeftA.getDeviceID());
        m_driveRightB.set(m_driveRightA.getDeviceID());
        m_driveRightC.set(m_driveRightA.getDeviceID());

        // Define a robot drive object in terms of only the A motors.
        // The B and C motors will play along at the same speed (see above.)
        m_robotDrive = new RobotDrive(m_driveLeftA, m_driveRightA);

        // Set some safety controls for the drive system
        m_robotDrive.setSafetyEnabled(true);
        m_robotDrive.setExpiration(0.1);
        m_robotDrive.setSensitivity(0.5);
        m_robotDrive.setMaxOutput(1.0);
        //robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        //robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        //robotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
        //robotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
    }

    @Override
    public void initDefaultCommand() {
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void takeJoystickInputs(Joystick joystk) {
        m_robotDrive.arcadeDrive(joystk);
    }

    public void forward() {
        m_robotDrive.drive(1.0, 0);
    }

    public void stop() {
        m_robotDrive.drive(/* speed */0, /* curve */0);
    }

    public double getEncoderRight() {
        return m_driveRightA.getEncPosition();
    }

    public double getEncoderLeft() {
        return m_driveLeftA.getEncPosition();
    }

    public double getEncoderDistance() {
        return (getEncoderLeft() - m_encOffsetValue) * RobotMap.DISTANCE_PER_PULSE;
    }

    public void resetDistance() {
        m_encOffsetValue = getEncoderLeft();
    }
}
