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

	private SendableChooser autonomousMode = new SendableChooser();
	
	private DashboardManager ()
	{
		autonomousMode.addObject ("Low Goal Blind Auto", new Integer (AutonomousController.LOW_GOAL_BLIND_AUTO));
		autonomousMode.addObject("Low Goal Detection Auto", new Integer(AutonomousController.LOW_GOAL_DETECTION_AUTO));
                autonomousMode.addObject ("Drive Only Auto", new Integer (AutonomousController.DRIVE_ONLY));
		autonomousMode.addObject ("No Drive Auto", new Integer (AutonomousController.STAY_PUT_AUTO));
		SmartDashboard.putData ("Autonomous Mode", autonomousMode);
	}
	
	public int getAutonomousMode ()
	{
		return ((Integer) autonomousMode.getSelected ()).intValue ();
	}
        public boolean shootReady(){
             double temp = SmartDashboard.getNumber("BLOB_COUNT", 0);
             return temp > 0;
        }
        public void update ()
        {
                SmartDashboard.putNumber ("Gyro Angle", IO.drivingGyro.getAngle());
                SmartDashboard.putBoolean("Pickup Arm Down?", Pickup.instance.getPickupArmStatus());
                
        }
}

