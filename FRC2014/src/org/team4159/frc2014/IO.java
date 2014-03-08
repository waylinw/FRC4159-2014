/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team4159.frc2014;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.I2C;
import org.team4159.frc2014.subsystems.Pickup;
import org.team4159.support.CombinedMotor;

/**
 *
 * @author Waylin
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
	public static final Joystick driveStickLeft = new Joystick (1);
        public static final Joystick driveStickRight = new Joystick(2);
        public static final Joystick shooterStick = new Joystick (3);
	
	/****************************************
	 * SENSORS                              *
	 ****************************************/
	// 1 Digital I/O is in use by compressor
        
        public static final Encoder driveEncoderLeft = new Encoder(2,3);
        public static final Encoder driveEncoderRight = new Encoder(4,5);
        static{
                double pulsesPerRevolution = 360;
		double revolutionsPerPulse = 1 / pulsesPerRevolution;
                
		driveEncoderLeft.setDistancePerPulse (revolutionsPerPulse);
		driveEncoderRight.setDistancePerPulse (revolutionsPerPulse);
		driveEncoderLeft.setPIDSourceParameter (PIDSource.PIDSourceParameter.kRate);
		driveEncoderRight.setPIDSourceParameter (PIDSource.PIDSourceParameter.kRate);
		driveEncoderLeft.start ();
		driveEncoderRight.start ();
        }
        
        //Analog
	public static final Gyro drivingGyro =
		new Gyro (1);
        static{   
            drivingGyro.reset();
            drivingGyro.setSensitivity(.007);
        }
	/****************************************
	 * MOTORS                               *
	 ****************************************/
	public static final Talon driveMotorLeft = new Talon (1);
	public static final Talon driveMotorRight = new Talon (2);
        public static final Talon pickupMotor = new Talon (3);
        private static final Talon shooterRaisingLeft = new Talon(4);
        private static final Talon shooterRaisingRight = new Talon(5);
        public static final CombinedMotor shooterHeightMotors = new CombinedMotor(shooterRaisingLeft,shooterRaisingRight);
        /****************************************
	 * PID CONTROLLERS                      *
	 ****************************************/
	        
	/****************************************
	 * RELAYS                               *
	 ****************************************/
	public static final Compressor pneumaticPump = new Compressor (1, 1);
	static {
		pneumaticPump.start ();
	}
	
	/****************************************
	 * SOLENOIDS                            *
	 ****************************************/
	public static final DoubleSolenoid driveGearbox = new DoubleSolenoid (1, 2);
	static {
		driveGearbox.set (DoubleSolenoid.Value.kForward);
	}
        public static final DoubleSolenoid pickupAngler = new DoubleSolenoid (3,4);
        static {
                pickupAngler.set(DoubleSolenoid.Value.kReverse);
        }
        public static final DoubleSolenoid shooterKicker = new DoubleSolenoid (5,6);
        static{
            shooterKicker.set(DoubleSolenoid.Value.kReverse);
        }
        public static final DoubleSolenoid shooterPiston = new DoubleSolenoid (7,8);
        static{
            shooterPiston.set(DoubleSolenoid.Value.kForward);
        }
        public static final Relay ballClamp = new Relay(2);
        static{
            ballClamp.set(Relay.Value.kForward);
        }
        //public static final 
	// private constructor to prevent instantiation
	private IO () {}
	
	static {
		System.out.println ("IO ready.");
	}
}
