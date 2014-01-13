package org.imogene.lib.sync.handler;

/**
 * Helper class to create unique id for bean.
 * 
 * @author MEDES-IMPS
 */
public class BeanKeyGenerator {

	public static final String KEY_SEPARATOR = "_";

	private static long _lastValue = 0L;

	/**
	 * Return unique id for a bean corresponding to type.
	 * 
	 * @param type bean type short name.
	 * @return unique id.
	 */
	public static String getNewId(String type) {
		return type + KEY_SEPARATOR + generateKeyId();
	}

	/**
	 * Generate unique id key based on current system time
	 * 
	 * @return unique id key
	 */
	public static String generateKeyId() {
		long value;

		while ((value = System.currentTimeMillis()) == _lastValue) {
			// iterate
		}
		_lastValue = value;

		int poidFort = (int) ((value & 0xffff0000) >> 32);
		int poidFaible = (int) (value & 0x0000ffff);
		String kf = Integer.toHexString(poidFaible);
		return (poidFort == 0) ? kf : Integer.toHexString(poidFort) + kf;
	}

}
