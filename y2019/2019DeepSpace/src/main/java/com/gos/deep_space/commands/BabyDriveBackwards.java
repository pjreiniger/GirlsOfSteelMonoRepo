/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.deep_space.commands;

import com.gos.deep_space.subsystems.BabyDrive;
import edu.wpi.first.wpilibj.command.Command;

public class BabyDriveBackwards extends Command {
    private static final double BABYDRIVE_SPEED = 0.4;

    private final BabyDrive m_babyDrive;

    public BabyDriveBackwards(BabyDrive babyDrive) {
        m_babyDrive = babyDrive;
        requires(m_babyDrive);
    }


    @Override
    protected void initialize() {
        System.out.println("init BabyDriveBackwards");

    }


    @Override
    protected void execute() {
        m_babyDrive.babyDriveSetSpeed(BABYDRIVE_SPEED);
    }


    @Override
    protected boolean isFinished() {
        return false;
    }


    @Override
    protected void end() {
        m_babyDrive.babyDriveStop();
        System.out.println("end BabyDriveBackwards");
    }

}
