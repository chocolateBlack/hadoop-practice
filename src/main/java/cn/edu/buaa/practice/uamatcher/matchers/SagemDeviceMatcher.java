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
public class SagemDeviceMatcher implements Matcher {
	private final static String BRAND_NAME = "Sagem";
	private final static char[][] KEYWORDS = {
		"Sagem".toCharArray(),
		"SAGEM".toCharArray()
	};
	
	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#match(java.lang.String, cn.edu.buaa.practice.uamatcher.UserAgentInfo, int)
	 */
	public void match(String userAgent, UserAgentInfo userAgentInfo, int beginIndex) {
		userAgentInfo.setBrandName(BRAND_NAME);
		userAgentInfo.setMobileDevice(true);
	}

	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#tryMatch(java.lang.String)
	 */
	public int tryMatch(String userAgent) {
		for(int i = 0; i < KEYWORDS.length; ++i) {
			if(StringUtils.startsWith(userAgent, KEYWORDS[i]))
				return 0;
		}
		
		return -1;
	}
}
