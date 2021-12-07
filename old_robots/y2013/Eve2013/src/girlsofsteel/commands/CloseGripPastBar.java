/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

import girlsofsteel.subsystems.Gripper;

/**
 *
 * @author sam
 */

//this command reads the limit switches under the grippers. When press the grippers will close after safely passing the bar
public class CloseGripPastBar extends CommandBase{
    private final Gripper gripper;

    public CloseGripPastBar(Gripper gripper) {
        this.gripper = gripper;

        requires(gripper);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
        return gripper.pastBar();
    }

    @Override
    protected void end() {
        gripper.closeGrip();
    }

    @Override
    protected void interrupted() {
    }

}
