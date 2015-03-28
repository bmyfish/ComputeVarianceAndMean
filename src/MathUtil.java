import java.util.ArrayList;
/**
 * A set of utilities to calculate variance and mean of a given ArrayList
 * @author Ted
 */
public class MathUtil {
	/**
	 * Calculate the variance of the input ArrayList
	 * Note: Since the input data is sample(a selection taken from a bigger Population):
	 *       divide by N-1 when calculating Variance
	 *       If the input data is The Population: 
	 *       divide by N when calculating Variance   
	 * @param values the input ArrayList
	 * @return the variance of the input ArrayList
	 */
	public static double variance(ArrayList<Double> values) {
		double mean = mean(values);
		double dv = 0;
		for (double d: values) {
			double dm = d - mean;
			dv += (dm * dm);
		}
		if (values.size() < 2) {
			return 0;
		}
		double var = dv / (values.size() - 1);
		//double var = dv / values.size();
		return var;
	}
	
	/**
	 * Calculate the mean of the input ArrayList
	 * @param values the input ArrayList
	 * @return the mean of the input ArrayList
	 */
	
	public static strictfp double mean(ArrayList<Double> values) {
		return sum(values) / values.size();
	}
	
	/**
	 * Calculate the sum of the input ArrayList
	 * @param values the input ArrayList
	 * @return the sum of the input ArrayList
	 */
	
	public static strictfp double sum(ArrayList<Double> values) {
		if (values == null || values.size() == 0) {
			throw new IllegalArgumentException("The data array either is null or does not contain any data");
		}
		else {
			double sum = 0;
			for (int i = 0; i < values.size(); i++) {
				sum += values.get(i);
			}
			return sum;
		}
	}
}
