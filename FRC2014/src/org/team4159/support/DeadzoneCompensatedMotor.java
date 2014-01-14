package org.team4159.support;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.parsing.IDeviceController;

public class DeadzoneCompensatedMotor implements SpeedController, IDeviceController
{
	private final SpeedController sub;
	private final double deadzoneLower, deadzoneUpper;
	private final double lowerSlope, upperSlope;
	private double value;
	
	public DeadzoneCompensatedMotor (SpeedController sub, double lower, double upper)
	{
		this.sub = sub;
		this.deadzoneLower = lower;
		this.deadzoneUpper = upper;
		this.lowerSlope = 1 + lower;
		this.upperSlope = 1 - upper;
	}

	public void pidWrite (double output)
	{
		set (output);
	}

	public double get ()
	{
		return value;
	}

	public void set (double speed, byte syncGroup)
	{
		set (speed);
	}

	public void set (double speed)
	{
		value = speed;
		if (speed < 0)
			sub.set (lowerSlope * speed + deadzoneLower);
		else if (speed > 0)
			sub.set (upperSlope * speed + deadzoneUpper);
		else
			sub.set (0);
	}
	
	public void disable ()
	{
		sub.disable ();
	}
}
