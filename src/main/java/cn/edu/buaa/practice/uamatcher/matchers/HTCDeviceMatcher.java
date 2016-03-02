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
public class HTCDeviceMatcher implements Matcher {
	private final static String BRAND_NAME = "HTC";
	private final static String KEYWORD = "HTC";
	private final static int KEYWORD_LENGTH = KEYWORD.length();
	private final static char DELIMITER = ' ';
	
	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#match(java.lang.String, cn.edu.buaa.practice.uamatcher.UserAgentInfo, int)
	 */
	public void match(String userAgent, UserAgentInfo userAgentInfo, int beginIndex) {
		userAgentInfo.setBrandName(BRAND_NAME);
		userAgentInfo.setMobileDevice(true);
		
		beginIndex += KEYWORD_LENGTH;
		if(beginIndex >= userAgent.length())
			return;
		
		char nextChar = userAgent.charAt(beginIndex);
		if(nextChar == ' ' || nextChar == '_' || nextChar == '-') {
			String modelName = StringUtils.getNextToken(userAgent, DELIMITER, ++beginIndex, 16);
			userAgentInfo.setModelName(modelName);
		}
	}

	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#tryMatch(java.lang.String)
	 */
	public int tryMatch(String userAgent) {
		int beginIndex = userAgent.indexOf(KEYWORD);
		if(beginIndex == -1 || beginIndex == 0)
			return beginIndex;
		
		
		char prevChar = userAgent.charAt(beginIndex - 1);
		return (prevChar == ' ' || prevChar == '_' ? beginIndex : -1);
	}
}
