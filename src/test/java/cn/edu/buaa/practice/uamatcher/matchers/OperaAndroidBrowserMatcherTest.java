package cn.edu.buaa.practice.uamatcher.matchers;

import cn.edu.buaa.practice.uamatcher.MatcherChain;
import cn.edu.buaa.practice.uamatcher.MatcherChainFactory;
import cn.edu.buaa.practice.uamatcher.UserAgentInfo;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by wushang on 15/9/17.
 */
public class OperaAndroidBrowserMatcherTest {

    OperaAndroidBrowserMatcher matcher = null;
    UserAgentInfo userAgentInfo = null;
    String ua = "Mozilla/5.0 (Linux; U; Android 4.1.2; zh-CN; GT-I9220 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 OPR/10.5.0.94402 Mobile Safari/534.30";
    @BeforeMethod
    public void setUp() throws Exception {
        userAgentInfo = new UserAgentInfo();
        matcher = new OperaAndroidBrowserMatcher();
    }

    @Test
    public void testMatch() throws Exception {
        matcher.match(ua, userAgentInfo, matcher.tryMatch(ua));
        Assert.assertEquals(userAgentInfo.getBrowserName(), "Opera for Android");
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "10.5.0.94402");
    }

    @Test
    public void  testMatchChain() throws Exception {
        MatcherChain matcherChain = MatcherChainFactory.create();
        userAgentInfo = matcherChain.match(ua);
        Assert.assertEquals(userAgentInfo.getBrowserName(), "Opera for Android");
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "10.5.0.94402");
    }

    @Test
    public void testTryMatch() throws Exception {
        Assert.assertNotEquals(matcher.tryMatch(ua), -1);
    }
}