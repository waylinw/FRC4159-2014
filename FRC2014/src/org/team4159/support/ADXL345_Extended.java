package org.team4159.support;

import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SensorBase;

public class ADXL345_Extended extends SensorBase
{
	private static final byte I2C_ADDRESS = 0x3A;
	private static final byte[] DEVID_EXPECTED = { (byte) 0xE5 };
	private static final double GS_PER_LSB = 1.0 / 256;
	private static final double MS2_PER_LSB = GS_PER_LSB * 9.80665;
	
	private static final byte
		REG_DEVID = 0x00,
		REG_THRESH_TAP = 0x1D,
		REG_OFSX = 0x1E,
		REG_OFSY = 0x1F,
		REG_OFSZ = 0x20,
		REG_DUR = 0x21,
		REG_Latent = 0x22,
		REG_Window = 0x23,
		REG_THRESH_ACT = 0x24,
		REG_THRESH_INACT = 0x25,
		REG_TIME_INACT = 0x26,
		REG_INACT_CTL = 0x27,
		REG_THRESH_FF = 0x28,
		REG_TIME_FF = 0x29,
		REG_TAP_AXES = 0x2A,
		REG_ACT_TAP_STATUS = 0x2B,
		REG_BW_RATE = 0x2C,
		REG_POWER_CTL = 0x2D,
		REG_INT_ENABLE = 0x2E,
		REG_INT_MAP = 0x2F,
		REG_INT_SOURCE = 0x30,
		REG_DATA_FORMAT = 0x31,
		REG_DATAX0 = 0x32,
		REG_DATAX1 = 0x33,
		REG_DATAY0 = 0x34,
		REG_DATAY1 = 0x35,
		REG_DATAZ0 = 0x36,
		REG_DATAZ1 = 0x37,
		REG_FIFO_CTL = 0x38,
		REG_FIFO_STATUS = 0x39;
	
	private static final byte
		MASK_POWER_CTL_Link = 0x20,
		MASK_POWER_CTL_AUTO_SLEEP = 0x10,
		MASK_POWER_CTL_Measure = 0x08,
		MASK_POWER_CTL_Sleep = 0x04,
		MASK_POWER_CTL_Wakeup = 0x03,
		SHIFT_POWER_CTL_Wakeup = 0;
	
	private static final byte
		MASK_DATA_FORMAT_SELF_TEST = (byte) 0x80,
		MASK_DATA_FORMAT_SPI = 0x40,
		MASK_DATA_FORMAT_INT_INVERT = 0x20,
		MASK_DATA_FORMAT_FULL_RES = 0x08,
		MASK_DATA_FORMAT_Justify = 0x04,
		MASK_DATA_FORMAT_Range = 0x03,
		SHIFT_DATA_FORMAT_Range = 0;
	
	private static final byte
		MASK_BW_RATE_LOW_POWER = 0x10,
		MASK_BW_RATE_Rate = 0x0f,
		SHIFT_BW_RATE_Rate = 0,
		VAL_BW_RATE_Rate_0_10 = 0,
		VAL_BW_RATE_Rate_0_20 = 1,
		VAL_BW_RATE_Rate_0_39 = 2,
		VAL_BW_RATE_Rate_0_78 = 3,
		VAL_BW_RATE_Rate_1_56 = 4,
		VAL_BW_RATE_Rate_3_13 = 5,
		VAL_BW_RATE_Rate_6_25 = 6,
		VAL_BW_RATE_Rate_12_5 = 7,
		VAL_BW_RATE_Rate_25 = 8,
		VAL_BW_RATE_Rate_50 = 9,
		VAL_BW_RATE_Rate_100 = 10,
		VAL_BW_RATE_Rate_200 = 11,
		VAL_BW_RATE_Rate_400 = 12,
		VAL_BW_RATE_Rate_800 = 13,
		VAL_BW_RATE_Rate_1600 = 14,
		VAL_BW_RATE_Rate_3200 = 15;
	
	private final I2C i2c;
	private final byte[] data = new byte[2];
	
	public ADXL345_Extended ()
	{
		DigitalModule module = DigitalModule.getInstance (getDefaultDigitalModule ());
		i2c = module.getI2C (I2C_ADDRESS);

		// check if sensor is there
		if (!i2c.verifySensor (REG_DEVID, 1, DEVID_EXPECTED))
		{
			System.out.println ("warning: accelerometer not found");
			return;
		}
		
		// set data format
		i2c.write (REG_DATA_FORMAT, MASK_DATA_FORMAT_FULL_RES | (0x03 << SHIFT_DATA_FORMAT_Range));
		
		// set sampling rate
		i2c.write (REG_BW_RATE, VAL_BW_RATE_Rate_50 << SHIFT_BW_RATE_Rate);
		
		// start measurements
		i2c.write (REG_POWER_CTL, MASK_POWER_CTL_Measure);
	}
	
	private double getAccel (int reg0)
	{
		i2c.read (reg0, 2, data);
		return (data[0] | (data[1] << 8)) * MS2_PER_LSB;
	}
	
	public double getX () { return getAccel (REG_DATAX0); }
	public double getY () { return getAccel (REG_DATAY0); }
	public double getZ () { return getAccel (REG_DATAZ0); }
}