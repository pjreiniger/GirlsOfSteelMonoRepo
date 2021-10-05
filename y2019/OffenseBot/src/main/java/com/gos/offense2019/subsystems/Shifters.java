package com.gos.offense2019.subsystems;

import com.gos.offense2019.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Shifters extends Subsystem {
    private DoubleSolenoid shifterLeft;
    private DoubleSolenoid shifterRight;

    public static enum Speed {
        kHigh, kLow
    };

    private Speed speed;

    public Shifters() {
        shifterLeft = new DoubleSolenoid(RobotMap.SHIFTER_LEFT_A, RobotMap.SHIFTER_LEFT_B);
        shifterRight = new DoubleSolenoid(RobotMap.SHIFTER_RIGHT_A, RobotMap.SHIFTER_RIGHT_B);

        addChild("shifterLeft", shifterLeft);
        addChild("shifterRight", shifterRight);
  }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void shiftGear(Speed speed) {
        this.speed = speed;
        if (speed == Speed.kLow) {
            shifterLeft.set(DoubleSolenoid.Value.kForward);
            shifterRight.set(DoubleSolenoid.Value.kForward);
        } else {
            shifterLeft.set(DoubleSolenoid.Value.kReverse);
            shifterRight.set(DoubleSolenoid.Value.kReverse);
        }
    }

    public Speed getGearSpeed() {
        return speed;
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
}
