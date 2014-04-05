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
        boolean changeArmLightPressed = false;
        boolean chargePistonPressed = false;
        boolean shootPistonPressed = false;
        boolean pickupPressed = false;
        boolean endGame = false;
        long timer = 0;
        
	public OperatorController ()
	{
		super (ModeEnumerator.OPERATOR);
	}

        public void run(){
            timer = System.currentTimeMillis();
            super.run();
        }
	public void tick ()
	{
                Drive.instance.setSafetyEnabled(true);
		Drive.instance.tankDrive(-IO.driveStickLeft.getY(), -IO.driveStickRight.getY());
                if(fingerComputerControl){
                    if(!IO.limitSwitch.get()){
                        Drive.instance.setGearboxPosition (false);
                        System.out.println(IO.limitSwitch.get());
                        fingerComputerControl=false;
                    }
                }
                
//		boolean shiftDown = IO.driveStickLeft.getRawButton (2) || IO.driveStickRight.getRawButton(2);
//		boolean shiftUp = IO.driveStickLeft.getRawButton (3) || IO.driveStickRight.getRawButton(3);
//		if (shiftUp ^ shiftDown)
//			Drive.instance.setGearboxPosition (shiftUp);
                boolean openFinger = IO.driveStickLeft.getRawButton(4);
		boolean closeFinger = IO.driveStickLeft.getRawButton(5);
		if (openFinger ^ closeFinger){
			Drive.instance.setGearboxPosition (openFinger);
                        fingerComputerControl = openFinger;
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
                    if(!pickupPressed){
                        ArduinoInterface.instance.setLightState(ArduinoInterface.PICKUP_SPINNING);
                        pickupPressed = true;
                        shootPistonPressed = false;
                        chargePistonPressed = false;
                        changeArmLightPressed = false;
                    }
                    Pickup.instance.setMotorOutput(-.6);
                }
                else if(IO.driveStickRight.getTrigger()){
                    if(!pickupPressed){
                        ArduinoInterface.instance.setLightState(ArduinoInterface.PICKUP_SPINNING);
                        pickupPressed = true;
                        shootPistonPressed = false;
                        chargePistonPressed = false;
                        changeArmLightPressed = false;
                    }
                    Pickup.instance.setMotorOutput(.4);
                }
                else{
                    Pickup.instance.setMotorOutput(0);
                    IO.ballClamp.set(Relay.Value.kReverse);
                }
                
                if(IO.shooterStick.getRawButton(6)){
                    if(!chargePistonPressed){
                        ArduinoInterface.instance.setLightState(ArduinoInterface.CHARGE_PISTON);
                        pickupPressed = false;
                        shootPistonPressed = false;
                        chargePistonPressed = true;
                        changeArmLightPressed = false;
                    }
                    IO.shooterPiston.set(DoubleSolenoid.Value.kForward);//Extends piston
                    fingerComputerControl = true;
                }
                else if(IO.shooterStick.getRawButton(7)){
                    if(chargePistonPressed){
                        ArduinoInterface.instance.setLightState(ArduinoInterface.IDLE);
                        pickupPressed = false;
                        shootPistonPressed = true;
                        chargePistonPressed = false;
                        changeArmLightPressed = false;
                    }
                    IO.shooterPiston.set(DoubleSolenoid.Value.kReverse);//retracts piston
                }
                
                if(IO.shooterStick.getTrigger()){
                    if(!shootPistonPressed){
                        ArduinoInterface.instance.setLightState(ArduinoInterface.SHOOT);
                        pickupPressed = false;
                        shootPistonPressed = true;
                        chargePistonPressed = false;
                        changeArmLightPressed = false;
                    }
                    IO.shooterKicker.set(DoubleSolenoid.Value.kForward);//kicker up
                    fingerComputerControl = true;
                }
                else if(IO.shooterStick.getRawButton(9)){
                    if(shootPistonPressed){
                        ArduinoInterface.instance.setLightState(ArduinoInterface.IDLE);
                        pickupPressed = false;
                        shootPistonPressed = false;
                        chargePistonPressed = false;
                        changeArmLightPressed = false;
                    }
                    IO.shooterKicker.set(DoubleSolenoid.Value.kReverse);//kicker down
                }

                if(IO.shooterStick.getRawButton (3)){
                    if(!changeArmLightPressed){
                        ArduinoInterface.instance.setLightState(ArduinoInterface.RAISING_ARM);
                        pickupPressed = false;
                        shootPistonPressed = false;
                        chargePistonPressed = true;
                        changeArmLightPressed = true;
                    }
                    Shooter.instance.adjustShooterPitch(.5);
                }
                else if(IO.shooterStick.getRawButton (2)){
                    if(chargePistonPressed){
                        ArduinoInterface.instance.setLightState(ArduinoInterface.LOWERING_ARM);
                        pickupPressed = false;
                        shootPistonPressed = false;
                        chargePistonPressed = false;
                        changeArmLightPressed = false;
                    }
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
                if(!endGame){
                    if(System.currentTimeMillis()-timer>90000){
                        ArduinoInterface.instance.setLightState(9);
                        endGame = true;
                    }
                }
                
                
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
