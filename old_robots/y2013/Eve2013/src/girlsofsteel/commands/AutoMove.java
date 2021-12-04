package girlsofsteel.commands;

public class AutoMove extends CommandBase {

    double x;
    double y;
    double time;

    double startTime;

    /**
     * Moves in the direction given for a certain amount of time automatically.
     * @param x the percentage to move in the x direction (-1 to 1)
     * @param y the percentage to move in the y direction (-1 to 1)
     * @param time the length of time to move
     */
    public AutoMove(double x, double y, double time){
        this.x = x;
        this.y = y;
        this.time = time;
    }

    @Override
    protected void initialize() {
        startTime = timeSinceInitialized();
    }

    @Override
    protected void execute() {
        chassis.driveVoltage(x, y, 0, 1.0, true);
    }

    @Override
    protected boolean isFinished() {
        return timeSinceInitialized() - startTime > time;
    }

    @Override
    protected void end() {
        chassis.stopJags();
    }

    @Override
    protected void interrupted() {
        end();
    }

}
