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
public class DopodDeviceMatcher implements Matcher {
	private final static String BRAND_NAME = "Samsung";
	private final static char[][] KEYWORDS = {
		"Dopod".toCharArray(),
		"dopod".toCharArray(),
		"DOPOD".toCharArray()
	};
	
	private final char[] DILIMITERS = {'/', '_'};
	
	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#match(java.lang.String, cn.edu.buaa.practice.uamatcher.UserAgentInfo, int)
	 */
	public void match(String userAgent, UserAgentInfo userAgentInfo, int beginIndex) {
		userAgentInfo.setBrandName(BRAND_NAME);
		userAgentInfo.setMobileDevice(true);
		
		beginIndex += 5;
		if(beginIndex >= userAgent.length())
			return;
		
		switch(userAgent.charAt(beginIndex)) {
		case ' ' :
		case '-' :
			++beginIndex;
			break;
		case '/' :
			return;
		}
		
		String modelName = StringUtils.getNextToken(userAgent, DILIMITERS, beginIndex, 8);
		userAgentInfo.setModelName(modelName);
	}

	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#tryMatch(java.lang.String)
	 */
	public int tryMatch(String userAgent) {
		for(int i = 0; i < KEYWORDS.length; ++i) {
			if(StringUtils.startsWith(userAgent, KEYWORDS[i]))
				return 0;
		}
		
		return -1;
	}
}
