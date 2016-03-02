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
public class TencentTravelerBrowserMatcher implements Matcher {
	private final static String BROWSER_NAME = "TencentTraveler";
	private final static String KEYWORD = "TencentTraveler";
	
	private final char[] DILIMITERS = new char[] {';', ')'};
	private final int KEYWORD_LENGTH = KEYWORD.length();
	
	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#match(java.lang.String, cn.edu.buaa.practice.uamatcher.UserAgentInfo, int)
	 */
	public void match(String userAgent, UserAgentInfo userAgentInfo, int beginIndex) {
		userAgentInfo.setBrowserName(BROWSER_NAME);
		
		beginIndex += KEYWORD_LENGTH;
		if(beginIndex >= userAgent.length())
			return;
		
		char nextChar = userAgent.charAt(beginIndex);
		if(nextChar == ' ' || nextChar == '/') {
			String browserVersion = StringUtils.getNextToken(userAgent, DILIMITERS, ++beginIndex, 8);
			userAgentInfo.setBrowserVersionName(browserVersion);
		}
	}

	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#tryMatch(java.lang.String)
	 */
	public int tryMatch(String userAgent) {
		return userAgent.indexOf(KEYWORD);
	}
}
