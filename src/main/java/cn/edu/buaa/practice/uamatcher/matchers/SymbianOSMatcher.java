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
public class SymbianOSMatcher implements Matcher {
	private final static String OS_NAME = "Symbian";
	private final String KEYWORDS_PREFIX = "Symb";
	private final char[][] KEYWORDS_SUFFIX = new char[][] {
		"ianOS".toCharArray(),
		"OS".toCharArray()
	};
	
	private final char[] DILIMITERS = new char[] {';', ' ', ')'};
	
	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#match(java.lang.String, cn.edu.buaa.practice.uamatcher.UserAgentInfo, int)
	 */
	public void match(String userAgent, UserAgentInfo userAgentInfo, int beginIndex) {
		userAgentInfo.setOsName(OS_NAME);
		userAgentInfo.setMobileDevice(true);
		
		if(beginIndex >= userAgent.length())
			return;
		
		int c = userAgent.charAt(beginIndex);
		if(c == '/' || c == ' ') {
			String osVersion = StringUtils.getNextToken(userAgent, DILIMITERS, ++beginIndex, 16);
			userAgentInfo.setOsVersionName(osVersion);
		}
	}

	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#tryMatch(java.lang.String)
	 */
	public int tryMatch(String userAgent) {
		int k = userAgent.indexOf(KEYWORDS_PREFIX);
		if(k != -1) {
			for(int i = 0; i < KEYWORDS_SUFFIX.length; ++i) {
				if(StringUtils.followWith(userAgent, KEYWORDS_SUFFIX[i], k + 4)) {
					return k + KEYWORDS_PREFIX.length() + KEYWORDS_SUFFIX[i].length;
				}
			}
		}
		
		return -1;
	}
}
