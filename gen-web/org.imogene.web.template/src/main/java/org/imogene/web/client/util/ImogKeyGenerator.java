package org.imogene.web.client.util;

public class ImogKeyGenerator {
	
	    /** The string separator value used in key format */
	    public static final String KEY_SEPARATOR = "_"; 

	    private static long _lastValue = 0L;
	    
	    /** Generate a unique key id for a card type */
	    public static String generateKeyId(String type)
	    {
	        return type + KEY_SEPARATOR + generateKeyId();
	    }
	    
	    // Generated the unique key id.
	    public static String generateKeyId()
	    {
	        long value;
	        
	        synchronized (ImogKeyGenerator.class)
	        {
	            while ((value = System.currentTimeMillis()) == _lastValue)
	            {
	               
	            }
	            _lastValue = value;
	        }

	        int poidFort = (int) ((value & 0xffff0000) >> 32);
	        int poidFaible = (int) (value & 0x0000ffff);
	        String kf = Integer.toHexString(poidFaible);
	        return (poidFort == 0) ? kf : Integer.toHexString(poidFort) + kf;
	    }

	    public static String getCardTypeFromKey(String cardKey)
	    {
	    	String result = "";
	    
	    	result = cardKey.substring(0, cardKey.indexOf(KEY_SEPARATOR));
	    	
	    	return result;
	    }

	

}
