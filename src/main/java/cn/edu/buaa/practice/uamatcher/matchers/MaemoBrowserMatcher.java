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
public class MaemoBrowserMatcher implements Matcher {
	private final static String BROWSER_NAME = "Maemo Browser";
	private final static String KEYWORD = "Maemo ";
	private final static char[][] KEYWORD_SUFFIX = {
		"Browser".toCharArray(),
		"browser".toCharArray()
	};
	private final char DELIMITER = ' ';
	
	private final static int KEYWORD_LENGTH = KEYWORD.length();
	
	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#match(java.lang.String, cn.edu.buaa.practice.uamatcher.UserAgentInfo, int)
	 */
	public void match(String userAgent, UserAgentInfo userAgentInfo, int beginIndex) {
		userAgentInfo.setBrowserName(BROWSER_NAME);
		userAgentInfo.setMobileDevice(true);
		
		beginIndex += KEYWORD_LENGTH;
		
		if(beginIndex >= userAgent.length())
			return;
		
		for(int i = 0; i < KEYWORD_SUFFIX.length; ++i) {
			if(StringUtils.followWith(userAgent, KEYWORD_SUFFIX[i], beginIndex)) {
				//found
				beginIndex += KEYWORD_SUFFIX[i].length;
				if(userAgent.charAt(beginIndex) == ' ') {
					String browserVersion = StringUtils.getNextToken(userAgent, DELIMITER, ++beginIndex, 16);
					userAgentInfo.setBrowserVersionName(browserVersion);
					
					//check Nokia-N900: Mozilla/5.0 (X11; U; Linux armv7l; fi-FI; rv:1.9.2a1pre) Gecko/20090928 Firefox/3.5 Maemo Browser 1.4.1.15 RX-51 N900
					if(userAgent.indexOf(" N900", beginIndex) != -1) {
						userAgentInfo.setBrandName("Nokia");
						userAgentInfo.setModelName("N900");
					}
				}
				
				break;
			}
		}
	}

	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#tryMatch(java.lang.String)
	 */
	public int tryMatch(String userAgent) {
		return userAgent.indexOf(KEYWORD);
	}
}
