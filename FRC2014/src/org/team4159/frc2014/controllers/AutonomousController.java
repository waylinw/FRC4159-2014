package org.team4159.frc2014.controllers;

import org.team4159.frc2014.IO;
import org.team4159.frc2014.subsystems.ArduinoInterface;
import org.team4159.frc2014.subsystems.DashboardManager;
import org.team4159.frc2014.subsystems.Drive;
import org.team4159.frc2014.subsystems.Pickup;
import org.team4159.frc2014.subsystems.Shooter;
import org.team4159.support.Controller;
import org.team4159.support.DriverStationLCD;
import org.team4159.support.ModeEnumerator;

public class AutonomousController extends Controller 
{
    	public static final int MODE_LEFT = 1;
	public static final int MODE_RIGHT = 2;
	public static final int MODE_STATIC = 3;
        private byte []toSend = new byte[1];
        
	public AutonomousController ()
	{
		super (ModeEnumerator.AUTONOMOUS);
	}
        
        public void run(){
            toSend[0]='a';
            ArduinoInterface.instance.lightMode(toSend);
            
            //Lowers pickup
            Pickup.instance.raiseAngler(false);
            Controller.sleep(500);
            
            Drive.instance.drive(.3, 0);
            Shooter.instance.fire();
            
            Controller.sleep(3000);
            Drive.instance.drive(0, 0);
            
            
            	switch (DashboardManager.instance.getAutonomousMode ())
		{
			case MODE_LEFT:
				break;
			case MODE_RIGHT:
				break;
			case MODE_STATIC:
				break;
			default:
				System.out.println ("Invalid autonomous mode!");
				break;
		}
        }
	
}
