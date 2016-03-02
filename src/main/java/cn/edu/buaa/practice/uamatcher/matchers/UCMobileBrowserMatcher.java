package cn.edu.buaa.practice.uamatcher.matchers;

import cn.edu.buaa.practice.uamatcher.Matcher;
import cn.edu.buaa.practice.uamatcher.UserAgentInfo;
import cn.edu.buaa.practice.util.StringUtils;

/**
 * Created by wushang on 15/9/17.
 * Mozilla/5.0 (Linux; U; Android 4.4.4; zh-CN; 2014813 Build/KTU84P) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 UCBrowser/10.7.0.634 U3/0.8.0 Mobile Safari/534.30
 * Mozilla/5.0 (iPhone; CPU iPhone OS 9_0 like Mac OS X; zh-CN) AppleWebKit/537.51.1 (KHTML, like Gecko) CriOS/23.0.1271.100 Mobile/13A344 Safari/7534.48.3 UCBrowser/10.7.1.655
 * Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.155 UBrowser/5.4.4237.50 Safari/537.36
 */
public class UCMobileBrowserMatcher implements Matcher {

    private final static String BROWSER_KEY = "UCBrowser";
    private final static int BROWSER_LENTH = BROWSER_KEY.length();
    private final static String BROWSER_NAME_FOR_ANDROID = "UC for Android";
    private final static String BROWSER_NAME_FOR_IOS = "UC for IOS";
    private final char DELIMITER = ' ';

    @Override
    public void match(String userAgent, UserAgentInfo userAgentInfo, int beginIndex) {
        userAgentInfo.setBrowserName(getBrowserName(userAgent));
        beginIndex += BROWSER_LENTH;
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
