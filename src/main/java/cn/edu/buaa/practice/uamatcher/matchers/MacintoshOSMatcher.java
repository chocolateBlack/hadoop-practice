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
public class MacintoshOSMatcher implements Matcher {
	private final static String OS_NAME = "Mac OS";
	private final static String KEYWORD = "Mac OS X";
	private final static char[] DILIMITERS = new char[] {';', ')'};
	private final static char[] LIKE = "like ".toCharArray();
	
	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#match(java.lang.String, cn.edu.buaa.practice.uamatcher.UserAgentInfo, int)
	 */
	public void match(String userAgent, UserAgentInfo userAgentInfo, int beginIndex) {
		userAgentInfo.setOsName(OS_NAME);
		userAgentInfo.setDesktopDevice(true);
		
		beginIndex += 8;
		if(beginIndex >= userAgent.length())
			return;
		
		if(userAgent.charAt(beginIndex) == ' ') {
			String osVersion = StringUtils.getNextToken(userAgent, DILIMITERS, ++beginIndex, 16);
			userAgentInfo.setOsVersionName(osVersion);
		}
	}

	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#tryMatch(java.lang.String)
	 */
	public int tryMatch(String userAgent) {
		int beginIndex = userAgent.indexOf(KEYWORD);
		if(beginIndex == -1)
			return -1;
		
		if(beginIndex >= 5) {
			if(StringUtils.followWith(userAgent, LIKE, beginIndex - 5))
				return -1;
		}
		
		return beginIndex;
	}
}
