package cn.edu.buaa.practice.uamatcher.matchers;

import cn.edu.buaa.practice.uamatcher.Matcher;
import cn.edu.buaa.practice.uamatcher.UserAgentInfo;
import cn.edu.buaa.practice.util.StringUtils;

/**
 * Created by wushang on 15/10/23.
 */
public class QQForAndroidBrowserMatcher implements Matcher {
    private final static String KEY = "QQBrowse";
    private final static String KEY2 = "Android";
    private final static String KEY3 = "Version";
    private final int KEY_LENGTH = KEY3.length();
    private final char[] DILIMITERS = new char[]{' ', '_'};
    private final static String BROWSER_NAME_FOR_ANDROID = "QQBrowser for Android";

    private final static String[] NET_TYPE_2G_KEYWORDS = new String[]{
            "cmnet",
            "cmwap",
            "uninet",
            "uniwap",
            "ctnet",
            "ctwap"
    };

    private final static String[] NET_TYPE_3G_KEYWORDS = new String[]{
            "3gnet",
            "3gwap",
            "LTE"
    };

    @Override
    public void match(String userAgent, UserAgentInfo userAgentInfo, int beginIndex) {
        userAgentInfo.setBrowserName(BROWSER_NAME_FOR_ANDROID);
        beginIndex += KEY_LENGTH;
        if (beginIndex >= userAgent.length())
            return;
        if (userAgent.charAt(beginIndex) == '/') {
            String browserVersion = StringUtils.getNextToken(userAgent, DILIMITERS, ++beginIndex, 16, true);
            userAgentInfo.setBrowserVersionName(browserVersion);
        }
    }

    @Override
    public int tryMatch(String userAgent) {
        int index = userAgent.indexOf(KEY);
        if (index >= 0) {
            if (userAgent.indexOf(KEY2) >= 0)
                return userAgent.indexOf(KEY3);
        }
        return -1;
    }
}
