/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team4159.frc2014.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team4159.frc2014.IO;
import org.team4159.frc2014.controllers.AutonomousController;

/**
 *
 * @author Waylin
 */
public class DashboardManager
{
	public static final DashboardManager instance = new DashboardManager ();

	private SendableChooser autonomousMode;
	
	private DashboardManager ()
	{
		SendableChooser autonomousMode = new SendableChooser ();
		autonomousMode.addObject ("Left", new Integer (AutonomousController.MODE_LEFT));
		autonomousMode.addObject ("Right", new Integer (AutonomousController.MODE_RIGHT));
		autonomousMode.addObject ("Static", new Integer (AutonomousController.MODE_STATIC));
		SmartDashboard.putData ("Autonomous Mode", autonomousMode);
	}
	
	public int getAutonomousMode ()
	{
		return ((Integer) autonomousMode.getSelected ()).intValue ();
	}
        public void update ()
        {
            SmartDashboard.putNumber ("Shooter Power", IO.drivingGyro.getAngle());
        }
}

