/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team4159.frc2014;

import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.CriteriaCollection;
import edu.wpi.first.wpilibj.image.Image;
import edu.wpi.first.wpilibj.image.LinearAverages;
import edu.wpi.first.wpilibj.image.LinearAverages.LinearAveragesMode;
import edu.wpi.first.wpilibj.image.NIVision;
import edu.wpi.first.wpilibj.image.NIVision.Rect;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import java.util.Vector;

/**
 *
 * @author Team4159
 */
public class TargetDetector extends Thread
{
	public class Target
	{
		double centerX;
		double centerY;
		int targetType;
	}
	
	private static final int TARGET_OUTER = 1;
	private static final int TARGET_INNER = 2;
	
	private static final int EDGE_X = 3;
	private static final int EDGE_Y = 4;
	
	private static final double RECTANGULARITY_SCORE_THRESHOLD = 0.5;
	private static final double ASPECT_RATIO_SCORE_THRESHOLD = 0.5;
	private double X_EDGE_SCORE_THRESHOLD = 0.7;
	private double Y_EDGE_SCORE_THRESHOLD = 0.7;
	
	private static final double xMax[] = { 1, 1, 1, 1, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, 1, 1, 1, 1 };
	private static final double xMin[] = { .4, .6, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, .1, 0.6, 0 };
	private static final double yMax[] = { 1, 1, 1, 1, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, .5, 1, 1, 1, 1 };
	private static final double yMin[] = { .4, .6, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .05, .6, 0 };
	
	private boolean running = true;
	private final Vector results = new Vector ();
	
	public final void run ()
	{
		AxisCamera camera = AxisCamera.getInstance ("10.41.59.11");
		CriteriaCollection cc = new CriteriaCollection ();
		cc.addCriteria(NIVision.MeasurementType.IMAQ_MT_AREA, 512, 65535, false);
		Vector nextResults = new Vector ();
		
		int tries = 0;
		while (running)
		{
			ColorImage image = null;
			BinaryImage thresholdImage = null;
			BinaryImage convexHullImage = null;
			BinaryImage filteredImage = null;
			
			nextResults.removeAllElements ();
			
			try {
				image = camera.getImage();
				thresholdImage = image.thresholdHSV(48, 255, 190, 255, 184, 255);
				convexHullImage = thresholdImage.convexHull(false);
				filteredImage = convexHullImage.particleFilter(cc);
				
				for (int i = 0, ii = filteredImage.getNumberParticles(); i < ii; i++)
				{
					Rect rect = getBoundingRectangle (filteredImage, i);
					
					double rectangularityScore = scoreRectangularity (filteredImage, i, rect);
					if (rectangularityScore < RECTANGULARITY_SCORE_THRESHOLD)
						continue;

					double outerAspectRatioScore = scoreAspectRatio (filteredImage, i, TARGET_OUTER);
					double innerAspectRatioScore = scoreAspectRatio (filteredImage, i, TARGET_INNER);
					boolean couldBeOuter = outerAspectRatioScore >= ASPECT_RATIO_SCORE_THRESHOLD;
					boolean couldBeInner = innerAspectRatioScore >= ASPECT_RATIO_SCORE_THRESHOLD;
					if (!couldBeOuter && !couldBeInner)
						continue;
					
					double xEdgeScore = scoreEdge (filteredImage, i, rect, EDGE_X);
					double yEdgeScore = scoreEdge (filteredImage, i, rect, EDGE_Y);
					if (xEdgeScore < X_EDGE_SCORE_THRESHOLD || yEdgeScore < Y_EDGE_SCORE_THRESHOLD)
						continue;
					
					if (couldBeInner || couldBeOuter)
					{
						double cx = NIVision.MeasureParticle (image.image, i, false,
							NIVision.MeasurementType.IMAQ_MT_CENTER_OF_MASS_X);
						double cy = NIVision.MeasureParticle (image.image, i, false,
							NIVision.MeasurementType.IMAQ_MT_CENTER_OF_MASS_Y);
						System.out.println ((couldBeInner ? "inner" : "outer") + " target at (" + cx + "," + cy + ")");
						
						Target target = new Target ();
						target.centerX = cx;
						target.centerY = cy;
						target.targetType = couldBeInner ? TARGET_INNER : TARGET_OUTER;
						nextResults.addElement (target);
					}
				}
				
				synchronized (results)
				{
					int len = nextResults.size ();
					results.setSize (len);
					for (int i = 0; i < len; i++)
						results.setElementAt(nextResults.elementAt(i), i);
				}
				
				tries = 0;
			} catch (Exception exc) {
				System.out.println ("Vision exception occurred! (" + exc + ")");
				exc.printStackTrace();
				if (tries++ >= 10)
				{
					System.out.println ("Too many vision exceptions, bailing out");
					break;
				}
			} finally {
				freeImage (image);
				freeImage (thresholdImage);
				freeImage (convexHullImage);
				freeImage (filteredImage);
			}
		}
	}
	
