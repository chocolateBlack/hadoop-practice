package cn.edu.buaa.practice.uamatcher.matchers;

import cn.edu.buaa.practice.uamatcher.Matcher;
import cn.edu.buaa.practice.uamatcher.UserAgentInfo;
import cn.edu.buaa.practice.util.StringUtils;

/**
 * Created by wushang on 15/10/23.
 */
public class QQForIosBrowserMatcher implements Matcher {

    private final static String KEY = "QQ";
    private final static String KEY2 = "Mac";
    private final static String BROWSER_NAME_FOR_IOS = "QQBrowser for IOS";
    private final char[] DILIMITERS = new char[]{' ','_'};

    @Override
    public void match(String userAgent, UserAgentInfo userAgentInfo, int beginIndex) {
        userAgentInfo.setBrowserName(BROWSER_NAME_FOR_IOS);
        beginIndex += KEY.length();
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
            if (userAgent.indexOf(KEY2) < 0)
                return -1;
        }
        return index;
    }
}
