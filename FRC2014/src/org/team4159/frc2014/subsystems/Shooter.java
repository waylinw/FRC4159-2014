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
        if(IO.shooterPiston.get()==DoubleSolenoid.Value.kForward){
            return true;
        }
        else{
            IO.shooterPiston.set(DoubleSolenoid.Value.kForward);
        }
        return true;
    }
    
    public void getReadyToFire(){
        IO.shooterKicker.set(DoubleSolenoid.Value.kReverse);
        Controller.sleep(200);
        IO.shooterPiston.set(DoubleSolenoid.Value.kForward);
        toSend[0]='c';
        ArduinoInterface.instance.lightMode(toSend);
    }
    
    public void fire(){
        IO.ballClamp.set(Relay.Value.kForward);
        IO.shooterKicker.set(DoubleSolenoid.Value.kForward);
        toSend[0]='s';
        ArduinoInterface.instance.lightMode(toSend);
        reset();
    }
    
    public void reset(){
        toSend[0]='c';
        ArduinoInterface.instance.lightMode(toSend);
        IO.shooterPiston.set(DoubleSolenoid.Value.kReverse);
        Controller.sleep(2000);
        IO.shooterKicker.set(DoubleSolenoid.Value.kReverse);
        Controller.sleep(700);
        IO.shooterPiston.set(DoubleSolenoid.Value.kForward);
        Controller.sleep(10000);
        toSend[0]='i';
        ArduinoInterface.instance.lightMode(toSend);
    }
    
    public void hardReset(){
        toSend[0]='w';
        ArduinoInterface.instance.lightMode(toSend);
        IO.shooterPiston.set(DoubleSolenoid.Value.kForward);
        IO.shooterKicker.set(DoubleSolenoid.Value.kForward);
        IO.shooterPiston.set(DoubleSolenoid.Value.kReverse);
        Controller.sleep(2000);
        IO.shooterKicker.set(DoubleSolenoid.Value.kReverse);
        Controller.sleep(400);
        reset();
    }
    
    public void adjustShooterPitch (double pwr){
        IO.shooterHeightMotors.set(pwr);
    }
    
    public void adjustShooterYaw(double pwr){
        IO.shooterChangeYAngle.set(pwr);
    }
    
}
