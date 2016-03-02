package cn.edu.buaa.practice.uamatcher.matchers;

import cn.edu.buaa.practice.uamatcher.MatcherChain;
import cn.edu.buaa.practice.uamatcher.MatcherChainFactory;
import cn.edu.buaa.practice.uamatcher.UserAgentInfo;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by wushang on 15/9/17.
 */
public class UCMobileBrowserMatcherTest {
    UserAgentInfo userAgentInfo = null;
    UCMobileBrowserMatcher matcher = null;
	MatcherChain matcherChain;
	
    String uaFromIphone = "Mozilla/5.0 (iPhone; CPU iPhone OS 9_0 like Mac OS X; zh-CN) AppleWebKit/537.51.1 (KHTML, like Gecko) CriOS/23.0.1271.100 Mobile/13A344 Safari/7534.48.3 UCBrowser/10.7.1.655";
    String uaFromAndroid = "Mozilla/5.0 (Linux; U; Android 4.4.4; zh-CN; 2014813 Build/KTU84P) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 UCBrowser/10.7.0.634 U3/0.8.0 Mobile Safari/534.30";
    @BeforeMethod
    public void setUp() throws Exception {
        userAgentInfo = new UserAgentInfo();
        matcher = new UCMobileBrowserMatcher();
    }

    @Test
    public void testMatch() throws Exception {
        matcher.match(uaFromIphone, userAgentInfo, matcher.tryMatch(uaFromIphone));
        Assert.assertEquals(userAgentInfo.getBrowserName(), "UC for IOS");
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "10.7.1.655");

        matcher.match(uaFromAndroid, userAgentInfo, matcher.tryMatch(uaFromAndroid));
        Assert.assertEquals(userAgentInfo.getBrowserName(), "UC for Android");
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "10.7.0.634");

    }

    @Test
    public void testMatchChain() throws Exception {
    	MatcherChain matcherChain = MatcherChainFactory.create();
    	
    	userAgentInfo = matcherChain.match(uaFromIphone);
        Assert.assertEquals(userAgentInfo.getBrowserName(), "UC for IOS");
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "10.7.1.655");

        userAgentInfo = matcherChain.match(uaFromAndroid);
        Assert.assertEquals(userAgentInfo.getBrowserName(), "UC for Android");
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "10.7.0.634");

    }
    
    @Test
    public void testTryMatch() throws Exception {
        Assert.assertNotEquals(matcher.tryMatch(uaFromIphone), -1);
        Assert.assertNotEquals(matcher.tryMatch(uaFromAndroid), -1);
    }
}