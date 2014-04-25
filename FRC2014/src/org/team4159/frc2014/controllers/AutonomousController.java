package org.team4159.frc2014.controllers;

import edu.wpi.first.wpilibj.PWM;
import org.team4159.frc2014.IO;
import org.team4159.frc2014.subsystems.ArduinoInterface;
import org.team4159.frc2014.subsystems.DashboardManager;
import org.team4159.frc2014.subsystems.Drive;
import org.team4159.frc2014.subsystems.Pickup;
import org.team4159.frc2014.subsystems.Shooter;
import org.team4159.support.Controller;
import org.team4159.support.ModeEnumerator;

public class AutonomousController extends Controller 
{
    	public static final int LOW_GOAL_AUTO = 1;
	public static final int DRIVE_ONLY = 2;
	public static final int STAY_PUT_AUTO = 3;
        private byte []toSend = new byte[1];
        
	public AutonomousController ()
	{
		super (ModeEnumerator.AUTONOMOUS);
	}
        public void run(){
            	switch (DashboardManager.instance.getAutonomousMode ())
		{
			case LOW_GOAL_AUTO:
                                lowGoalAuto();
				break;
			case DRIVE_ONLY:
                                driveOnly();
				break;
			case STAY_PUT_AUTO:
                                stayPut();
				break;
			default:
				System.out.println ("Invalid autonomous mode!");
                                stayPut();
				break;
		}
        }
        private void lowGoalAuto(){
            Drive.instance.setGearboxPosition (true);
            Drive.instance.setSafetyEnabled(false);
            
            int i = 1;
            while(i<2){
                Drive.instance.tankDrive(.6, .4);
                Shooter.instance.fire();
                Controller.sleep(7000);
                Drive.instance.setSafetyEnabled(true);
                Drive.instance.stopMotor();
                Pickup.instance.setMotorOutput(-.8);
                i++;
            }
        }
        
        private void driveOnly(){
            //Lowers pickup
            //Pickup.instance.raiseAngler(true);
            Drive.instance.setGearboxPosition (false);
            Drive.instance.setSafetyEnabled(false);
            int i = 1;
            while(i<2){
                Drive.instance.tankDrive(.6, .4);
                Shooter.instance.fire();
                Controller.sleep(5000);
                Drive.instance.setSafetyEnabled(true);
                Drive.instance.stopMotor();
                i++;
            }
        }
        
        private void stayPut(){
            return;
        }
	
}
