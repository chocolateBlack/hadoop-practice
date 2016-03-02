/**
 * 
 */
package cn.edu.buaa.practice.uamatcher.matchers;

import cn.edu.buaa.practice.uamatcher.Matcher;
import cn.edu.buaa.practice.uamatcher.UserAgentInfo;
import cn.edu.buaa.practice.util.StringUtils;

/**
 * @author BurningIce
 *
 */
public class SamsungDeviceMatcher implements Matcher {
	private final static String BRAND_NAME = "Samsung";
	private final static char[][] KEYWORDS = {
		"SAMSUNG-".toCharArray(),
		"SEC".toCharArray()
	};
	
	private final char DILIMITER = '/';
	
	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#match(java.lang.String, cn.edu.buaa.practice.uamatcher.UserAgentInfo, int)
	 */
	public void match(String userAgent, UserAgentInfo userAgentInfo, int beginIndex) {
		userAgentInfo.setBrandName(BRAND_NAME);
		userAgentInfo.setMobileDevice(true);
		
		String modelName = StringUtils.getNextToken(userAgent, DILIMITER, beginIndex, 16);
		userAgentInfo.setModelName(modelName);
	}

	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#tryMatch(java.lang.String)
	 */
	public int tryMatch(String userAgent) {
		for(int i = 0; i < KEYWORDS.length; ++i) {
			if(StringUtils.startsWith(userAgent, KEYWORDS[i]))
				//return 0;
				return KEYWORDS[i].length;
		}
		
		return -1;
	}
}
