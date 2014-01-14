package org.team4159.frc2013.controllers;

import org.team4159.frc2013.IO;
import org.team4159.frc2013.subsystems.Drive;
import org.team4159.support.Controller;
import org.team4159.support.DriverStationLCD;
import org.team4159.support.ModeEnumerator;
import com.sun.squawk.util.MathUtils;
import org.team4159.frc2013.subsystems.DashboardManager;

public class OperatorController extends Controller 
{
    private boolean finePressed = false;
    private boolean fineShooter = false;
    private double fineShooterBase = 0.0;
    private double fineShooterLevel = 0.0;
    
	public OperatorController ()
	{
		super (ModeEnumerator.OPERATOR);
	}
	
	public void tick ()
	{
		
                DashboardManager.instance.update ();
	}
}
