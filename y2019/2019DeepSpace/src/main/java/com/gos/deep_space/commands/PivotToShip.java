/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.deep_space.commands;

import com.gos.deep_space.Robot;
import com.gos.deep_space.subsystems.Pivot;
import edu.wpi.first.wpilibj.command.Command;

public class PivotToShip extends Command {
    public PivotToShip() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.m_pivot);

    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        System.out.println("init PivotToShip");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.m_pivot.setGoalPivotPosition(Pivot.PIVOT_CARGO);
        Robot.m_pivot.holdPivotPosition();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return Robot.m_pivot.checkCurrentPivotPosition(Pivot.PIVOT_CARGO);

    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.m_pivot.pivotStop();
        System.out.println("end PivotToShip");

    }
}
