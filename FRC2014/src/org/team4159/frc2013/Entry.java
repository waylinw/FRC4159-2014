package org.team4159.frc2013;

import org.team4159.support.Controller;
import edu.wpi.first.wpilibj.RobotBase;
import org.team4159.frc2013.controllers.AutonomousController;
import org.team4159.frc2013.controllers.DisabledController;
import org.team4159.frc2013.controllers.OperatorController;
import org.team4159.frc2013.controllers.ResetController;
import org.team4159.frc2013.controllers.TestController;
import org.team4159.frc2013.subsystems.DashboardManager;
import org.team4159.support.ModeEnumerator;

public class Entry extends RobotBase
{
	public static final int TICK_INTERVAL_MS = 20;
	public static final int RETRY_INTERVAL_MS = 500;
	public static final int RETRY_COUNT = 10;
	
	
	public Entry ()
	{
		System.out.println ("Entry initializing ...");
		
		// wake up the IO class
		IO.class.equals (null);
		
		// initialize dashboard manager
		DashboardManager.class.equals (null);
		
		System.out.println ("Entry instantiated.");
	}
	
	private Controller createController (int ct)
	{
		switch (ct)
		{
			case ModeEnumerator.DISABLED:
				return new DisabledController ();
			case ModeEnumerator.AUTONOMOUS:
				return new AutonomousController ();
			case ModeEnumerator.OPERATOR:
				return new OperatorController ();
			case ModeEnumerator.TEST:
				return new TestController ();
			default:
				throw new IllegalArgumentException ("unknown controller state");
		}
	}
	
	public void startCompetition ()
	{
		System.out.println ("Entry.startCompetition() called.");
		
		while (true)
		{
			try {
				new ResetController ().run ();
			} catch (Throwable t) {
				System.err.println ("ResetController threw an exception!");
				t.printStackTrace ();
			}
			
			int mode = ModeEnumerator.getMode();
			
			int retries = 0;
			boolean givenUp = false;
			
			modeLoop:
			while (true)
			{
				Controller controller = createController (mode);
				String controllerName = controller.getClass ().getName ();
				
				if (!givenUp)
				{
					System.out.println ("Starting controller " + controllerName);
					controller.start ();
				}
				
				while (controller.active ())
				{
					sleep (TICK_INTERVAL_MS);
					
					if (controller.getError () != null && !givenUp)
					{
						if (retries++ < RETRY_COUNT)
						{
							System.out.println ("Controller " + controllerName + " crashed, attempting restart ...");
							sleep (RETRY_INTERVAL_MS);
							continue modeLoop;
						}
						else
						{
							System.out.println ("Too many retries to restart " + controllerName +", giving up!");
							givenUp = true;
						}
					}
				}
				
				if (!givenUp)
				{
					System.out.println ("Stopping controller " + controllerName);
					controller.stop ();
				}
				
				break modeLoop;
			}
		}
	}
	
	private void sleep (long x)
	{
		try {
			Thread.sleep (x);
		} catch (InterruptedException e) {}
	}
}