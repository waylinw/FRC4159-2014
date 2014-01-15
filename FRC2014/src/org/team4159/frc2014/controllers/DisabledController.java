package org.team4159.frc2014.controllers;


import org.team4159.frc2014.IO;
import org.team4159.frc2014.subsystems.DashboardManager;
import org.team4159.support.Controller;
import org.team4159.support.ModeEnumerator;

public class DisabledController extends Controller
{
	public DisabledController ()
	{
		super (ModeEnumerator.DISABLED);
	}
	
	public void tick ()
        {
                DashboardManager.instance.update ();
	}
}
