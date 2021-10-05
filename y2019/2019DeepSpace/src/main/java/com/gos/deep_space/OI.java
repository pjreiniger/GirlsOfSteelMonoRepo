/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.deep_space;

import com.gos.deep_space.commands.BabyDriveBackwards;
import com.gos.deep_space.commands.BabyDriveForward;
import com.gos.deep_space.commands.ClimberManual;
import com.gos.deep_space.commands.ClimberToSetPoint;
import com.gos.deep_space.commands.CollectorCollect;
import com.gos.deep_space.commands.CollectorRelease;
import com.gos.deep_space.commands.HatchCollect;
import com.gos.deep_space.commands.HatchRelease;
import com.gos.deep_space.commands.PivotManual;
import com.gos.deep_space.commands.PivotToGround;
import com.gos.deep_space.commands.PivotToRocket;
import com.gos.deep_space.commands.PivotToShip;
import com.gos.deep_space.subsystems.Climber;
import com.gos.deep_space.subsystems.Pivot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.POVButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    public final Joystick m_drivingPad;
    public final Joystick m_operatingPad;

    public OI() {
        m_drivingPad = new Joystick(0);
        m_operatingPad = new Joystick(1);

        // Climber buttons
        POVButton frontToZero = new POVButton(m_drivingPad, 90);
        frontToZero.whenPressed(new ClimberToSetPoint(Climber.FIRST_GOAL_POS, Climber.ClimberType.Front));

        POVButton backToZero = new POVButton(m_drivingPad, 270);
        backToZero.whenPressed(new ClimberToSetPoint(Climber.FIRST_GOAL_POS, Climber.ClimberType.Back));

        POVButton toSecondUp = new POVButton(m_drivingPad, 180);
        toSecondUp.whenPressed(new ClimberToSetPoint(Climber.SECOND_GOAL_POS, Climber.ClimberType.All));

        POVButton toThirdUp = new POVButton(m_drivingPad, 0);
        toThirdUp.whenPressed(new ClimberToSetPoint(Climber.THIRD_GOAL_POS, Climber.ClimberType.All));

        JoystickButton frontDown = new JoystickButton(m_drivingPad, 2);
        frontDown.whileHeld(new ClimberManual(false, Climber.ClimberType.Front));

        JoystickButton backDown = new JoystickButton(m_drivingPad, 4);
        backDown.whileHeld(new ClimberManual(false, Climber.ClimberType.Back));

        JoystickButton allUp = new JoystickButton(m_drivingPad, 5);
        allUp.whileHeld(new ClimberManual(true, Climber.ClimberType.All));

        JoystickButton allDown = new JoystickButton(m_drivingPad, 6);
        allDown.whileHeld(new ClimberManual(false, Climber.ClimberType.All));

        JoystickButton frontUp = new JoystickButton(m_drivingPad, 1);
        frontUp.whileHeld(new ClimberManual(true, Climber.ClimberType.Front));

        JoystickButton backUp = new JoystickButton(m_drivingPad, 3);
        backUp.whileHeld(new ClimberManual(true, Climber.ClimberType.Back));


        // allToZero = new POVButton(drivingPad, 180);
        // allToZero.whenPressed(new ClimberAllToZero());

        // robotToSecond = new POVButton(drivingPad, 270);
        // robotToSecond.whenPressed(new RobotToPlatform(2));

        // robotToThird = new POVButton(drivingPad, 90);
        // robotToThird.whenPressed(new RobotToPlatform(3));

        //Lidar button (testing purposes only)
        // lidarDrive = new JoystickButton(drivingPad, 9);
        // lidarDrive.whenPressed(new LidarDriveForward(82, true));

        // BabyDrive buttons
        JoystickButton babyDriveForward = new JoystickButton(m_drivingPad, 8);
        babyDriveForward.whileHeld(new BabyDriveForward());

        JoystickButton babyDriveBackward = new JoystickButton(m_drivingPad, 7);
        babyDriveBackward.whileHeld(new BabyDriveBackwards());

        // Collector buttons
        JoystickButton collect = new JoystickButton(m_operatingPad, 5);
        collect.whileHeld(new CollectorCollect());

        JoystickButton release = new JoystickButton(m_operatingPad, 6);
        release.whileHeld(new CollectorRelease());

        // Hatch buttons
        JoystickButton hatchCollect = new JoystickButton(m_operatingPad, 7);
        hatchCollect.whileHeld(new HatchCollect());

        JoystickButton hatchRelease = new JoystickButton(m_operatingPad, 8);
        hatchRelease.whileHeld(new HatchRelease());

        // Pivot buttons
        // negative is down, positive is up
        // must start up
        JoystickButton pivotUp = new JoystickButton(m_operatingPad, 2);
        pivotUp.whileHeld(new PivotManual(Pivot.PivotDirection.Up));

        JoystickButton pivotDown = new JoystickButton(m_operatingPad, 1);
        pivotDown.whileHeld(new PivotManual(Pivot.PivotDirection.Down));

        JoystickButton pivotGround = new JoystickButton(m_operatingPad, 3);
        pivotGround.whenPressed(new PivotToGround());

        JoystickButton pivotRocket = new JoystickButton(m_operatingPad, 4);
        pivotRocket.whenPressed(new PivotToRocket());

        JoystickButton pivotShip = new JoystickButton(m_operatingPad, 10);
        pivotShip.whenPressed(new PivotToShip());

        // DriveByVision button
        // driveByVision = new JoystickButton (operatingPad, 9);
        // driveByVision.whenPressed(new DriveByVision());
    }

    public double getLeftUpAndDown() {
        return -m_drivingPad.getY();
    }

    public double getRightSideToSide() {
        return m_drivingPad.getRawAxis(4);
    }

}
