package com.gos.chargedup.commands;


import com.gos.chargedup.subsystems.ArmSubsystem;
import com.gos.chargedup.subsystems.IntakeSubsystem;
import com.gos.chargedup.subsystems.TurretSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TurretArmToIntakeCommandGroup extends SequentialCommandGroup {
    public TurretArmToIntakeCommandGroup(ArmSubsystem arm, IntakeSubsystem intake, TurretSubsystem turret) {
        addCommands(turret.commandTurretPID(0.0));
        addCommands(arm.commandPivotArmDown());
        addCommands(intake.createIntakeInCommand());
    }
}
