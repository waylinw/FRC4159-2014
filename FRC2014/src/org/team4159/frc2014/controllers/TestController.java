package org.team4159.frc2014.controllers;

import org.team4159.frc2014.IO;
import org.team4159.frc2014.subsystems.Drive;
import org.team4159.support.Controller;
import org.team4159.support.ModeEnumerator;

/**
 *
 * @author Waylin
 */

public class TestController extends Controller 
{
	public TestController ()
	{
		super (ModeEnumerator.TEST);
	}
        public void tick(){
            if(IO.driveStick.getRawButton(3))
                Drive.instance.correctedDrive(.5, 0);
            else if(IO.driveStick.getRawButton(2))
                Drive.instance.correctedDrive(-.5, 0);
            else
                Drive.instance.correctedDrive(0, 0);
        }
}
