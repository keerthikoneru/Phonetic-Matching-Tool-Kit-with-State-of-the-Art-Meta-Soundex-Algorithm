package PhoneticMatching;

/**
 * interface for calculating string distance for obtaining distance factor.
 *
 */
public interface MetricStringDistance extends StringDistance {
    public double distance(String s1, String s2);
}

