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
public class BotMatcher implements Matcher {
	private final static String[] BOT_KEYWORDS = {
		"bot", 
		"spider",
		"crawler",
		"yahoo! searchmonkey",
		"yahoo! slurp",
		"toolbar",
		"wget",
		"http client",
	};
	
	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#match(java.lang.String, cn.edu.buaa.practice.uamatcher.UserAgentInfo, int)
	 */
	public void match(String userAgent, UserAgentInfo userAgentInfo, int tryMatchResult) {
		userAgentInfo.setBrowserName("Bot");
		userAgentInfo.setBrandName("Other");
	}

	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#tryMatch(java.lang.String)
	 */
	public int tryMatch(String userAgent) {
		return StringUtils.indexOfAny(userAgent, BOT_KEYWORDS);
	}
}
