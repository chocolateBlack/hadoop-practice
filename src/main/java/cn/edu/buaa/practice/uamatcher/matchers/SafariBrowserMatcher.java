/**
 * 
 */
package cn.edu.buaa.practice.uamatcher.matchers;

import cn.edu.buaa.practice.uamatcher.Matcher;
import cn.edu.buaa.practice.uamatcher.UserAgentInfo;
import cn.edu.buaa.practice.util.StringUtils;

/**
 * @author BurningIce
 *         Mozilla/5.0 (Linux;U;Android 4.1.2; zh-cn;) Version/4.0 LieBaoFast/3.19 Mobile Safari/535.19
 *         Mozilla/5.0 (iPhone; CPU iPhone OS 8_3 like Mac OS X) AppleWebKit/600.1.4.15.16 (KHTML, like Gecko) Version/6.0 Mobile/10A523 Safari/8536.25
 *         Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36
 */
public class SafariBrowserMatcher implements Matcher {
    private final static String BROWSER_NAME = "Safari";
    private final static String BROWSER_NAME_FOR_ANDROID =  "Safari for Android";
    private final static String BROWSER_NAME_FOR_IOS =  "Safari for IOS";

    private final char[] DILIMITERS = new char[]{' ', ')',';'};
    private final static int KEYWORD_LENGTH = BROWSER_NAME.length();

    /* (non-Javadoc)
     * @see cn.edu.buaa.practice.uamatcher.Matcher#match(java.lang.String, cn.edu.buaa.practice.uamatcher.UserAgentInfo, int)
     */
    public void match(String userAgent, UserAgentInfo userAgentInfo, int beginIndex) {
        userAgentInfo.setBrowserName(getBrowserName(userAgent));
        int versionIndex = userAgent.indexOf(" Version/");
        if (versionIndex == -1) {
            beginIndex += KEYWORD_LENGTH;
            if (beginIndex >= userAgent.length())
                return;

            if (userAgent.charAt(beginIndex) == '/') {
                String browserVersion = StringUtils.getNextToken(userAgent, DILIMITERS, ++beginIndex, 16, true);
                userAgentInfo.setBrowserVersionName(browserVersion);
            }
        } else {
            //preferablly
            String browserVersion = StringUtils.getNextToken(userAgent, DILIMITERS, versionIndex + 9, 16, true);
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
        if (userAgent.indexOf("iPhone OS") != -1)
            return BROWSER_NAME_FOR_IOS;
        else if (userAgent.indexOf("Android") != -1)
            return BROWSER_NAME_FOR_ANDROID;
        else
            return BROWSER_NAME;
    }
}
