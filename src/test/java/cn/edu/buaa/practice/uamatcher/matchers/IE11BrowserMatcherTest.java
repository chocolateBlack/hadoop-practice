package cn.edu.buaa.practice.uamatcher.matchers;

import cn.edu.buaa.practice.uamatcher.MatcherChain;
import cn.edu.buaa.practice.uamatcher.MatcherChainFactory;
import cn.edu.buaa.practice.uamatcher.UserAgentInfo;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by wushang on 15/10/9.
 */
public class IE11BrowserMatcherTest {

    IE11BrowserMatcher matcher = null;
    UserAgentInfo userAgentInfo = null;
    MatcherChain matcherChain;
    String ua = "Mozilla/5.0 (Windows NT 6.1; Trident/7.0; rv:11.0; NetworkBench/6.0.0.136-3867353-2059837) like Gecko";

    @BeforeMethod
    public void setUp() throws Exception {
        matcher = new IE11BrowserMatcher();
        userAgentInfo = new UserAgentInfo();
        matcherChain = MatcherChainFactory.create();
    }

    @Test
    public void testMatchChain() throws Exception {
        userAgentInfo = matcherChain.match(ua);
        Assert.assertEquals(userAgentInfo.getBrowserName(), "Internet Explorer");
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "11.0");
    }

    @Test
    public void testTryMatch() throws Exception {
        Assert.assertNotEquals(-1, matcher.tryMatch(ua));
    }
}