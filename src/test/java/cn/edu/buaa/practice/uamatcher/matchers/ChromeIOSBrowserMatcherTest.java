package cn.edu.buaa.practice.uamatcher.matchers;

import cn.edu.buaa.practice.uamatcher.MatcherChain;
import cn.edu.buaa.practice.uamatcher.MatcherChainFactory;
import cn.edu.buaa.practice.uamatcher.UserAgentInfo;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by wushang on 15/9/17.
 */
public class ChromeIOSBrowserMatcherTest {

    UserAgentInfo userAgentInfo = null;
    String ua = "Mozilla/5.0 (iPhone; CPU iPhone OS 8_3 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) CriOS/45.0.2454.89 Mobile/12F70 Safari/600.1.4";

    @Test
    public void testMatchChain() throws Exception {
        MatcherChain matcherChain = MatcherChainFactory.create();
        userAgentInfo = matcherChain.match(ua);
        Assert.assertEquals(userAgentInfo.getBrowserName(), "Chrome for IOS");
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "45.0.2454.89");
    }
}