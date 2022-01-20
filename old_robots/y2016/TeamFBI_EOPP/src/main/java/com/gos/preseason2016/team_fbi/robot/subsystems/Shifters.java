package com.gos.preseason2016.team_fbi.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shifters extends Subsystem {

    private final DoubleSolenoid m_shifterLeft = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);
    private final DoubleSolenoid m_shifterRight = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 2, 3);

    @Override
    public void initDefaultCommand() {

    }

    public void shiftLeft(boolean highgear) {
        if (highgear) {
            m_shifterLeft.set(DoubleSolenoid.Value.kReverse);
            System.out.println("Shifting left side into high gear (fwd)");
        } else {
            m_shifterLeft.set(DoubleSolenoid.Value.kForward);
            System.out.println("Shifting left side into low gear (rev)");
        }
    }

    public void shiftRight(boolean highgear) {
        if (highgear) {
            m_shifterRight.set(DoubleSolenoid.Value.kReverse);
            System.out.println("Shifting right side into high gear (fwd)");
        } else {
            m_shifterRight.set(DoubleSolenoid.Value.kForward);
            System.out.println("Shifting right side into low gear (rev)");
        }
    }
}
