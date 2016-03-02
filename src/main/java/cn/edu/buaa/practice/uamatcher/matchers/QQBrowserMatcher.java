package cn.edu.buaa.practice.uamatcher.matchers;

import cn.edu.buaa.practice.uamatcher.Matcher;
import cn.edu.buaa.practice.uamatcher.UserAgentInfo;
import cn.edu.buaa.practice.util.StringUtils;

/**
 * Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.69 Safari/537.36 QQBrowser/9.1.3471.400
 */
public class QQBrowserMatcher implements Matcher {

    private final static String BROWSER_NAME = "QQBrowser";
    private final static int BROWSER_LENTH = BROWSER_NAME.length();
    private final char DILIMITER = ' ';

    @Override
    public void match(String userAgent, UserAgentInfo userAgentInfo, int beginIndex) {
        userAgentInfo.setBrowserName(BROWSER_NAME);
        beginIndex += BROWSER_LENTH;
        if (beginIndex >= userAgent.length())
            return;
        if (userAgent.charAt(beginIndex) == '/') {
//            userAgentInfo.setBrowserVersionName(userAgent.substring(beginIndex+1));
            String browserVersion = StringUtils.getNextToken(userAgent, DILIMITER, ++beginIndex, 16, true);
            userAgentInfo.setBrowserVersionName(browserVersion);
        }
    }

    @Override
    public int tryMatch(String userAgent) {
        return userAgent.indexOf(BROWSER_NAME);
    }
}
