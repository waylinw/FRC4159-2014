/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team4159.frc2014;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import org.team4159.frc2014.subsystems.Drive;
import org.team4159.support.SlidingAverageEncoder;
import org.team4159.support.CorrectedGyro;

/**
 *
 * @author waylin
 */
public class IO
{
	// To avoid changes, make sure all declarations are "public static final".
	static {
		System.out.println ("Initializing IO ...");
	}
        
        public final AxisCamera camera = AxisCamera.getInstance ("10.41.59.11");
	
	/****************************************
	 * JOYSTICKS                            *
	 ****************************************/
	public static final Joystick driveStick = new Joystick (1);
	//public static final Joystick joystick2 = new Joystick (2);
        //public static final Joystick joystick3 = new Joystick (3);

        /****************************************
	 * MOTORS                               *
	 ****************************************/
        public static final Talon driveMotorLeft = new Talon(1);
//        public static final Talon driveMotorRight = new Talon(2);
//        public static final Talon pickupSpinner = new Talon(3);
//        public static final Talon shooterYAdjust = new Talon(4);
        
        /****************************************
	 * RELAYS                               *
	 ****************************************/
//	public static final Compressor pneumaticPump = new Compressor (1, 1);
//	static {
//		pneumaticPump.start ();
//	}
        
        /****************************************
	 * SOLENOIDS                            *
	 ****************************************/
	public static final DoubleSolenoid driveGearbox = new DoubleSolenoid (1, 2);
	static {
		driveGearbox.set (Value.kForward);
	}
         /****************************************
	 * SENSORS                              *
	 ****************************************/
        //public static final HiTechnicColorSensor railSensor = new HiTechnicColorSensor(1);
        public static final CorrectedGyro drivingGyro = new CorrectedGyro(1);
        
        
	private IO () {}
	
	static {
		System.out.println ("IO ready.");
	}
    }
