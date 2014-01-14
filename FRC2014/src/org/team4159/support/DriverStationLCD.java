package org.team4159.support;

import edu.wpi.first.wpilibj.DriverStationLCD.Line;

public class DriverStationLCD
{
	private static final Line[] lines = {
		Line.kUser1,
		Line.kUser2,
		Line.kUser3,
		Line.kUser4,
		Line.kUser5,
		Line.kUser6
	};
	
	private static final edu.wpi.first.wpilibj.DriverStationLCD lcd = 
		edu.wpi.first.wpilibj.DriverStationLCD.getInstance ();
	
	public static void setLine (int line, String text)
	{
		StringBuffer sb = new StringBuffer (text);
		while (sb.length () < edu.wpi.first.wpilibj.DriverStationLCD.kLineLength)
			sb.append (' ');
	
		lcd.println (lines[line], 1, sb);
		lcd.updateLCD ();
	}
}
