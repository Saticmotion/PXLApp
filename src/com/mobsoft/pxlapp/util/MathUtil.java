package com.mobsoft.pxlapp.util;

public class MathUtil 
{
	public static int clamp(int value, int min, int max)
	{
		return Math.min(max, Math.max(min, value));
	}
	
	public static float clampf(float value, float min, float max)
	{
		return Math.min(max, Math.max(min, value));
	}
}
