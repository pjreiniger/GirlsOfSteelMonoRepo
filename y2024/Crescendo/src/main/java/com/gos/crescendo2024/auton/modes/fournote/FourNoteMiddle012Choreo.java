
package com.gos.crescendo2024.auton.modes.fournote;

import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.crescendo2024.PathPlannerUtils.followChoreoPath;

public class FourNoteMiddle012Choreo extends SequentialCommandGroup {

    private static final String PATH_BASE = "FourNoteMiddle012";

    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public FourNoteMiddle012Choreo() {
        super(
            NamedCommands.getCommand("AimAndShootIntoSpeaker"),
            Commands.deadline(
                followChoreoPath(PATH_BASE + ".1"),
                NamedCommands.getCommand("IntakePiece")
            ),
            NamedCommands.getCommand("AimAndShootIntoSpeaker"),
            Commands.deadline(
                followChoreoPath(PATH_BASE + ".2"),
                NamedCommands.getCommand("IntakePiece")
            ),
            NamedCommands.getCommand("AimAndShootIntoSpeaker"),
            Commands.deadline(
                followChoreoPath(PATH_BASE + ".3"),
                NamedCommands.getCommand("IntakePiece")
            ),
            NamedCommands.getCommand("AimAndShootIntoSpeaker")
        );
    }
}

