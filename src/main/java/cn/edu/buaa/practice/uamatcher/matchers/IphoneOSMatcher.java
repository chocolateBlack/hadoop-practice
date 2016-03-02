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
public class IphoneOSMatcher implements Matcher {
	private final String[] KEYWORDS = new String[] {"iPhone", "iPad", "iPod"};
	private final char[] DILIMITERS = new char[] {' ', ';', ')'};
	
	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#match(java.lang.String, cn.edu.buaa.practice.uamatcher.UserAgentInfo, int)
	 */
	public void match(String userAgent, UserAgentInfo userAgentInfo, int tryMatchResult) {
		userAgentInfo.setOsName("iOS");
		userAgentInfo.setMobileDevice(true);
		
		int beginIndex = userAgent.indexOf("iPhone OS");
		if(beginIndex != -1) {
			beginIndex += 9;
			if(beginIndex >= userAgent.length())
				return;
			
			if(userAgent.charAt(beginIndex) == ' ') {
				String osVersion = StringUtils.getNextToken(userAgent, DILIMITERS, ++beginIndex, 8);
				if(osVersion != null) {
					userAgentInfo.setOsVersionName(osVersion.replace('_', '.'));
				}
			}
		}
		
		// get device
		if(userAgent.indexOf("iPad") != -1) {
			userAgentInfo.setBrandName("iPad");
		} else if(userAgent.indexOf("iPod") != -1) {
			userAgentInfo.setBrandName("iPod");
		} else {
			userAgentInfo.setBrandName("iPhone");
		}
	}

	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#tryMatch(java.lang.String)
	 */
	public int tryMatch(String userAgent) {
		int k;
		for(int i = 0; i < KEYWORDS.length; ++i) {
			if((k = userAgent.indexOf(KEYWORDS[i])) != -1) {
				return k;
			}
		}
		
		return -1;
	}
}