	public final void stop ()
	{
		running = false;
		while (isAlive ())
		{
			try {
				join ();
			} catch (InterruptedException e) {}
		}
	}
	
	private static void freeImage (Image image)
	{
		if (image == null)
			return;
		try {
			image.free ();
		} catch (NIVisionException e) {
			System.out.println ("Failed to free image:" + e);
			e.printStackTrace ();
		}
	}
	
	private static Rect getBoundingRectangle (BinaryImage image, int index)
		throws NIVisionException
	{
		return new Rect (
			(int) NIVision.MeasureParticle (image.image, index, false,
				NIVision.MeasurementType.IMAQ_MT_BOUNDING_RECT_TOP),
			(int) NIVision.MeasureParticle (image.image, index, false,
				NIVision.MeasurementType.IMAQ_MT_BOUNDING_RECT_LEFT),
			(int) NIVision.MeasureParticle (image.image, index, false,
				NIVision.MeasurementType.IMAQ_MT_BOUNDING_RECT_HEIGHT),
			(int) NIVision.MeasureParticle (image.image, index, false,
				NIVision.MeasurementType.IMAQ_MT_BOUNDING_RECT_WIDTH)
		);
	}
	
	private static double scoreRectangularity (BinaryImage image, int index, Rect rect)
		throws NIVisionException
	{
		double ptarea = NIVision.MeasureParticle (image.image, index, false,
			NIVision.MeasurementType.IMAQ_MT_AREA);
		long bbarea = rect.getWidth () * rect.getHeight ();
		return bbarea != 0 ? (double) ptarea / bbarea : 0;
	}
	
	private static double scoreAspectRatio (BinaryImage image, int index, int target)
		throws NIVisionException
	{
		double rectLong = NIVision.MeasureParticle (image.image, index, false,
			NIVision.MeasurementType.IMAQ_MT_EQUIVALENT_RECT_LONG_SIDE);
		double rectShort = NIVision.MeasureParticle (image.image, index, false,
			NIVision.MeasurementType.IMAQ_MT_EQUIVALENT_RECT_SHORT_SIDE);
		double idealAspectRatio = (target == TARGET_INNER) ? (62.0 / 20.0) : (62.0 / 29.0);
		
		// check how close real is to ideal
		return 1.0 - Math.abs (1.0 - idealAspectRatio * rectShort / rectLong);
	}
	
	private static double scoreEdge (BinaryImage image, int index, Rect rect, int edge)
		throws NIVisionException
	{
		double total = 0;
		LinearAverages la = NIVision.getLinearAverages (
			image.image,
			(edge == EDGE_X) ?
				LinearAveragesMode.IMAQ_COLUMN_AVERAGES :
				LinearAveragesMode.IMAQ_ROW_AVERAGES,
			rect
		);
		
		float[] averages = (edge == EDGE_X) ?
			la.getColumnAverages () :
			la.getRowAverages ();
		
		double[] min = (edge == EDGE_X) ? xMin : yMin;
		double[] max = (edge == EDGE_X) ? xMax : yMax;
		
		for (int i = 0; i < averages.length; i++)
		{
			if (
				min[i * min.length / averages.length] < averages[i]
				&&
				averages[i] < max[i * max.length / averages.length]
			)
			{
				total++;
			}
		}
		
		return (double) total / averages.length;
	}
}
