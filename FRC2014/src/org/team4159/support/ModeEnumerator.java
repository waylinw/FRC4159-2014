/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team4159.support;

import edu.wpi.first.wpilibj.DriverStation;

/**
 *
 * @author Team4159
 */
public class ModeEnumerator
{
	public static final int
		UNKNOWN = 0,
		DISABLED = 1,
		AUTONOMOUS = 2,
		OPERATOR = 3,
		TEST = 4;
	
	public static final DriverStation driverStation = DriverStation.getInstance();
	
	public static int getMode ()
	{
		DriverStation ds = driverStation;
		if (ds.isDisabled ())
			return DISABLED;
		if (ds.isTest ())
			return TEST;
		if (ds.isAutonomous())
			return AUTONOMOUS;
		if (ds.isOperatorControl())
			return OPERATOR;
		return UNKNOWN;
	}
}
