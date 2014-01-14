/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team4159.support;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.parsing.IDeviceController;

/**
 *
 * @author gavin
 */
public class CombinedMotor implements SpeedController, IDeviceController
{
	private final SpeedController a;
	private final SpeedController b;
	
	public CombinedMotor (SpeedController a, SpeedController b)
	{
		this.a = a;
		this.b = b;
	}

	public double get ()
	{
		return (this.a.get () + this.b.get ()) / 2;
	}

	public void set (double speed, byte syncGroup)
	{
		set (speed);
	}

	public void set (double speed)
	{
		a.set (speed);
		b.set (speed);
	}

	public void disable ()
	{
		a.disable ();
		b.disable ();
	}

	public void pidWrite (double value)
	{
		a.pidWrite (value);
		b.pidWrite (value);
	}
}