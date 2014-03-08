package org.team4159.frc2014.subsystems;

import org.team4159.frc2014.IO;
import org.team4159.support.Subsystem;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 *
 * @author Waylin
 */

public final class Drive extends RobotDrive implements Subsystem
{
	public static final double PID_kP = 0.03;
	
	public static final Drive instance = new Drive ();
	
	private Drive ()
	{
            //driving on 1 left, 2 right
		super (IO.driveMotorLeft,IO.driveMotorRight);
	}
	

	/**
	 * Shifts the gearbox to high or low speed position.
	 * @param high Shift to high-speed if true, low-speed if false.
	 */
	public void setGearboxPosition (boolean high)
	{
            IO.driveGearbox.set (high ? Value.kForward : Value.kReverse);
	}
	
	/**
	 * Gets current position of gearbox.
	 * @return true if high-speed, low-speed if false
	 */
	public boolean getGearboxPosition ()
	{
            return IO.driveGearbox.get () == Value.kForward;
	}
        
        public void correctedDrive(double move, double rotate)
        {
            if(Math.abs(IO.drivingGyro.getRate())< .2){
            //stationary
            if(Math.abs(move)<.05 && Math.abs(rotate)<.05){
                IO.drivingGyro.reset();
            }
            //if driving straight
            else if(Math.abs(move)>.05 && Math.abs(rotate-0)<.05){
                // if moving forward
                if(move < 0)
                    super.arcadeDrive(move,-IO.drivingGyro.getAngle()*PID_kP);
                //if moving backward
                else if(move > 0)
                    super.arcadeDrive(move, IO.drivingGyro.getAngle()*PID_kP);
            }
            else if(Math.abs(rotate)<.5){
                super.arcadeDrive(move, rotate);
                IO.drivingGyro.reset();
            }
            }
            else{
                super.arcadeDrive(move, rotate);
                IO.drivingGyro.reset();
            }
        }
}
