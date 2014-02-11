/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.team4159.support;
import edu.wpi.first.wpilibj.Gyro;
/**
 *
 * @author WaylinWang
 */
public class CorrectedGyro extends Gyro{
    private static final int CAL_SAMPLES = 50;
    private static final int SAMPLING_RATE = 3;
    
    private double gyroValue = 0;
    private double previousValue;
    private boolean calibrated = false;
    private int sampleCount = 0;
    private int gyroBaseAngle;
    
    public CorrectedGyro(int channel) {
        super(channel);
        this.setSensitivity(.0125);
        if(!calibrated) calibrate();
    }
    
    private void calibrate()
    {

        while(sampleCount < CAL_SAMPLES) 
        {        
            gyroValue = this.getAngle();
            gyroBaseAngle += gyroValue; // Accumulate samples 
        } 
        
        gyroBaseAngle /= CAL_SAMPLES;
        calibrated = true;
    }
    public double getCorrectedAngle(){
        int count = 0;
        double value = this.getAngle();
        while(count < SAMPLING_RATE){
            gyroValue = this.getAngle();
            value = ((gyroValue + previousValue) / 2)-gyroBaseAngle;
            previousValue = value;
        }
        return value;
    }
    
}
