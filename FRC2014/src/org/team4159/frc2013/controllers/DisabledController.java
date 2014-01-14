package org.team4159.frc2013.controllers;


import org.team4159.frc2013.IO;
import org.team4159.frc2013.subsystems.DashboardManager;
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
