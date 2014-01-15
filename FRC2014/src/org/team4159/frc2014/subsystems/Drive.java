package org.team4159.frc2014.subsystems;

import org.team4159.frc2014.IO;
import org.team4159.support.Subsystem;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public final class Drive extends RobotDrive implements Subsystem
{
	public static final double POSITION_TOLERANCE = 0.1;
	
	public static final double KP = 0.5;
	public static final double KI = 0.1;
	public static final double KD = 0.0;
	
	public static final Drive instance = new Drive ();
	
	private Drive ()
	{
            //replace with actual speed controller port #
		super (1,2);
		
		setInvertedMotor (MotorType.kRearLeft, true);
		setInvertedMotor (MotorType.kRearRight, true);
		
//		IO.drivePIDLeft.setAbsoluteTolerance (POSITION_TOLERANCE);
//		IO.drivePIDRight.setAbsoluteTolerance (POSITION_TOLERANCE);
	}
	
	/**
	 * Shifts the gearbox to high or low speed position.
	 * @param high Shift to high-speed if true, low-speed if false.
	 */
	public void setGearboxPosition (boolean high)
	{
	}
	
	/**
	 * Gets current position of gearbox.
	 * @return true if high-speed, low-speed if false
	 */
	public boolean getGearboxPosition ()
	{
            return false;
	}
	
	/**
	 * {@inheritDoc}<br/><br/>
	 * This method is overridden to disable PID when manually driven.
	 */
	public void setLeftRightMotorOutputs (double left, double right)
	{
//		IO.drivePIDLeft.disable ();
//		IO.drivePIDRight.disable ();
//		super.setLeftRightMotorOutputs (left, right);
	}

	/**
	 * Incrementally positions the robot by setting a position
	 * achieved by the relative movements of the wheels.
	 * @param left The amount the left side should move.
	 * @param right The amount the right side should move.
	 */
	public void incrementalPosition (double left, double right)
	{
//		IO.drivePIDLeft.disable ();
//		IO.drivePIDRight.disable ();
//		
//		IO.drivePIDLeft.reset ();
//		IO.drivePIDRight.reset ();
//		
//		IO.drivePIDLeft.setSetpoint (IO.driveEncoderLeft.getDistance () + left);
//		IO.drivePIDRight.setSetpoint (IO.driveEncoderRight.getDistance () + right);
//		
//		IO.drivePIDLeft.enable ();
//		IO.drivePIDRight.enable ();
//		
//		while (true)
//		{
//			if (!IO.drivePIDLeft.onTarget ())
//				continue;
//			if (!IO.drivePIDRight.onTarget ())
//				continue;
//			break;
//		}
	}
}
