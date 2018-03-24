package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;
import org.usfirst.frc.team3504.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveByMotionMagic extends Command {
	
	private double encoderTicks; //in sensor units
	private double targetHeading; //in degrees
	private boolean resetPigeon;
	
	private int timeoutCtr;

	private WPI_TalonSRX leftTalon = Robot.chassis.getLeftTalon();
	private WPI_TalonSRX rightTalon = Robot.chassis.getRightTalon();
	
	private final static double DISTANCE_FINISH_THRESHOLD = 500; //TODO tune (in encoder ticks)
	private final static double TURNING_FINISH_THRESHOLD = 2.0; //TODO tune (in degrees)
	
	private final static double DISTANCE_TIMER_THRESHOLD = 1500; //TODO tune (in encoder ticks)
	private final static double TURNING_TIMER_THRESHOLD = 7.0; //TODO tune (in degrees)
	
	private final static double TIMER_THRESHOLD = 1.5; //in seconds

    public DriveByMotionMagic(double inches, double degrees) {
		encoderTicks = RobotMap.CODES_PER_WHEEL_REV * (inches / (RobotMap.WHEEL_DIAMETER * Math.PI));
		targetHeading = degrees;
		resetPigeon = true;
		requires(Robot.chassis);
		System.out.println("DriveByMotionMagic: constructed");
    }
    
    public DriveByMotionMagic(double inches, double degrees, boolean reset) {
		encoderTicks = RobotMap.CODES_PER_WHEEL_REV * (inches / (RobotMap.WHEEL_DIAMETER * Math.PI));
		targetHeading = degrees;
		resetPigeon = reset;
		requires(Robot.chassis);
		System.out.println("DriveByMotionMagic: constructed");
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.chassis.setInverted(true);
    	//System.out.println("DriveByMotionMagic: motors inverted");
    	
    	Robot.chassis.configForMotionMagic();
    	//System.out.println("DriveByMotionMagic: configured for motion magic");
    	
    	if (resetPigeon) Robot.chassis.zeroSensors();
    	else Robot.chassis.zeroEncoder();
    	System.out.println("DriveByMotionMagic: sensors zeroed");
    	
    	System.out.println("DriveByMotionMagic encoder ticks + heading: " + encoderTicks + targetHeading);
    	
		rightTalon.set(ControlMode.MotionMagic, 2 * encoderTicks, DemandType.AuxPID, 10 * targetHeading);
		leftTalon.follow(rightTalon, FollowerType.AuxOutput1);
		
		System.out.println("DriveByMotionMagic: instructions sent, started");
		
		timeoutCtr = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (!resetPigeon || targetHeading == 0) //if trying to drive straight
    	{
    		double currentTicks = rightTalon.getSensorCollection().getQuadraturePosition();
    		double error = Math.abs(encoderTicks - currentTicks);
    		if (error < DISTANCE_TIMER_THRESHOLD) timeoutCtr++;
    	}
    	else //if trying to turn to an angle
    	{
    		double currentHeading = Robot.chassis.getYaw();
    		double error = Math.abs(targetHeading - currentHeading);
    		//System.out.println("DriveByMotionMagic: turning error = " + error);
    		if (error < TURNING_TIMER_THRESHOLD) timeoutCtr++;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (timeoutCtr > (TIMER_THRESHOLD * 50)) return true;
    	else if (!resetPigeon || targetHeading == 0) //if trying to drive straight
    	{
    		double currentTicks = rightTalon.getSensorCollection().getQuadraturePosition();
    		double error = Math.abs(encoderTicks - currentTicks);
    		//System.out.println("DriveByMotionMagic: distance error = " + error);
    		return (error < DISTANCE_FINISH_THRESHOLD);
    	}
    	else //if trying to turn to an angle
    	{
    		double currentHeading = Robot.chassis.getYaw();
    		double error = Math.abs(targetHeading - currentHeading);
    		//System.out.println("DriveByMotionMagic: turning error = " + error);
    		return (error < TURNING_FINISH_THRESHOLD);
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("DriveByMotionMagic: ended");
    	Robot.chassis.stop();
    	Robot.chassis.setInverted(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	System.out.println("DriveByMotionMagic: interrupted");
    	end();
    }
}
