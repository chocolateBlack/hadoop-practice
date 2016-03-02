/**
 * 
 */
package cn.edu.buaa.practice.uamatcher.matchers;

import cn.edu.buaa.practice.uamatcher.Matcher;
import cn.edu.buaa.practice.uamatcher.UserAgentInfo;
import cn.edu.buaa.practice.util.StringUtils;

/**
 * @author BurningIce
 * 火狐未开发ios版本
 */
public class FirefoxBrowserMatcher implements Matcher {
	private final static String BROWSER_NAME = "Firefox";
	private final static String BROWSER_NAME_FOR_ANDROID = "Firefox for Android";
	private final char[] DILIMITERS = new char[] {' ', '(', ')', ';'};
	
	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#match(java.lang.String, cn.edu.buaa.practice.uamatcher.UserAgentInfo, int)
	 */
	public void match(String userAgent, UserAgentInfo userAgentInfo, int beginIndex) {
		userAgentInfo.setBrowserName(getBrowserName(userAgent));
		
		beginIndex += 7;
		if(beginIndex >= userAgent.length())
			return;
		
		char nextChar = userAgent.charAt(beginIndex);
		switch(nextChar) {
		case '/':
			String browserVersion = StringUtils.getNextToken(userAgent, DILIMITERS, ++beginIndex, 16, true);
			userAgentInfo.setBrowserVersionName(browserVersion);
			break;
		case ' ':
			++beginIndex;
			nextChar = userAgent.charAt(beginIndex);
			if(nextChar >= '0' && nextChar <= '9') {
				browserVersion = StringUtils.getNextToken(userAgent, DILIMITERS, beginIndex, 16, true);
				userAgentInfo.setBrowserVersionName(browserVersion);
			}
			break;
		}
	}

	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#tryMatch(java.lang.String)
	 */
	public int tryMatch(String userAgent) {
		return userAgent.indexOf(BROWSER_NAME);
	}

	private String getBrowserName(String userAgent) {
		if (userAgent.indexOf("Android") != -1)
			return BROWSER_NAME_FOR_ANDROID;
		else
			return BROWSER_NAME;
	}
}
