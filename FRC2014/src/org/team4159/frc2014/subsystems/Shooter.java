/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team4159.frc2014.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Relay;
import org.team4159.frc2014.IO;
import org.team4159.support.Controller;
import org.team4159.support.Subsystem;

/**
 *
 * @author Waylin
 */
public class Shooter implements Subsystem {
    public static final Shooter instance = new Shooter();
    private boolean shooterCharged = false;
    private byte []toSend = new byte[1];
    
    private Shooter()
    {
        
    }
    public boolean chargeUpForShooting(){
        return true;
    }
    
    public void getReadyToFire(){
    }
    
    public void fire(){
    }
    
    public void reset(){
    }
    
    public void hardReset(){
    }
    
        public void adjustShooterPitch (double pwr){
    }
    
    public void adjustShooterYaw(double pwr){
    }
    
}
