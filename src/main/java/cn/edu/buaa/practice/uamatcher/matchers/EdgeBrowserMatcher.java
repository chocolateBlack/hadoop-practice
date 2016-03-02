package cn.edu.buaa.practice.uamatcher.matchers;

import cn.edu.buaa.practice.uamatcher.Matcher;
import cn.edu.buaa.practice.uamatcher.UserAgentInfo;
import cn.edu.buaa.practice.util.StringUtils;

/**
 * Created by wushang on 15/11/18.
 */
public class EdgeBrowserMatcher implements Matcher {
    private final static String BROWSER_NAME = "Edge";
    private final static String KEY_WORD = "Edge";
    private final static int KEY_WORD_LENTH = KEY_WORD.length();
    private final char DILIMITER = ' ';

    @Override
    public void match(String userAgent, UserAgentInfo userAgentInfo, int beginIndex) {
        userAgentInfo.setBrowserName(BROWSER_NAME);
        beginIndex += KEY_WORD_LENTH;
        if (beginIndex >= userAgent.length())
            return;
        if (userAgent.charAt(beginIndex) == '/') {
            String browserVersion = StringUtils.getNextToken(userAgent, DILIMITER, ++beginIndex, 16, true);
            userAgentInfo.setBrowserVersionName(browserVersion);
        }
    }

    @Override
    public int tryMatch(String userAgent) {
        return userAgent.indexOf(KEY_WORD);
    }
}
