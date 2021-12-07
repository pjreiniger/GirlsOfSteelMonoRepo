package org.usfirst.frc.team3504.robot.subsystems;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3504.robot.RobotMap;

/**
 *
 */
@SuppressWarnings("PMD.TooManyMethods")
public class Manipulator extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    private final CANTalon m_pivotMotor;
    private final CANTalon m_collectorMotor;
    private final DoubleSolenoid m_shooter; //shoots the ball
    private final DoubleSolenoid m_arm; //opens and closes the arm to get the ball in

    public Manipulator() {
        m_pivotMotor = new CANTalon(RobotMap.ARM_PIVOT_CAN_ID);
        m_collectorMotor = new CANTalon(RobotMap.COLLECTOR_CAN_ID);
        m_shooter = new DoubleSolenoid(RobotMap.SHOOTER_PISTON_A, RobotMap.SHOOTER_PISTON_B);
        m_arm = new DoubleSolenoid(RobotMap.ARM_PISTON_A, RobotMap.ARM_PISTON_B);
    }

    public void openArm(){
        m_arm.set(DoubleSolenoid.Value.kForward);
    }

    public void closeArm(){
        m_arm.set(DoubleSolenoid.Value.kReverse);
    }

    public void shootBall() {
        m_shooter.set(DoubleSolenoid.Value.kReverse);
    }

    public void shooterIn() {
        m_shooter.set(DoubleSolenoid.Value.kForward);
    }

    public void collectBall() {
        m_collectorMotor.set(-1);
    }

    public void releaseBall() {
        m_collectorMotor.set(1);
    }

    public void stopCollecting() {
        m_collectorMotor.set(0);
    }

    public void pivotUp() {
        m_pivotMotor.set(1);
    }

    public void pivotDown() {
        m_pivotMotor.set(-1);
    }

    public void stopPivot() {
        m_pivotMotor.set(0);
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    public boolean getTopLimitSwitch(){
        return m_pivotMotor.isFwdLimitSwitchClosed() == 0;
    }

    public boolean getBottomLimitSwitch(){
        return m_collectorMotor.isRevLimitSwitchClosed() == 0;
    }
}
