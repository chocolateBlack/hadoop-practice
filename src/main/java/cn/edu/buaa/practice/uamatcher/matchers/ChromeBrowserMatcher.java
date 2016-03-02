/**
 * 
 */
package cn.edu.buaa.practice.uamatcher.matchers;

import cn.edu.buaa.practice.uamatcher.Matcher;
import cn.edu.buaa.practice.uamatcher.UserAgentInfo;
import cn.edu.buaa.practice.util.StringUtils;

/**
 * @author BurningIce
 *         Mozilla/5.0 (iPhone; CPU iPhone OS 8_3 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) CriOS/45.0.2454.89 Mobile/12F70 Safari/600.1.4
 *         Mozilla/5.0 (Linux; Android 5.0; Lenovo K50-t5 Build/LRX21M) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/33.0.0.0 Mobile Safari/537.36
 *         Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36
 *         Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.132 Safari/537.36
 */
public class ChromeBrowserMatcher implements Matcher {
    private final static String BROWSER_NAME = "Chrome";
    private final static String BROWSER_NAME_ANDROID = "Chrome for Android";
    private final char DILIMITER = ' ';

    /* (non-Javadoc)
     * @see cn.edu.buaa.practice.uamatcher.Matcher#match(java.lang.String, cn.edu.buaa.practice.uamatcher.UserAgentInfo, int)
     */
    public void match(String userAgent, UserAgentInfo userAgentInfo, int beginIndex) {
        userAgentInfo.setBrowserName(getBrowserName(userAgent));

        beginIndex += 6;
        if (beginIndex >= userAgent.length())
            return;

        if (userAgent.charAt(beginIndex) == '/') {
            String browserVersion = StringUtils.getNextToken(userAgent, DILIMITER, ++beginIndex, 16, true);
            userAgentInfo.setBrowserVersionName(browserVersion);
        }
    }

    /* (non-Javadoc)
     * @see cn.edu.buaa.practice.uamatcher.Matcher#tryMatch(java.lang.String)
     */
    public int tryMatch(String userAgent) {
        return userAgent.indexOf(BROWSER_NAME);
    }

    private String getBrowserName(String userAgent) {
        if (userAgent.indexOf("Android") != -1) {
            return BROWSER_NAME_ANDROID;
        }
        return BROWSER_NAME;

    }
}
