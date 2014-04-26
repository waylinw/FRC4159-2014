package org.team4159.frc2014.controllers;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;
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
    	public static final int LOW_GOAL_BLIND_AUTO = 1;
	public static final int DRIVE_ONLY = 2;
	public static final int STAY_PUT_AUTO = 3;
        public static final int LOW_GOAL_DETECTION_AUTO = 4;
        private byte []toSend = new byte[1];
        
	public AutonomousController ()
	{
		super (ModeEnumerator.AUTONOMOUS);
	}
        public void run(){
                Pickup.instance.setMotorOutput(0);
                Drive.instance.stopMotor();
                
            	switch (DashboardManager.instance.getAutonomousMode ())
		{
			case LOW_GOAL_BLIND_AUTO:
                                lowGoalBlindAuto();
				break;
                        case LOW_GOAL_DETECTION_AUTO:
                                lowGoalDetectionAuto();
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
        private void lowGoalBlindAuto(){
            Pickup.instance.raiseAngler(false);
            Drive.instance.setGearboxPosition (true);
            Drive.instance.setSafetyEnabled(false);
            int i = 1;
            while(i<2){
                Drive.instance.tankDrive(.62,.6);
                Shooter.instance.fire();
                Controller.sleep(4000);
                Drive.instance.setSafetyEnabled(true);
                Drive.instance.stopMotor();
                Pickup.instance.setMotorOutput(-1);
                Controller.sleep(3000);
                Pickup.instance.setMotorOutput(0);
                i++;
            }
        }
        private void lowGoalDetectionAuto(){
            Pickup.instance.raiseAngler(false);
            Drive.instance.setGearboxPosition (true);
            Drive.instance.setSafetyEnabled(false);
            int i = 1;
            while(i<2){
                Drive.instance.tankDrive(.62,.6);
                Shooter.instance.fire();
                Controller.sleep(4000);
                Drive.instance.setSafetyEnabled(true);
                Drive.instance.stopMotor();
                i++;
            }
            while (true){
                if(DashboardManager.instance.shootReady()){
                    Pickup.instance.setMotorOutput(-1);
                    Controller.sleep(3000);
                    Pickup.instance.setMotorOutput(0);
                    break;
                }
            }
        }
        
        private void driveOnly(){
            Pickup.instance.raiseAngler(false);
            Drive.instance.setGearboxPosition (false);
            Drive.instance.setSafetyEnabled(false);
            int i = 1;
            while(i<2){
                Drive.instance.tankDrive(.62, .6);
                Shooter.instance.fire();
                Controller.sleep(3000);
                Drive.instance.setSafetyEnabled(true);
                Drive.instance.stopMotor();
                i++;
            }
        }
        
        private void stayPut(){
            return;
        }
	
}
