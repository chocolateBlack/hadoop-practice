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
public class BlackBerryDeviceMatcher implements Matcher {
	private final static String BRAND_NAME = "BlackBerry";
	private final char[] DILIMITERS = new char[] {';', '/', ')'};
	private final int KEYWORD_LENGTH = BRAND_NAME.length();
	
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
		if(nextChar == ';') {
			beginIndex = userAgent.indexOf(BRAND_NAME, beginIndex);	//try next match
			if(beginIndex != -1) {
				beginIndex += KEYWORD_LENGTH;
			}
		}
		
		if(beginIndex != -1) {
			if(userAgent.charAt(beginIndex) != ' ')
				++beginIndex;
			
			String modelName = StringUtils.getNextToken(userAgent, DILIMITERS, beginIndex, 8);
			userAgentInfo.setModelName(modelName);
		}
	}

	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#tryMatch(java.lang.String)
	 */
	public int tryMatch(String userAgent) {
		return userAgent.indexOf(BRAND_NAME);
	}
}
