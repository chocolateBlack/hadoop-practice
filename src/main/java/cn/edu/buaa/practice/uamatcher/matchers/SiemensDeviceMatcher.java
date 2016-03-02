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
public class SiemensDeviceMatcher implements Matcher {
	private final static String BRAND_NAME = "Siemens";
	private final static char[] KEYWORD = "SIE-".toCharArray();
	private final static int KEYWORD_LENGTH = KEYWORD.length;
	private final static char DELIMITER = '/';
	
	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#match(java.lang.String, cn.edu.buaa.practice.uamatcher.UserAgentInfo, int)
	 */
	public void match(String userAgent, UserAgentInfo userAgentInfo, int beginIndex) {
		userAgentInfo.setBrandName(BRAND_NAME);
		userAgentInfo.setMobileDevice(true);
		
		beginIndex += KEYWORD_LENGTH;
		if(beginIndex >= userAgent.length())
			return;
		
		String modelName = StringUtils.getNextToken(userAgent, DELIMITER, beginIndex, 16);
		userAgentInfo.setModelName(modelName);
	}

	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#tryMatch(java.lang.String)
	 */
	public int tryMatch(String userAgent) {
		if(StringUtils.startsWith(userAgent, KEYWORD))
			return 0;
		
		return -1;
	}
}
