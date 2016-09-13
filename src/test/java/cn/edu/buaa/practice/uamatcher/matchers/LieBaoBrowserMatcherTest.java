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
public class LieBaoBrowserMatcherTest {

    LieBaoBrowserMatcher matcher = null;
    UserAgentInfo userAgentInfo = null;
	MatcherChain matcherChain;
	
    String ua = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.152 Safari/537.36 LBBROWSER";
    @BeforeMethod
    public void setUp() throws Exception {
        matcher = new LieBaoBrowserMatcher();
        userAgentInfo = new UserAgentInfo();
        matcherChain = MatcherChainFactory.create();
    }

    @Test
    public void testMatch() throws Exception {
        matcher.match(ua, userAgentInfo, matcher.tryMatch(ua));
        Assert.assertEquals(userAgentInfo.getBrowserName(), "Liebao");
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "42.0.2311.152");
    }

    @Test
    public void testMatchChain() throws Exception {
    	userAgentInfo = matcherChain.match(ua);
        Assert.assertEquals(userAgentInfo.getBrowserName(), "Liebao");
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "42.0.2311.152");
    }
    
    @Test
    public void testTryMatch() throws Exception {
        Assert.assertNotEquals(-1, matcher.tryMatch(ua));
    }
}