package org.team4159.frc2014.controllers;

import org.team4159.frc2014.Entry;
import org.team4159.frc2014.IO;
import org.team4159.frc2014.subsystems.Drive;
import org.team4159.support.Controller;
import org.team4159.support.DriverStationLCD;
import org.team4159.support.ModeEnumerator;
import org.team4159.support.filters.LowPassFilter;
import com.sun.squawk.util.Arrays;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.networktables2.type.NumberArray;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;
import org.team4159.frc2014.subsystems.Pickup;

//class ElevatorTest
//{
//	private double[] samples = new double[8];
//	private double[] samplessorted = new double[samples.length];
//	private int samplei = 0;
//	
//	private double maxRate = 0;
//	
//	public void tick ()
//	{
//		samples[samplei] = IO.elevatorEncoder.getRate ();
//		samplei = (samplei + 1) % samples.length;
//		
//		System.arraycopy (samples, 0, samplessorted, 0, samples.length);
//		Arrays.sort (samplessorted);
//		
//		double medianRate = samplessorted[samples.length / 2];
//		maxRate = IO.joystick1.getRawButton (2) ?
//			0 : Math.max (maxRate, Math.abs (medianRate));
//		
//		double elevatorOutput = IO.joystick1.getZ ();
//		IO.elevatorMotor.set (IO.joystick1.getTrigger () ? elevatorOutput : 0);
//		
//		DriverStationLCD.setLine (0, "Motor: " + elevatorOutput);
//		DriverStationLCD.setLine (1, "Encoder: " + maxRate);
//	}
//}

//class ShooterTest
//{
//    private double[] samplesraw = new double[21];
//    private double[] samplessorted = new double[samplesraw.length];
//    private int samplesindex = 0;
//    
//    public void tick ()
//    {
//        samplesraw[samplesindex++] = IO.shooterEncoder.getRate ();
//        samplesindex = (samplesindex + 1) % samplesraw.length;
//        
//        System.arraycopy (samplesraw, 0, samplessorted, 0, samplesraw.length);
//        Arrays.sort (samplessorted);
//        
//        DriverStationLCD.setLine (1, "ShtMed: " + samplessorted[samplesraw.length / 2]);
//    }
//}

public class OperatorController extends Controller 
{
    
	public OperatorController ()
	{
		super (ModeEnumerator.OPERATOR);
	}


	public void tick ()
	{
		Drive.instance.correctedDrive(-IO.driveStick.getX()*.7, -IO.driveStick.getY()*.7);
		
                DriverStationLCD.setLine(1, "Gyro Angle:"+IO.drivingGyro.getAngle());
                
		boolean shiftDown = IO.driveStick.getRawButton (2);
		boolean shiftUp = IO.driveStick.getRawButton (3);
		if (shiftUp ^ shiftDown)
			Drive.instance.setGearboxPosition (shiftUp);
                NetworkTable server = NetworkTable.getTable("SmartDashboard");
                try
                {
                    final NumberArray targetNum = new NumberArray();
                    server.retrieveValue("testla", targetNum);
                    if (targetNum.size()>0)
                    {
			System.out.print(targetNum.get(0));
			System.out.print(' ');
			System.out.println(targetNum.get(1));
                    }
                }
                catch (TableKeyNotDefinedException exp)
                {
                }
                
                if(IO.driveStick.getRawButton(4)){
                    Pickup.instance.raiseAngler(true);
                }
                else if(IO.driveStick.getRawButton(5)){
                    Pickup.instance.raiseAngler(false);
                }
		
	}
}
