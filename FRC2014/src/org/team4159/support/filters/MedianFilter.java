package org.team4159.support.filters;

import com.sun.squawk.util.Arrays;

public class MedianFilter implements Filter
{
	private final int length;
	
	private final double[] samples;
	private int index;
	
	private final double[] sorted;
	private double last;
	
	public MedianFilter (int n)
	{
		if (n % 2 != 1)
			n += 1;
		
		length = n;
		samples = new double[length];
		index = length - 1;
		sorted = new double[length];
		last = Double.NaN;
	}
	
	public void update (double value, double interval)
	{
		samples[index = (index + 1) % length] = value;
		last = Double.NaN;
	}

	public double get ()
	{
		if (Double.isNaN (last))
		{
			System.arraycopy (samples, 0, sorted, 0, length);
			Arrays.sort (sorted);
			last = sorted[length / 2];
		}
		
		return last;
	}
}
