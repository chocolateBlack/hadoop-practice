package cn.edu.buaa.practice.uamatcher.matchers;

import cn.edu.buaa.practice.uamatcher.Matcher;
import cn.edu.buaa.practice.uamatcher.UserAgentInfo;

/**
 * User-Agent:Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36 SE 2.X MetaSr 1.0
 */
public class SogouBrowserMatcher implements Matcher {
    private final static String BROWSER_NAME = " SE ";

    @Override
    public void match(String userAgent, UserAgentInfo userAgentInfo, int beginIndex) {
        userAgentInfo.setBrowserName("Sogou");
        beginIndex += 4;
        if (beginIndex >= userAgent.length())
            return;
        userAgentInfo.setBrowserVersionName(userAgent.substring(beginIndex));
    }

    @Override
    public int tryMatch(String userAgent) {
        return userAgent.indexOf(BROWSER_NAME);
    }
}
