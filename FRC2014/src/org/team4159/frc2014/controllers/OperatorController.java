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
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.networktables2.type.NumberArray;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;
import org.team4159.frc2014.subsystems.Pickup;
import org.team4159.frc2014.subsystems.Shooter;

public class OperatorController extends Controller 
{
    
	public OperatorController ()
	{
		super (ModeEnumerator.OPERATOR);
	}


	public void tick ()
	{
		Drive.instance.correctedDrive(-IO.driveStick.getX(), -IO.driveStick.getY());
		
                DriverStationLCD.setLine(1, "Gyro Angle:"+IO.drivingGyro.getAngle());
                
		boolean shiftDown = IO.driveStick.getRawButton (2);
		boolean shiftUp = IO.driveStick.getRawButton (3);
		if (shiftUp ^ shiftDown)
			Drive.instance.setGearboxPosition (shiftUp);
                boolean fasterPressed = IO.shooterStick.getRawButton (3);
                boolean slowerPressed = IO.shooterStick.getRawButton (2);
                if(fasterPressed){
                    Shooter.instance.adjustShooterPitch(.3);
                }
                else if(slowerPressed){
                    Shooter.instance.adjustShooterPitch(-.3);
                }
                else{
                    Shooter.instance.adjustShooterPitch(0);
                }
                
                if(IO.driveStick.getRawButton(4)){
                    Pickup.instance.raiseAngler(true);
                    Pickup.instance.setPickupArmStatus(false);
                }
                else if(IO.driveStick.getRawButton(5)){
                    Pickup.instance.raiseAngler(false);
                    Pickup.instance.setPickupArmStatus(true);
                }
                
                if(IO.driveStick.getRawButton(11)){
                    Pickup.instance.setSpeed(.6, false);
                }
                else if(IO.driveStick.getTrigger()){
                    Pickup.instance.setSpeed(-.6, false);
                }
                else{
                    Pickup.instance.setSpeed(0, false);
                }
                
                if(IO.driveStick.getRawButton(6)){
                    IO.shooterPiston.set(DoubleSolenoid.Value.kForward);
                }
                else if(IO.driveStick.getRawButton(7)){
                    IO.shooterPiston.set(DoubleSolenoid.Value.kReverse);
                }
                else if(IO.driveStick.getRawButton(8)){
                    IO.shooterKicker.set(DoubleSolenoid.Value.kForward);
                }
                else if(IO.driveStick.getRawButton(9)){
                    IO.shooterKicker.set(DoubleSolenoid.Value.kReverse);
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
