/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team4159.frc2014.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import org.team4159.frc2014.IO;
import org.team4159.support.Subsystem;

/**
 *
 * @author Waylin
 */
public class Shooter implements Subsystem {
    public static final Shooter instance = new Shooter();
    private boolean shooterCharged = false;
    
    private Shooter()
    {
        
    }
    public boolean chargeUpForShooting(){
        if(IO.shooterPiston.get()==DoubleSolenoid.Value.kForward){
            return true;
        }
        else{
            IO.shooterPiston.set(DoubleSolenoid.Value.kForward);
        }
        return true;
    }
    public void adjustShooterPitch (double pwr){
        IO.shooterHeightMotors.set(pwr);
    }
    
    public void adjustShooterAimingAngle(double pwr){
        
    }
    
}
