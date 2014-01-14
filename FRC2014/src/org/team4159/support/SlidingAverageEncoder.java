package org.team4159.support;

import java.util.Timer;
import java.util.TimerTask;
import edu.wpi.first.wpilibj.Encoder;

public class SlidingAverageEncoder extends Encoder
{
	private final double[] samples;
	private int index = 0;
	private final long period;
	private Timer timer;
	
	public SlidingAverageEncoder (int aChannel, int bChannel, int samples, long period)
	{
		this (aChannel, bChannel, false, samples, period);
	}
	
	public SlidingAverageEncoder (int aChannel, int bChannel, boolean reverseDirection, int samples, long period)
	{
		super (aChannel, bChannel, reverseDirection);
		this.samples = new double[samples];
		this.period = period;
	}
	
	public double getRate ()
	{
		double firstSample, lastSample;
		
		synchronized (samples)
		{
			firstSample = samples[index];
			lastSample = samples[index == 0 ? samples.length - 1 : index - 1];
		}
		
		double d = lastSample - firstSample;
		double t = (samples.length - 1) * (period / 1000.);
		return d / t;
	}
	
	public void start ()
	{
		super.start ();
		
		if (timer == null)
		{
			timer = new Timer ();
			timer.scheduleAtFixedRate (new Task (), 0, period);
		}
	}
	
	public void stop ()
	{
		if (timer != null)
		{
			timer.cancel ();
			timer = null;
		}
		
		super.stop ();
	}
	
	private class Task extends TimerTask
	{
		public void run ()
		{
			synchronized (samples)
			{
				samples[index] = SlidingAverageEncoder.super.getDistance ();
				index = (index + 1) % samples.length;
			}
		}
	}
}