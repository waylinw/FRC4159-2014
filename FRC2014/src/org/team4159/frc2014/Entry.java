package org.team4159.frc2014;

import org.team4159.support.Controller;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import org.team4159.frc2014.controllers.AutonomousController;
import org.team4159.frc2014.controllers.DisabledController;
import org.team4159.frc2014.controllers.OperatorController;
import org.team4159.frc2014.controllers.TestController;
import org.team4159.support.ModeEnumerator;

public class Entry extends RobotBase
{
	public static final int TICK_INTERVAL_MS = 20;
	
	public Entry ()
	{
		System.out.println ("Entry instantiated.");
		
		// wake up the IO class
		IO.class.getName ();
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
                
                //new ImageSampler ().start ();
		
		while (true)
		{
			int mode = ModeEnumerator.getMode();
			Controller controller = createController (mode);
			
			System.out.println ("Controller set to " + controller.getClass ().getName ());
			
			controller.start ();
			while (ModeEnumerator.getMode () == mode)
				try {
					Thread.sleep (TICK_INTERVAL_MS);
				} catch (InterruptedException e) {}
			controller.stop ();
		}
	}
}