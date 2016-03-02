package cn.edu.buaa.practice.uamatcher.matchers;

import cn.edu.buaa.practice.uamatcher.Matcher;
import cn.edu.buaa.practice.uamatcher.UserAgentInfo;
import cn.edu.buaa.practice.util.StringUtils;

/**
 * Created by wushang on 15/9/17.
 * Mozilla/5.0 (Linux;U;Android 4.1.2; zh-cn;) Version/4.0 LieBaoFast/3.19 Mobile Safari/535.19
 * ios的ua没有猎豹的关键字，无法识别。
 */
public class LieBaoMobileBrowserMatcher implements Matcher {

    private final static String BROWSER_KEY = "LieBaoFast";
    private final static int BROWSER_KEY_LENTH = BROWSER_KEY.length();
    private final static String BROWSER_NAME_FOR_ANDROID = "Liebao for Android";
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
        if (userAgent.indexOf("Android") != -1)
            return BROWSER_NAME_FOR_ANDROID;
        else
            return BROWSER_KEY;
    }
}
