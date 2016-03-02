package cn.edu.buaa.practice.uamatcher.matchers;

import cn.edu.buaa.practice.uamatcher.Matcher;
import cn.edu.buaa.practice.uamatcher.UserAgentInfo;
import cn.edu.buaa.practice.util.StringUtils;

/**
 * Created by wushang on 15/9/17.
 * Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.152 Safari/537.36 LBBROWSER
 * ios的ua没有猎豹的关键字，无法识别。
 */

public class LieBaoBrowserMatcher implements Matcher {

    private final static String BROWSER_KEY = "LBBROWSER";
    private final static int BROWSER_KEY_LENGTH = BROWSER_KEY.length();
    private final static String BROWSER_NAME = "Liebao";
    private final char DELIMITER = ' ';

    @Override
    public void match(String userAgent, UserAgentInfo userAgentInfo, int beginIndex) {
        userAgentInfo.setBrowserName(BROWSER_NAME);

        //猎豹没有版本，取Chrome版本
        beginIndex = userAgent.indexOf("Chrome");
        if(beginIndex == -1)
            return;
        beginIndex += 6;
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
}
