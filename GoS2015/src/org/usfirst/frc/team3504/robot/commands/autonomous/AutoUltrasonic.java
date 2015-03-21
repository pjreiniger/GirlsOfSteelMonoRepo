package org.usfirst.frc.team3504.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Authors: Alexa, Corinne, Sarah
 */
public class AutoUltrasonic extends CommandGroup {

	public AutoUltrasonic() {
		addSequential(new AutoCollector());
		addSequential(new Lifting());
		addSequential(new AutoFirstPickup());
		addSequential(new AutoCollector());
		addSequential(new Lifting());
		// used to get first can and tote

		addSequential(new AutoCollector());
		// commented out to eliminate errors, not deleted because we don't know
		// if we need it still or not
		// addSequential(new AutoDriveForward());
		addSequential(new Lifting());
		// gets middle tote assuming partner cleared second can

		addSequential(new AutoCollector());
		// commented out to eliminate errors, not deleted because we don't know
		// if we need it still or not
		// addSequential(new AutoDriveForward());
		addSequential(new Lifting());
		// gets last tote assuming partner cleared third can

		addSequential(new AutoDriveLeft());
		addSequential(new Lifting()); // just down

		// Add Commands here:
		// e.g. addSequential(new Command1());
		// addSequential(new Command2());
		// these will run in order.

		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		// addSequential(new Command2());
		// Command1 and Command2 will run in parallel.

		// A command group will require all of the subsystems that each member
		// would require.
		// e.g. if Command1 requires chassis, and Command2 requires arm,
		// a CommandGroup containing them would require both the chassis and the
		// arm.
	}
}
