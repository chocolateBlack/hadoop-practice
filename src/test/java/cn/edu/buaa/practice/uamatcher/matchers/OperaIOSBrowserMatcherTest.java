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
 */
public class OperaIOSBrowserMatcherTest {

    OperaIOSBrowserMatcher matcher = null;
    UserAgentInfo userAgentInfo = null;
    String ua = "Mozilla/5.0 (iPhone; CPU iPhone OS 8_3 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) OPiOS/10.2.2.95391 Version/7.0 Mobile/12F70 Safari/9537.53";
    @BeforeMethod
    public void setUp() throws Exception {
        userAgentInfo = new UserAgentInfo();
        matcher = new OperaIOSBrowserMatcher();
    }

    @Test
    public void testMatch() throws Exception {
        matcher.match(ua, userAgentInfo, matcher.tryMatch(ua));
        Assert.assertEquals(userAgentInfo.getBrowserName(), "Opera for IOS");
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "10.2.2.95391");
    }

    @Test
    public void  testMatchChain() throws Exception {
        MatcherChain matcherChain = MatcherChainFactory.create();
        userAgentInfo = matcherChain.match(ua);
        Assert.assertEquals(userAgentInfo.getBrowserName(), "Opera for IOS");
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "10.2.2.95391");

    }

    @Test
    public void testTryMatch() throws Exception {
        Assert.assertNotEquals(matcher.tryMatch(ua), -1);

    }
}