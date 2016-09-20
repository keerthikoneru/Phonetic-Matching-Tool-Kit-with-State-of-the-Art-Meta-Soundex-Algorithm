package PhoneticMatching;

import java.io.Serializable;

/**
 * @author keerthi
 * 
 *         interface for calculating distance factor.
 *
 */
public interface StringDistance extends Serializable {

	public double distance(String s1, String s2);

}