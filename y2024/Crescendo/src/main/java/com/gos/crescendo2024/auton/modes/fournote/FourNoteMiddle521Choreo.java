
package com.gos.crescendo2024.auton.modes.fournote;

import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.crescendo2024.PathPlannerUtils.followChoreoPath;

public class FourNoteMiddle521Choreo extends SequentialCommandGroup {

    private static final String PATH_BASE = "FourNoteMiddle521";

    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public FourNoteMiddle521Choreo() {
        super(
            NamedCommands.getCommand("AimAndShootIntoSpeaker"),
            Commands.deadline(
                followChoreoPath(PATH_BASE + ".1"),
                NamedCommands.getCommand("IntakePiece")
            ),
            followChoreoPath(PATH_BASE + ".2"),
            NamedCommands.getCommand("AimAndShootIntoSpeaker"),
            Commands.parallel(
                followChoreoPath(PATH_BASE + ".3"),
                NamedCommands.getCommand("IntakePiece")
            ),
            Commands.deadline(
                followChoreoPath(PATH_BASE + ".4"),
                NamedCommands.getCommand("PrepSpeakerShot")
            ),
            NamedCommands.getCommand("AimAndShootIntoSpeaker"),
            Commands.deadline(
                followChoreoPath(PATH_BASE + ".5"),
                NamedCommands.getCommand("IntakePiece")
            ),
            NamedCommands.getCommand("AimAndShootIntoSpeaker")
        );
    }
}

