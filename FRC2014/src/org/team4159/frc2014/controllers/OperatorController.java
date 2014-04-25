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
		Drive.instance.tankDrive(-IO.driveStickLeft.getY(), -IO.driveStickRight.getY(),true);
                
		boolean shiftDown = IO.driveStickLeft.getRawButton (2) || IO.driveStickRight.getRawButton(2);
		boolean shiftUp = IO.driveStickLeft.getRawButton (3) || IO.driveStickRight.getRawButton(3);
		if (shiftUp ^ shiftDown)
			Drive.instance.setGearboxPosition (shiftUp);

                
                if(IO.shooterStick.getRawButton(4)){
                    Pickup.instance.raiseAngler(true);
                }
                else if(IO.shooterStick.getRawButton(5)){
                    Pickup.instance.raiseAngler(false);
                }
                
                if(IO.shooterStick.getRawButton(3)){
                    IO.pickupMotor.set(-.8);
                }
                else if(IO.shooterStick.getTrigger()){
                    IO.pickupMotor.set(1);
                }
                else{
                    IO.pickupMotor.set(0);
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
