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
 * ios的ua没有猎豹的关键字，无法识别。
 */
public class LieBaoMobileBrowserMatcherTest {

    LieBaoMobileBrowserMatcher matcher = null;
    UserAgentInfo userAgentInfo = null;
    String ua = "Mozilla/5.0 (Linux; U; Android 4.1.2; zh-cn; GT-I9220 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30 LieBaoFast/3.19.4";

    @BeforeMethod
    public void setUp() throws Exception {
        matcher = new LieBaoMobileBrowserMatcher();
        userAgentInfo = new UserAgentInfo();
    }

    @Test
    public void testMatch() throws Exception {
        matcher.match(ua, userAgentInfo, matcher.tryMatch(ua));
        Assert.assertEquals(userAgentInfo.getBrowserName(), "Liebao for Android");
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "3.19.4");
    }
    
    @Test
    public void testMatchChain() throws Exception {
    	MatcherChain matcherChain = MatcherChainFactory.create();
    	userAgentInfo = matcherChain.match(ua);
        Assert.assertEquals(userAgentInfo.getBrowserName(), "Liebao for Android");
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "3.19.4");
    }


    @Test
    public void testTryMatch() throws Exception {
        Assert.assertNotEquals(matcher.tryMatch(ua), -1);

    }
}