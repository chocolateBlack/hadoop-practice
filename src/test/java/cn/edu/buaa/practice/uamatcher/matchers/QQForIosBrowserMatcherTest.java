package cn.edu.buaa.practice.uamatcher.matchers;

import cn.edu.buaa.practice.uamatcher.MatcherChain;
import cn.edu.buaa.practice.uamatcher.MatcherChainFactory;
import cn.edu.buaa.practice.uamatcher.UserAgentInfo;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by wushang on 15/10/24.
 */
public class QQForIosBrowserMatcherTest {

    String ua = " Mozilla/5.0 (iPhone; CPU iPhone OS 8_1_3 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Mobile/12B466 QQ/5.4.0.454 NetType/WIFI Mem/12";
    String ua2 = "Mozilla/5.0 (iPad; CPU OS 7_1_1 like Mac OS X) AppleWebKit/537.51.2 (KHTML, like Gecko) Mobile/11D201 IPadQQ/5.4.0.127 QQ/5.4.0";
    UserAgentInfo userAgentInfo = null;

    @BeforeMethod
    public void setUp() throws Exception {
        userAgentInfo = new UserAgentInfo();
    }

    @Test
    public void testMatchChain() throws Exception {
        MatcherChain matcherChain = MatcherChainFactory.create();
        userAgentInfo = matcherChain.match(ua);
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "5.4.0.454");
        Assert.assertEquals(userAgentInfo.getBrowserName(), "QQBrowser for IOS");

        userAgentInfo = matcherChain.match(ua2);
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "5.4.0.127");
        Assert.assertEquals(userAgentInfo.getBrowserName(), "QQBrowser for IOS");


    }
}