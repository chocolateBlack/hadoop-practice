package cn.edu.buaa.practice.uamatcher.matchers;

import cn.edu.buaa.practice.uamatcher.Matcher;
import cn.edu.buaa.practice.uamatcher.UserAgentInfo;
import cn.edu.buaa.practice.util.StringUtils;

/**
 * Created by wushang on 15/9/17.
 */
public class OperaAndroidBrowserMatcher implements Matcher{
    private final static String BROWSER_KEYWORD = "OPR";
    private final static int BROWSER_KEYWORD_LENGTH = BROWSER_KEYWORD.length();
    private final static String BROWSER_NAME_FOR_ANDROID = "Opera for Android";
    private final char DILIMITER = ' ';

    @Override
    public void match(String userAgent, UserAgentInfo userAgentInfo, int beginIndex) {
        userAgentInfo.setBrowserName(getBrowserName(userAgent));
        beginIndex += BROWSER_KEYWORD_LENGTH;
        if (beginIndex >= userAgent.length())
            return;
        if (userAgent.charAt(beginIndex) == '/') {
            String browserVersion = StringUtils.getNextToken(userAgent, DILIMITER, ++beginIndex, 16, true);
            userAgentInfo.setBrowserVersionName(browserVersion);
        }
    }

    @Override
    public int tryMatch(String userAgent) {
        return userAgent.indexOf(BROWSER_KEYWORD);
    }

    private String getBrowserName(String userAgent) {
        if (userAgent.indexOf("Android") != -1)
            return BROWSER_NAME_FOR_ANDROID;
        else
            return "NULL";
    }
}
