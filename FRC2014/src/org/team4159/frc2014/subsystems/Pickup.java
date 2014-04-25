package org.team4159.frc2014.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import org.team4159.frc2014.IO;
import org.team4159.support.Subsystem;

public final class Pickup implements Subsystem
{
	public static final Pickup instance = new Pickup ();
	
        public static final double KP = .180;
        public static final double KI = .002;
        public static final double KD = .003;
        
        private static final double MAX_TOLERANCE = 1;
        private double pickupSpeed = Double.NaN;
        
        private boolean pickupArmStatus = false;
        
	private Pickup () 
        {
        }
        
        public void setMotorOutput(double x)
        {
            IO.pickupMotor.set(x);
        }
        
        public void raiseAngler(boolean temp){
            IO.pickupAngler.set(temp? DoubleSolenoid.Value.kReverse: DoubleSolenoid.Value.kForward);
            IO.pickupAnglerLeft.set(temp? DoubleSolenoid.Value.kForward: DoubleSolenoid.Value.kReverse);
        }
        
        public boolean getPickupArmStatus(){
            return pickupArmStatus;
        }
        public void setPickupArmStatus(boolean temp){
            pickupArmStatus = temp;
        }
}
