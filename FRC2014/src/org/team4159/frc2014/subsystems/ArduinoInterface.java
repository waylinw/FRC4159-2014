/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team4159.frc2014.subsystems;

import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.I2C;
/**
 *
 * @author Waylin
 */
public class ArduinoInterface {
    public static final ArduinoInterface instance = new ArduinoInterface();
    DigitalModule module = DigitalModule.getInstance(2);
    public  I2C arduino = module.getI2C(168);
    
    private ArduinoInterface(){
        
    }
    public void lightMode(byte[] array){
        sendData(array);
    }
    private void sendData(byte[] array){
        arduino.transaction(array, 1, null, 0);
    }
}
