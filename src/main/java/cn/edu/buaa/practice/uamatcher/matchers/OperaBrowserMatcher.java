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
public class OperaBrowserMatcher implements Matcher {
	private final static String BROWSER_NAME = "Opera";
	private final static int KEYWORD_LENGTH = BROWSER_NAME.length();
	private final char DILIMITER = ' ';
	
	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#match(java.lang.String, cn.edu.buaa.practice.uamatcher.UserAgentInfo, int)
	 */
	public void match(String userAgent, UserAgentInfo userAgentInfo, int beginIndex) {
		userAgentInfo.setBrowserName(BROWSER_NAME);
		
		beginIndex += KEYWORD_LENGTH;
		if(beginIndex >= userAgent.length())
			return;
		
		if(userAgent.charAt(beginIndex) == '/') {
			String browserVersion = StringUtils.getNextToken(userAgent, DILIMITER, ++beginIndex, 8, true);
			userAgentInfo.setBrowserVersionName(browserVersion);
		}
	}

	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#tryMatch(java.lang.String)
	 */
	public int tryMatch(String userAgent) {
		return userAgent.indexOf(BROWSER_NAME);
	}
}
