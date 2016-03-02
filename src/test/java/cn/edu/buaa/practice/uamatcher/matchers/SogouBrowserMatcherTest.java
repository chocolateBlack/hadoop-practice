package cn.edu.buaa.practice.uamatcher.matchers;

import cn.edu.buaa.practice.uamatcher.MatcherChain;
import cn.edu.buaa.practice.uamatcher.MatcherChainFactory;
import cn.edu.buaa.practice.uamatcher.UserAgentInfo;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class SogouBrowserMatcherTest {

    UserAgentInfo userAgentInfo = null;
    SogouBrowserMatcher matcher = null;
    String ua = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36 SE 2.X MetaSr 1.0";

    @BeforeMethod
    public void setUp() throws Exception {
        matcher = new SogouBrowserMatcher();
        userAgentInfo = new UserAgentInfo();
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testMatch() throws Exception {
        matcher.match(ua, userAgentInfo, matcher.tryMatch(ua));
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "2.X MetaSr 1.0");

    }
    
    @Test
    public void testMatchChain() throws Exception {
    	MatcherChain matcherChain = MatcherChainFactory.create();
    	userAgentInfo = matcherChain.match(ua);
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "2.X MetaSr 1.0");

    }

    @Test
    public void testTryMatch() throws Exception {
        Assert.assertNotEquals(matcher.tryMatch(ua), -1);
    }
}