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
public class CentOSMatcher implements Matcher {
	private final static String OS_NAME = "CentOS";
	private final char[] DILIMITERS = new char[] {'-', ' '};
	
	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#match(java.lang.String, cn.edu.buaa.practice.uamatcher.UserAgentInfo, int)
	 */
	public void match(String userAgent, UserAgentInfo userAgentInfo, int beginIndex) {
		userAgentInfo.setOsName(OS_NAME);
		
		beginIndex += 6;
		
		if(beginIndex < userAgent.length() && userAgent.charAt(beginIndex) == '/') {
			String osVersion = StringUtils.getNextToken(userAgent, DILIMITERS, ++beginIndex, 16);
			userAgentInfo.setOsVersionName(osVersion);
		}
	}

	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#tryMatch(java.lang.String)
	 */
	public int tryMatch(String userAgent) {
		return userAgent.indexOf(OS_NAME);
	}
}
