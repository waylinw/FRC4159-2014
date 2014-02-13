package org.team4159.frc2014;

import java.util.Timer;
import java.util.TimerTask;
import edu.wpi.first.wpilibj.ADXL345_I2C.AllAxes;

/*
final class Vec
{
	public final double x, y, z;
	
	public Vec ()
	{
		x = 0;
		y = 0;
		z = 0;
	}
	
	public Vec (double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vec (AllAxes ax)
	{
		this.x = ax.XAxis;
		this.y = ax.YAxis;
		this.z = ax.ZAxis;
	}
	
	public double length ()
	{
		return Math.sqrt (x*x + y*y + z*z);
	}
	
	public Vec normalize ()
	{
		double len = length ();
		if (len != 0)
			return new Vec (x / len, y / len, z / len);
		else
			return new Vec ();
	}
	
	public Vec add (Vec o)
	{
		return new Vec (x + o.x, y + o.y, z + o.z);
	}
	
	public Vec sub (Vec o)
	{
		return new Vec (x - o.x, y - o.y, z - o.z);
	}
	
	public Vec mult (double s)
	{
		return new Vec (s*x, s*y, s*z);
	}
	
	public double dot (Vec o)
	{
		return x * o.x + y * o.y + z * o.z;
	}
	
	public Vec cross (Vec o)
	{
		return new Vec (
			y * o.z - z * o.y,
			z * o.x - x * o.z,
			x * o.y - y * o.x
		);
	}
	
	public boolean zeroed ()
	{
		return (x == 0) && (y == 0) && (z == 0);
	}
}

public class TipProtectionOld
{
	private static final long CHECK_INTERVAL_MS = 50;
	private static final double IDLE_THRESHOLD = 0.1;
	private static final double TIP_THRESHOLD = 0.5;
	
	private static final double INACTIVITY_MS = 2500;
	private static final double ACTIVITY_MS = 250;
	
	private static final int INACTIVITY_INTERVALS =
		(int)(INACTIVITY_MS / CHECK_INTERVAL_MS);
	private static final int ACTIVITY_INTERVALS =
		(int)(ACTIVITY_MS / CHECK_INTERVAL_MS);
	
	private Vec gravity = new Vec ();
	private Vec lastAccel = new Vec ();
	
	private boolean idle = true;
	private int idleCount;
	
	private TipProtectionOld ()
	{
		(new Timer ()).scheduleAtFixedRate (new TimerTask () {
			public void run () { loop (); }
		}, 0, CHECK_INTERVAL_MS);
	}
	
	private void loop ()
	{
		Vec curAccel = new Vec (IO.accelerometer.getAccelerations ());
		
		double len = curAccel.sub (lastAccel).length ();
		lastAccel = curAccel;
		
		boolean curIdle = (len <= IDLE_THRESHOLD);
		if (idle)
		{
			if (curIdle)
			{
				idleCount = 0;
				gravity = curAccel;
			}
			else
			{
				if (idleCount++ >= ACTIVITY_INTERVALS)
				{
					idleCount = 0;
					idle = false;
				}
			}
		}
		else
		{
			if (curIdle)
			{
				if (idleCount++ >= INACTIVITY_INTERVALS)
				{
					idleCount = 0;
					idle = true;
				}
			}
			else
			{
				idleCount = 0;
			}
		}
		
		if (!idle && !gravity.zeroed ())
		{
			// subtract gravity vector
			final Vec v = curAccel.sub (gravity);
			
			// find angle of gravity with respect to z-axis
			final double cos_grav_Z = gravity.z / gravity.length ();
			final double sin_grav_Z = Math.sqrt (1 - cos_grav_Z * cos_grav_Z);
			
			// find rotation axis
			// axis = (gravity) x (z-axis)
			final Vec rotationAxis = new Vec (gravity.y, -gravity.x, 0);
			
			// normalize rotation axis
			final Vec k = rotationAxis.normalize ();
			
			// use Rodrigues' rotation formula to orient p
			final Vec vrot = v.mult (cos_grav_Z)
				.add (k.cross (v).mult (sin_grav_Z))
				.add (k.mult (k.dot (v) * (1 - cos_grav_Z)));
			
			System.out.println ("vz: " + Math.abs (vrot.z));
		}
	}
	
	private static TipProtectionOld instance;
	public static TipProtectionOld getInstance ()
	{
		if (instance == null)
			instance = new TipProtectionOld ();
		return instance;
	}
}
*/