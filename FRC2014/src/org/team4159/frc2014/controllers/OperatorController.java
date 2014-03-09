package org.team4159.frc2014.controllers;

import org.team4159.frc2014.Entry;
import org.team4159.frc2014.IO;
import org.team4159.frc2014.subsystems.Drive;
import org.team4159.support.Controller;
import org.team4159.support.DriverStationLCD;
import org.team4159.support.ModeEnumerator;
import org.team4159.support.filters.LowPassFilter;
import com.sun.squawk.util.Arrays;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.networktables2.type.NumberArray;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;
import org.team4159.frc2014.subsystems.ArduinoInterface;
import org.team4159.frc2014.subsystems.Pickup;
import org.team4159.frc2014.subsystems.Shooter;

public class OperatorController extends Controller 
{
        private byte []toSend = new byte[1]; 
        public boolean fingerComputerControl = true;
        
	public OperatorController ()
	{
		super (ModeEnumerator.OPERATOR);
	}


	public void tick ()
	{
                Drive.instance.setSafetyEnabled(true);
		Drive.instance.tankDrive(-IO.driveStickLeft.getY(), -IO.driveStickRight.getY());
                if(fingerComputerControl){
                    if(!IO.limitSwitch.get()){
                        Drive.instance.setGearboxPosition(false);
                        fingerComputerControl=false;
                    }
                }
                
                
//		boolean shiftDown = IO.driveStickLeft.getRawButton (2) || IO.driveStickRight.getRawButton(2);
//		boolean shiftUp = IO.driveStickLeft.getRawButton (3) || IO.driveStickRight.getRawButton(3);
//		if (shiftUp ^ shiftDown)
//			Drive.instance.setGearboxPosition (shiftUp);
                boolean openFinger = IO.shooterStick.getRawButton(4);
		boolean closeFinger = IO.shooterStick.getRawButton(5);
		if (openFinger ^ closeFinger){
			Drive.instance.setGearboxPosition (openFinger);
                        fingerComputerControl = false;
                }

                
                if(IO.driveStickRight.getRawButton(4)){
                    Pickup.instance.raiseAngler(true);
                    Pickup.instance.setPickupArmStatus(false);
                }
                else if(IO.driveStickRight.getRawButton(5)){
                    Pickup.instance.raiseAngler(false);
                    Pickup.instance.setPickupArmStatus(true);
                }
                
                if(IO.driveStickLeft.getRawButton(3)){
                    Pickup.instance.setMotorOutput(.6);
                }
                else if(IO.driveStickRight.getRawButton(1)){
                    Pickup.instance.setMotorOutput(-.8);
                }
                else{
                    Pickup.instance.setMotorOutput(0);
                    IO.ballClamp.set(Relay.Value.kReverse);
                }
                
                if(IO.shooterStick.getRawButton(6)){
                    IO.shooterPiston.set(DoubleSolenoid.Value.kForward);//Extends piston
                    fingerComputerControl = true;
                }
                else if(IO.shooterStick.getRawButton(7)){
                    IO.shooterPiston.set(DoubleSolenoid.Value.kReverse);//retracts piston
                }
                
                if(IO.shooterStick.getRawButton(8)){
                    IO.shooterKicker.set(DoubleSolenoid.Value.kForward);//kicker up
                    fingerComputerControl = true;
                }
                else if(IO.shooterStick.getRawButton(9)){
                    IO.shooterKicker.set(DoubleSolenoid.Value.kReverse);//kicker down
                }

                if(IO.shooterStick.getRawButton (3)){
                    Shooter.instance.adjustShooterPitch(.5);
                }
                else if(IO.shooterStick.getRawButton (2)){
                    Shooter.instance.adjustShooterPitch(-.3);
                }
                else{
                    Shooter.instance.adjustShooterPitch(0);
                }
                
//                if(IO.shooterStick.getRawButton (4)){
//                    Shooter.instance.adjustShooterYaw(.5);
//                }
//                else if(IO.shooterStick.getRawButton (5)){
//                    Shooter.instance.adjustShooterYaw(-.8);
//                }
//                else{
//                    Shooter.instance.adjustShooterYaw(0);
//                }
                
//                if(IO.shooterStick.getTrigger()){
//                    Shooter.instance.fire();
//                }
//                else if(IO.shooterStick.getRawButton(7)){
//                    Shooter.instance.hardReset();
//                }
                
                
                NetworkTable server = NetworkTable.getTable("ImageRecognitionValues");
                try
                {
                    System.out.println(server.getNumber("IMAGE_COUNT"));
                }
                catch (TableKeyNotDefinedException exp)
                {
                }
                
		
	}
}
