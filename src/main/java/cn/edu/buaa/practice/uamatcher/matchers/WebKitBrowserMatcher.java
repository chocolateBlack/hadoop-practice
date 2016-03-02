package cn.edu.buaa.practice.uamatcher.matchers;

import cn.edu.buaa.practice.uamatcher.Matcher;
import cn.edu.buaa.practice.uamatcher.UserAgentInfo;
import cn.edu.buaa.practice.util.StringUtils;

/**
 * Created by wushang on 15/10/28.
 */
public class WebKitBrowserMatcher implements Matcher {
    private final static String[] KEYS = {"AppleWebKit", "appleWebKit", "AppleWebkit"};
    private final static String KEY_ANDROID = "Android";
    private final static String KEY_IPHONE = "iPhone OS";

    private final static String BROWSER_NAME_FOR_IOS = "WebKit for IOS";
    private final static String BROWSER_NAME_FOR_ANDROID = "WebKit for Android";
    private final static String BROWSER_NAME_FOR_OTHER = "WebKit";

    private final char[] DILIMITERS = new char[]{' ', '/'};

    @Override
    public void match(String userAgent, UserAgentInfo userAgentInfo, int beginIndex) {
        if (userAgent.indexOf(KEY_ANDROID) > -1) {
            userAgentInfo.setBrowserName(BROWSER_NAME_FOR_ANDROID);
        } else if (userAgent.indexOf(KEY_IPHONE) > -1) {
            userAgentInfo.setBrowserName(BROWSER_NAME_FOR_IOS);
        } else {
            userAgentInfo.setBrowserName(BROWSER_NAME_FOR_OTHER);
        }
        beginIndex += KEYS[0].length();
        if (beginIndex >= userAgent.length())
            return;

        if (userAgent.charAt(beginIndex) == ' ' || userAgent.charAt(beginIndex) == '/') {
            String browserVersion = StringUtils.getNextToken(userAgent, DILIMITERS, ++beginIndex, 16, true);
            userAgentInfo.setBrowserVersionName(browserVersion);
        }
    }

    @Override
    public int tryMatch(String userAgent) {
        return StringUtils.indexOfAny(userAgent, KEYS);
    }
}
