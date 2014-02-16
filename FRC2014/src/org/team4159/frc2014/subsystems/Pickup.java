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
        
	private Pickup () 
        {
            IO.pickupPID.setAbsoluteTolerance(MAX_TOLERANCE);
            IO.pickupPID.setOutputRange(-.2, 1.0);
        }
        
        public void setSpeed(double x, boolean withPID){
            if(withPID){
                if(pickupSpeed != x){
                IO.pickupPID.setSetpoint(pickupSpeed = x);
                IO.pickupPID.reset();
                }
                IO.pickupPID.enable();
            }
            else if(!withPID){
                setMotorOutput(x);
            }
        }
        
        public void setMotorOutput(double x)
        {
            IO.pickupPID.disable();
            IO.pickupMotor.set(x);
        }
        public boolean pickupIsReady ()
	{
		return IO.pickupPID.onTarget ();
	}
        
        public void raiseAngler(boolean temp){
            IO.pickupAngler.set(temp? DoubleSolenoid.Value.kForward: DoubleSolenoid.Value.kReverse);
        }
        
        
}
