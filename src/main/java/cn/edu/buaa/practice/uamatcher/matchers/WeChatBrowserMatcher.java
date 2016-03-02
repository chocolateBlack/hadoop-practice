package cn.edu.buaa.practice.uamatcher.matchers;

import cn.edu.buaa.practice.uamatcher.Matcher;
import cn.edu.buaa.practice.uamatcher.UserAgentInfo;
import cn.edu.buaa.practice.util.StringUtils;

/*
 *Android:Mozilla/5.0 (Linux; Android 4.4.4; HM 2A Build/KTU84Q) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/33.0.0.0 Mobile Safari/537.36 MicroMessenger/6.2.5.51_rfe7d7c5.621 NetType/cmnet Language/zh_CN
 *ios:iPhone: Mozilla/5.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Mobile/12A365 MicroMessenger/5.4.1 NetType/WIFI
 *PC:Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36 MicroMessenger/6.5.2.501 NetType/WIFI WindowsWechat
 */
public class WeChatBrowserMatcher implements Matcher {
    private final static String BROWSER_NAME = "MicroMessenger";
    private final static int BROWSER_LENTH = BROWSER_NAME.length();
    private final char[] DILIMITERS = new char[]{' ','_'};

    @Override
    public void match(String userAgent, UserAgentInfo userAgentInfo, int beginIndex) {
        userAgentInfo.setBrowserName(getBrowserName(userAgent));
        beginIndex += BROWSER_LENTH;
        if (beginIndex >= userAgent.length())
            return;
        if (userAgent.charAt(beginIndex) == '/') {
            String browserVersion = StringUtils.getNextToken(userAgent, DILIMITERS, ++beginIndex, 16, true);
            userAgentInfo.setBrowserVersionName(browserVersion);
        }
    }

    @Override
    public int tryMatch(String userAgent) {
        return userAgent.indexOf(BROWSER_NAME);
    }

    private String getBrowserName(String userAgent) {
        String browserName = "";
        if (userAgent.indexOf("iPhone") != -1) {
            browserName = "WeChat for IOS";
        } else if(userAgent.indexOf("Android") != -1) {
            browserName = "WeChat for Android";
        } else {
            browserName = "WeChat for Windows";
        }
        return browserName;
    }
}
