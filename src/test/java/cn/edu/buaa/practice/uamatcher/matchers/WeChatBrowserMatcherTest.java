package cn.edu.buaa.practice.uamatcher.matchers;

import cn.edu.buaa.practice.uamatcher.MatcherChain;
import cn.edu.buaa.practice.uamatcher.MatcherChainFactory;
import cn.edu.buaa.practice.uamatcher.UserAgentInfo;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class WeChatBrowserMatcherTest {

    UserAgentInfo userAgentInfo = null;
    WeChatBrowserMatcher matcher = null;
	MatcherChain matcherChain;
    
    String uaFromIphone = "Mozilla/5.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Mobile/12A365 MicroMessenger/5.4.1 NetType/WIFI";
    String uaFromAndroid = "Mozilla/5.0 (Linux; Android 4.4.4; HM 2A Build/KTU84Q) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/33.0.0.0 Mobile Safari/537.36 MicroMessenger/6.2.5.51_rfe7d7c5.621 NetType/cmnet Language/zh_CN";
    String uaFromPc = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36 MicroMessenger/6.5.2.501 NetType/LTE WindowsWechat";
    @BeforeMethod
    public void setUp() throws Exception {
        matcher = new WeChatBrowserMatcher();
        userAgentInfo = new UserAgentInfo();
        matcherChain = MatcherChainFactory.create();
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testMatch() throws Exception {
        matcher.match(uaFromIphone, userAgentInfo, matcher.tryMatch(uaFromIphone));
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "5.4.1");
        Assert.assertEquals(userAgentInfo.getBrowserName(), "WeChat for IOS");
        
        matcher.match(uaFromAndroid, userAgentInfo, matcher.tryMatch(uaFromAndroid));
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "6.2.5.51");
        Assert.assertEquals(userAgentInfo.getBrowserName(), "WeChat for Android");
        
        matcher.match(uaFromPc, userAgentInfo, matcher.tryMatch(uaFromPc));
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "6.5.2.501");
        Assert.assertEquals(userAgentInfo.getBrowserName(), "WeChat for Windows");
    }

    @Test
    public void testMatchChain() throws Exception {
    	UserAgentInfo uaInfo = matcherChain.match(uaFromIphone);
        Assert.assertEquals(uaInfo.getBrowserName(), "WeChat for IOS");
        Assert.assertEquals(uaInfo.getBrowserVersionName(), "5.4.1");


        uaInfo = matcherChain.match(uaFromAndroid);
        Assert.assertEquals(uaInfo.getBrowserName(), "WeChat for Android");
        Assert.assertEquals(uaInfo.getBrowserVersionName(), "6.2.5.51");


        uaInfo = matcherChain.match(uaFromPc);
        Assert.assertEquals(uaInfo.getBrowserName(), "WeChat for Windows");
        Assert.assertEquals(uaInfo.getBrowserVersionName(), "6.5.2.501");

    }

    @Test
    public void testTryMatch() throws Exception {
        Assert.assertNotEquals(matcher.tryMatch(uaFromIphone), -1);
        Assert.assertNotEquals(matcher.tryMatch(uaFromAndroid), -1);
        Assert.assertNotEquals(matcher.tryMatch(uaFromPc), -1);

    }

}