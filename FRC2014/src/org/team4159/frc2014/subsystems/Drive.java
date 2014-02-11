package org.team4159.frc2014.subsystems;

import org.team4159.frc2014.IO;
import org.team4159.support.Subsystem;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public final class Drive extends RobotDrive implements Subsystem
{
	
	public static final double PID_kP = 0.5;
	
	public static final Drive instance = new Drive ();
	
	private Drive ()
	{
            //driving on 1 left, 2 right
		super (1,2);
		
//		setInvertedMotor (MotorType.kRearLeft, true);
//		setInvertedMotor (MotorType.kRearRight, true);
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
            if(Math.abs(rotate-0)<.02)
                this.arcadeDrive(move,-IO.drivingGyro.getCorrectedAngle()*PID_kP);
            else
                this.arcadeDrive(move, rotate);
            return;
        }
}
