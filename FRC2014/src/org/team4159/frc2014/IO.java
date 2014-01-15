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
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import org.team4159.frc2014.subsystems.Drive;
import org.team4159.support.SlidingAverageEncoder;

/**
 *
 * @author gavin
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
	public static final Joystick joystick1 = new Joystick (1);
	public static final Joystick joystick2 = new Joystick (2);
        public static final Joystick joystick3 = new Joystick (3);

	private IO () {}
	
	static {
		System.out.println ("IO ready.");
	}
}
