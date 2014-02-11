package org.team4159.frc2014.controllers;

import org.team4159.frc2014.IO;
import org.team4159.frc2014.subsystems.Drive;
import org.team4159.support.Controller;
import org.team4159.support.DriverStationLCD;
import org.team4159.support.ModeEnumerator;
import com.sun.squawk.util.MathUtils;
import org.team4159.frc2014.subsystems.DashboardManager;

public class OperatorController extends Controller 
{
    
    
	public OperatorController ()
	{
		super (ModeEnumerator.OPERATOR);
	}
	
	public void tick ()
	{
		Drive.instance.correctedDrive(IO.driveStick.getY(), IO.driveStick.getX());
                boolean shiftDown = IO.driveStick.getRawButton (2);
		boolean shiftUp = IO.driveStick.getRawButton (3);
		if (shiftUp ^ shiftDown) Drive.instance.setGearboxPosition (shiftUp);
                System.out.println("gyro reading: "+IO.drivingGyro.getCorrectedAngle());
                
                DashboardManager.instance.update ();
	}
}
