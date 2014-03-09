/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team4159.frc2014.subsystems;

import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.I2C;
import org.team4159.frc2014.IO;
/**
 *
 * @author Waylin
 */
public class ArduinoInterface {
    public static final ArduinoInterface instance = new ArduinoInterface();
    
    
    private ArduinoInterface(){
        
    }
    
    public void writePWM(int output){
        //IO.arduino.setRaw(output);
    }
    
}
