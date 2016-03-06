package cn.edu.buaa.practice.uamatcher.matchers;

import cn.edu.buaa.practice.uamatcher.Matcher;
import cn.edu.buaa.practice.uamatcher.UserAgentInfo;
import cn.edu.buaa.practice.util.StringUtils;

/**
 * Created by bingwang on 16/2/21.
 */
public class SogouMobileBrowserMatcher implements Matcher {

    private final static String BROWSER_KEY = "SogouMobileBrowser";
    private final static int BROWSER_KEY_LENTH = BROWSER_KEY.length();
    private final static String BROWSER_NAME_FOR_ANDROID = "Sogou for Android";
    private final static String BROWSER_NAME_FOR_IOS = "Sogou for IOS";
    private final char DELIMITER = ' ';

    @Override
    public void match(String userAgent, UserAgentInfo userAgentInfo, int beginIndex) {
        userAgentInfo.setBrowserName(getBrowserName(userAgent));
        beginIndex += BROWSER_KEY_LENTH;
        if (beginIndex >= userAgent.length())
            return;
        if (userAgent.charAt(beginIndex) == '/') {
            String browserVersion = StringUtils.getNextToken(userAgent, DELIMITER, ++beginIndex, 16, true);
            userAgentInfo.setBrowserVersionName(browserVersion);
        }
    }

    @Override
    public int tryMatch(String userAgent) {
        return userAgent.indexOf(BROWSER_KEY);
    }

    private String getBrowserName(String userAgent) {
        if (userAgent.indexOf("iPhone") != -1)
            return BROWSER_NAME_FOR_IOS;
        else if (userAgent.indexOf("Android") != -1)
            return BROWSER_NAME_FOR_ANDROID;
        else
            return BROWSER_KEY;
    }
}
