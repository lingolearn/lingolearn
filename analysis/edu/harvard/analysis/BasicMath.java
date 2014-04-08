package edu.harvard.analysis;

import java.util.List;

public class BasicMath {
	/**
	 * Find the average of the values provided.
	 */
	public static float average(List<Integer> values) {
		float divisor = 0, sum = 0;
		for (Integer value : values) {
			sum += value;
			divisor += (value != 0) ? 1 : 0;
		}
		return sum / divisor;
	}
	
	/**
	 * Find the count of the non-zero values.
	 */
	public static int notZero(List<Integer> values) {
		int count = 0;
		for (Integer value : values) {
			count += (value != 0) ? 1 : 0;
		}
		return count;
	}
	
	/**
	 * Find the sum of the values.
	 */
	public static int sum(List<Integer> values) {
		int sum = 0;
		for (Integer value : values) {
			sum += value;
		}
		return sum;
	}
}
