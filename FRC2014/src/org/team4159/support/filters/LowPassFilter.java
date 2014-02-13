package org.team4159.support.filters;

import com.sun.squawk.util.MathUtils;

public class LowPassFilter implements Filter
{
	private double coefficient;
	
	private double accumulator = 0;
	
	public LowPassFilter (double coefficient)
	{
		setCoefficient (coefficient);
	}
	
	public double getCoefficient ()
	{
		return coefficient;
	}
	
	public void setCoefficient (double coefficient)
	{
		this.coefficient = coefficient;
	}

	public void update (double value, double interval)
	{
		double error = value - accumulator;
		accumulator += error * -MathUtils.expm1 (-coefficient * interval);
	}

	public double get ()
	{
		return accumulator;
	}
}
