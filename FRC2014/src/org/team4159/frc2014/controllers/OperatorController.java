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
	public OperatorController ()
	{
		super (ModeEnumerator.OPERATOR);
	}


	public void tick ()
	{
		Drive.instance.tankDrive(IO.driveStickLeft, IO.driveStickRight);
		
                DriverStationLCD.setLine(1, "Gyro Angle:"+IO.drivingGyro.getAngle());
                
		boolean shiftDown = IO.driveStickLeft.getRawButton (2) || IO.driveStickRight.getRawButton(2);
		boolean shiftUp = IO.driveStickLeft.getRawButton (3) || IO.driveStickRight.getRawButton(3);
		if (shiftUp ^ shiftDown)
			Drive.instance.setGearboxPosition (shiftUp);

                
                if(IO.driveStickLeft.getRawButton(4) || IO.driveStickRight.getRawButton(4)){
                    Pickup.instance.raiseAngler(true);
                    Pickup.instance.setPickupArmStatus(false);
                }
                else if(IO.driveStickLeft.getRawButton(5) || IO.driveStickRight.getRawButton(5)){
                    Pickup.instance.raiseAngler(false);
                    Pickup.instance.setPickupArmStatus(true);
                }
                
                if(IO.driveStickLeft.getRawButton(11)||IO.driveStickRight.getRawButton(11)){
                    Pickup.instance.setMotorOutput(.6);
                    IO.ballClamp.set(Relay.Value.kForward);
                }
                else if(IO.driveStickLeft.getTrigger()||IO.driveStickRight.getTrigger()){
                    Pickup.instance.setMotorOutput(-.8);
                    IO.ballClamp.set(Relay.Value.kForward);
                }
                else{
                    Pickup.instance.setMotorOutput(0);
                    IO.ballClamp.set(Relay.Value.kReverse);
                }
                
//                if(IO.shooterStick.getRawButton(6)){
//                    IO.shooterPiston.set(DoubleSolenoid.Value.kForward);//Extends piston
//                }
//                else if(IO.shooterStick.getRawButton(7)){
//                    IO.shooterPiston.set(DoubleSolenoid.Value.kReverse);//retracts piston
//                }
//                
//                if(IO.shooterStick.getRawButton(8)){
//                    IO.shooterKicker.set(DoubleSolenoid.Value.kForward);//kicker up
//                }
//                else if(IO.shooterStick.getRawButton(9)){
//                    IO.shooterKicker.set(DoubleSolenoid.Value.kReverse);//kicker down
//                }
                
                boolean comingUp = IO.shooterStick.getRawButton (3);
                boolean comingDown = IO.shooterStick.getRawButton (2);
                if(comingUp){
                    toSend[0]='u';
                    ArduinoInterface.instance.lightMode(toSend);
                    Shooter.instance.adjustShooterPitch(.5);
                }
                else if(comingDown){
                    toSend[0]='d';
                    ArduinoInterface.instance.lightMode(toSend);
                    Shooter.instance.adjustShooterPitch(-.3);
                }
                else{
                    toSend[0]='i';
                    Shooter.instance.adjustShooterPitch(0);
                }
                
                if(IO.shooterStick.getTrigger()){
                    Shooter.instance.fire();
                }
                else if(IO.shooterStick.getRawButton(5)){
                    Shooter.instance.hardReset();
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
