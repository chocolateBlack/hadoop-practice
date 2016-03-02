package cn.edu.buaa.practice.uamatcher.matchers;

import cn.edu.buaa.practice.uamatcher.MatcherChain;
import cn.edu.buaa.practice.uamatcher.MatcherChainFactory;
import cn.edu.buaa.practice.uamatcher.UserAgentInfo;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by wushang on 15/9/17.
 *
 */
public class SogouMobileBrowserMatcherTest {

    UserAgentInfo userAgentInfo = null;
    SogouMobileBrowserMatcher matcher = null;
    String uaFromIphone = "Mozilla/5.0 (iPhone; CPU iPhone OS 8_3 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Mobile/12F70 SogouMobileBrowser/4.0.5";
    String uaFromAndroid = "Mozilla/5.0 (Linux; U; Android 4.1.2; zh-cn; GT-I9220 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30 SogouMSE,SogouMobileBrowser/3.8.5";
    @BeforeMethod
    public void setUp() throws Exception {
        userAgentInfo = new UserAgentInfo();
        matcher = new SogouMobileBrowserMatcher();
    }

    @Test
    public void testMatch() throws Exception {
        matcher.match(uaFromIphone, userAgentInfo, matcher.tryMatch(uaFromIphone));
        Assert.assertEquals(userAgentInfo.getBrowserName(), "Sogou for IOS");
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "4.0.5");

        matcher.match(uaFromAndroid, userAgentInfo, matcher.tryMatch(uaFromAndroid));
        Assert.assertEquals(userAgentInfo.getBrowserName(), "Sogou for Android");
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "3.8.5");
    }

    @Test
    public void testMatchChain() throws Exception {
        MatcherChain matcherChain = MatcherChainFactory.create();
        userAgentInfo = matcherChain.match(uaFromIphone);

        Assert.assertEquals(userAgentInfo.getBrowserName(), "Sogou for IOS");
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "4.0.5");

        userAgentInfo = matcherChain.match(uaFromAndroid);
        Assert.assertEquals(userAgentInfo.getBrowserName(), "Sogou for Android");
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "3.8.5");


    }
    @Test
    public void testTryMatch() throws Exception {
        Assert.assertNotEquals(matcher.tryMatch(uaFromIphone), -1);
        Assert.assertNotEquals(matcher.tryMatch(uaFromAndroid), -1);
    }
}