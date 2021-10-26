// RobotBuilder Version: 1.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc3504.BlueTeamEOPP.subsystems;

import org.usfirst.frc3504.BlueTeamEOPP.RobotMap;
import org.usfirst.frc3504.BlueTeamEOPP.commands.*;
import edu.wpi.first.wpilibj.*;

import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class DriveSystem extends Subsystem {

    public DriveSystem() {

    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    SpeedController leftTalon = RobotMap.driveSystemleftTalon;
    SpeedController rightTalon = RobotMap.driveSystemrightTalon;
    RobotDrive robotDrive21 = RobotMap.driveSystemRobotDrive21;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS


    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND
        setDefaultCommand(new DriveByJoystick());
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void takeJoystickInputs(Joystick drive) {
        robotDrive21.arcadeDrive(drive);
    }

    public void start(double speed) {
        robotDrive21.drive(speed, 0);
    }

    public void stop() {
        robotDrive21.drive(0,0);
    }
}
