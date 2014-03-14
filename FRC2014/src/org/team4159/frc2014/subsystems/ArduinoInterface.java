/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team4159.frc2014.subsystems;

import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.PWM;
import org.team4159.frc2014.IO;
import org.team4159.support.Controller;
/**
 *
 * @author Waylin
 */
public class ArduinoInterface {
    public static final ArduinoInterface instance = new ArduinoInterface();

    
    private ArduinoInterface(){
        
    }
    
    public void setLightState(int lightState){
        switch(lightState){
            case 1:
                writePWM(21);
                break;
            case 2:
                writePWM(41);
                break;
            case 3:
                writePWM(61);
                break;
            case 4:
                writePWM(81);
                break;
            case 5:
                writePWM(101);
                break;
            case 6:
                writePWM(121);
                break;
            case 7:
                writePWM(141);
                break;
            case 8:
                writePWM(161);
                break;
            case 9:
                writePWM(201);
                break;
        }
    }
    
    private void writePWM(int output){
        PWM arduino = new PWM(7);
        arduino.setPeriodMultiplier(PWM.PeriodMultiplier.k4X);
        long time = System.currentTimeMillis();
        while(System.currentTimeMillis() - time < 200)
            arduino.setRaw(output);
        arduino.free();
    }

    public static final int IDLE = 1;
    public static final int AUTONOMOUS = 2;
    public static final int CHARGE_PISTON = 3;
    public static final int SHOOT = 4;
    public static final int RAISING_ARM = 5;
    public static final int LOWERING_ARM = 6;
    public static final int PICKUP_SPINNING = 7;
    public static final int WARNING = 8;
    public static final int ENG_GAME_ANIMATION = 9;
    /*
        LIGHT STATE:
    1:  IDLE
    2:  AUTONOMOUS
    3:  CHARGE PISTON
    4:  SHOOT
    5:  RAISING ARM
    6:  LOWERING ARM
    7:  PICKUP SPINNING
    8:  WARNING
    */
    
}
