/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team4159.support;

import edu.wpi.first.wpilibj.DriverStation;
import org.team4159.frc2013.Entry;

/**
 *
 * @author Team4159
 */
public abstract class Controller
{
	private static class ExitException extends RuntimeException {}
	private static Controller currentController = null;
	
	private class ControllerThread extends Thread
	{
		public void run ()
		{
			Controller lastController = currentController;
			
			try {
				currentController = Controller.this;
				Controller.this.run ();
			} catch (ExitException e) {
			} catch (Throwable e) {
				Controller.this.controllerError = e;
				System.err.println ("Uncaught exception in " + Controller.this.getClass ().getName () + "!");
				e.printStackTrace ();
			} finally {
				currentController = lastController;
			}
		}
	}
	
	private final long timingInterval;
	private long timingStart;
	private long timingAccumulator;
	
	public final int controllerMode;
	
	private final Object controllerLock = new Object ();
	private final ControllerThread controllerThread = new ControllerThread ();
	private boolean controllerStarted = false;
	private boolean controllerRunning = false;
	private Throwable controllerError = null;
	
	protected final DriverStation driverStation = DriverStation.getInstance ();
	
	protected Controller (int mode)
	{
		this (mode, Entry.TICK_INTERVAL_MS);
	}
	
	protected Controller (int mode, long interval)
	{
		controllerMode = mode;
		timingInterval = interval;
		resetTiming ();
	}
	
	protected final void beginTiming ()
	{
		timingStart = System.currentTimeMillis ();
	}

	protected final void endTiming ()
	{
		long now = System.currentTimeMillis ();
		long elapsed = now - timingStart;

		timingAccumulator = timingAccumulator - elapsed + timingInterval;
		if (timingAccumulator > 0)
		{
			try {
				Thread.sleep (timingAccumulator);
			} catch (InterruptedException ex) {}
			timingAccumulator = 0;
		}
	}
	
	protected final void resetTiming ()
	{
		timingStart = 0;
		timingAccumulator = 0;
	}
	
	public final boolean active ()
	{
		return ModeEnumerator.getMode () == controllerMode;
	}
	
	public void tick ()
	{
		/* OVERRIDE ME */
	}
	
	public void run ()
	{
		while (active ())
		{
			beginTiming ();
			tick ();
			endTiming ();
		}
	}
	
	public final void start ()
	{
		if (controllerStarted)
			throw new IllegalStateException ("controller already started");
		
		controllerRunning = true;
		controllerThread.start ();
		controllerStarted = true;
	}
	
	public final void stop ()
	{
		if (!controllerStarted)
			throw new IllegalStateException ("controller not started");
		
		synchronized (controllerLock)
		{
			controllerRunning = false;
			controllerLock.notifyAll ();
		}
		
		while (controllerThread.isAlive ())
			try {
				controllerThread.join ();
			} catch (InterruptedException e) {}
	}
	
	public Throwable getError ()
	{
		return controllerError;
	}
	
	private void _sleep (long millis)
	{
		synchronized (controllerLock)
		{
			if (!controllerRunning)
				throw new ExitException ();
			
			long end = System.currentTimeMillis () + millis;
			while (true)
			{
				long remaining = end - System.currentTimeMillis ();
				if (remaining <= 0)
					break;
				
				try {
					controllerLock.wait (remaining);
				} catch (InterruptedException e) {}
				
				if (!controllerRunning)
					throw new ExitException ();
			}
		}
	}
	
	public static void sleep (long millis)
	{
		if (currentController != null)
		{
			currentController._sleep (millis);
		}
		else
		{
			try {
				Thread.sleep (millis);
			} catch (InterruptedException e) {}
		}
	}
}
