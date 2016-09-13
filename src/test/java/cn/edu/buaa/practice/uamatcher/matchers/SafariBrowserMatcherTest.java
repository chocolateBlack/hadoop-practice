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
public class SafariBrowserMatcherTest {

    SafariBrowserMatcher matcher = null;
    UserAgentInfo userAgentInfo = null;
    String uaFromIOS = "Mozilla/5.0 (iPhone; CPU iPhone OS 8_3 like Mac OS X) AppleWebKit/600.1.4.15.16 (KHTML, like Gecko) Version/6.0 Mobile/10A523 Safari/8536.25";
    String uaFromAndROID = "Mozilla/5.0 (Linux;U;Android 4.1.2; zh-cn;) Version/4.0 Mobile Safari/535.19";
    String uaFromAndROID2 = "Mozilla/5.0 (Linux;U;Android 4.1.2; zh-cn;) Mobile Safari/535.19;";
    String uaFromPc = "Mozilla/5.0 (Windows; U; Windows NT 5.2) AppleWebKit/525.13 (KHTML, like Gecko) Version/3.1 Safari/525.13";
    @BeforeMethod
    public void setUp() throws Exception {
        userAgentInfo = new UserAgentInfo();
        matcher = new SafariBrowserMatcher();

    }

    @Test
    public void testMatch() throws Exception {
        matcher.match(uaFromIOS, userAgentInfo, matcher.tryMatch(uaFromIOS));
        Assert.assertEquals(userAgentInfo.getBrowserName(), "Safari for IOS");
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "6.0");

        matcher.match(uaFromAndROID, userAgentInfo, matcher.tryMatch(uaFromAndROID));
        Assert.assertEquals(userAgentInfo.getBrowserName(), "Safari for Android");
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "4.0");

        matcher.match(uaFromPc, userAgentInfo, matcher.tryMatch(uaFromPc));
        Assert.assertEquals(userAgentInfo.getBrowserName(), "Safari");
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "3.1");

    }
    
    @Test
    public void testMatchChain() throws Exception {
    	MatcherChain matcherChain = MatcherChainFactory.create();
    	userAgentInfo = matcherChain.match(uaFromIOS);
        Assert.assertEquals(userAgentInfo.getBrowserName(), "Safari for IOS");
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "6.0");

        userAgentInfo = matcherChain.match(uaFromAndROID);
        Assert.assertEquals(userAgentInfo.getBrowserName(), "Safari for Android");
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "4.0");

        userAgentInfo = matcherChain.match(uaFromPc);
        Assert.assertEquals(userAgentInfo.getBrowserName(), "Safari");
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "3.1");


        userAgentInfo = matcherChain.match(uaFromAndROID2);
        Assert.assertEquals(userAgentInfo.getBrowserName(), "Safari for Android");
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "535.19");

    }

    @Test
    public void testTryMatch() throws Exception {
        Assert.assertNotEquals(matcher.tryMatch(uaFromIOS), -1);
        Assert.assertNotEquals(matcher.tryMatch(uaFromAndROID), -1);
        Assert.assertNotEquals(matcher.tryMatch(uaFromPc), -1);
    }
}