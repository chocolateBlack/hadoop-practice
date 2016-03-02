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
public class WindowsOSMatcher implements Matcher {
	private final static String OS_NAME = "Windows";
	private final static char[] PLATFORM_NT = "NT".toCharArray();
	private final static char[] PLATFORM_PHONE_OS = "Phone OS".toCharArray();
	private final static char[] PLATFORM_MOBILE = "Mobile".toCharArray();
	private final static char[] PLATFORM_CE = "CE".toCharArray();
	
	private final char[] DILIMITERS = new char[] {';', ')', '(', ' '};
	
	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#match(java.lang.String, cn.edu.buaa.practice.uamatcher.UserAgentInfo, int)
	 */
	public void match(String userAgent, UserAgentInfo userAgentInfo, int beginIndex) {
		userAgentInfo.setOsName(OS_NAME);
		
		beginIndex += 7;
		if(beginIndex >= userAgent.length())
			return;
		
		char nextChar = userAgent.charAt(beginIndex);
		for( ; nextChar == ';'; ) {
			//search for next ocurrence
			beginIndex = userAgent.indexOf(OS_NAME, beginIndex);
			if(beginIndex == -1) 
				return;
			
			beginIndex += 7;
			if(beginIndex >= userAgent.length())
				return;
			
			nextChar = userAgent.charAt(beginIndex);
		}
		
		if(nextChar == ' ') {
			++beginIndex;
			
			if(StringUtils.followWith(userAgent, PLATFORM_NT, beginIndex)) {
				userAgentInfo.setOsName("Windows");
				beginIndex += 3;				
				String osVersion = StringUtils.getNextToken(userAgent, DILIMITERS, beginIndex, 16);
				userAgentInfo.setOsVersionName(osVersion);
				userAgentInfo.setDesktopDevice(true);
			} else if(StringUtils.followWith(userAgent, PLATFORM_PHONE_OS, beginIndex)) {
				userAgentInfo.setOsName("Windows Phone OS");
				beginIndex += 9;
				String osVersion = StringUtils.getNextToken(userAgent, DILIMITERS, beginIndex, 16);
				userAgentInfo.setOsVersionName(osVersion);
				userAgentInfo.setMobileDevice(true);
			} else if(StringUtils.followWith(userAgent, PLATFORM_MOBILE, beginIndex)) {
				userAgentInfo.setOsName("Windows Mobile");
				beginIndex += 7;
				String osVersion = StringUtils.getNextToken(userAgent, DILIMITERS, beginIndex, 16);
				userAgentInfo.setOsVersionName(osVersion);
				userAgentInfo.setMobileDevice(true);
			} else if(StringUtils.followWith(userAgent, PLATFORM_CE, beginIndex)) {
				userAgentInfo.setOsName("Windows CE");
				beginIndex += 3;
				String osVersion = StringUtils.getNextToken(userAgent, DILIMITERS, beginIndex, 16);
				userAgentInfo.setOsVersionName(osVersion);
				userAgentInfo.setMobileDevice(true);
			}
		}
	}

	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#tryMatch(java.lang.String)
	 */
	public int tryMatch(String userAgent) {
		return userAgent.indexOf(OS_NAME);
	}
}
