package cn.edu.buaa.practice.uamatcher.matchers;

import cn.edu.buaa.practice.uamatcher.Matcher;
import cn.edu.buaa.practice.uamatcher.UserAgentInfo;
import cn.edu.buaa.practice.util.StringUtils;

/**
 * Created by wushang on 15/10/9.
 */
public class IE11BrowserMatcher implements Matcher {

    private final static String KEYWORD1 = "Trident/7.0";
    private final static String KEYWORD2 = "rv";
    private final static String BROWSER_NAME = "Internet Explorer";
    private final char[] DELIMITERS = {' ', ')', ';'};

    @Override
    public void match(String userAgent, UserAgentInfo userAgentInfo, int beginIndex) {
        userAgentInfo.setBrowserName(BROWSER_NAME);
        beginIndex+=KEYWORD2.length();
        if (beginIndex >= userAgent.length())
            return;
        if (userAgent.charAt(beginIndex) == ':') {
            String browserVersion = StringUtils.getNextToken(userAgent, DELIMITERS, ++beginIndex, 16, true);
            userAgentInfo.setBrowserVersionName(browserVersion);
        }
    }

    @Override
    public int tryMatch(String userAgent) {
        int index =  userAgent.indexOf(KEYWORD1);
        if(index > 0) {
            return userAgent.indexOf(KEYWORD2);
        }
        return index;
    }
}
