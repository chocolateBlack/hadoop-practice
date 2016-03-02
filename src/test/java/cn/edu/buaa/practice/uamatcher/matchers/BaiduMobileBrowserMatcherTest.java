package cn.edu.buaa.practice.uamatcher.matchers;

import cn.edu.buaa.practice.uamatcher.MatcherChain;
import cn.edu.buaa.practice.uamatcher.MatcherChainFactory;
import cn.edu.buaa.practice.uamatcher.UserAgentInfo;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by wushang on 15/10/30.
 */
public class BaiduMobileBrowserMatcherTest {

    UserAgentInfo userAgentInfo = null;
    String uaFromAndroid = "LenovoS868t_TD/1.0 Android 4.0.3 Release/10.01.2012 Browser/WAP2.0 appleWebkit/534.30 baidubrowser/5.3.4.0 (Baidu; P1 4.0.3)";
    String uaFromIOS = "Mozilla/5.0 (iPhone; U; CPU iPhone OS 9_1 like Mac OS X) AppleWebit/601.1.46 (KHTML, like Gecko) Version/9.1 Mobile/13B143 Safari/600.14 baidubrowser/2.9.108 (Baidu; P2 9.1)";
    String uaFromPC = "Mozilla/5.0 (iPhone; U; CPU Mac OS 9_1 like Mac OS X) AppleWebit/601.1.46 (KHTML, like Gecko) Version/9.1 Mobile/13B143 Safari/600.14 baidubrowser/2.9.108 (Baidu; P2 9.1)";

    @BeforeMethod
    public void setUp() throws Exception {
        userAgentInfo = new UserAgentInfo();
    }

    @Test
    public void testMatchChain() throws Exception {
        MatcherChain matcherChain = MatcherChainFactory.create();
        userAgentInfo = matcherChain.match(uaFromAndroid);
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "5.3.4.0");
        Assert.assertEquals(userAgentInfo.getBrowserName(), "Baidu for Android");

        userAgentInfo = matcherChain.match(uaFromIOS);
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "2.9.108");
        Assert.assertEquals(userAgentInfo.getBrowserName(), "Baidu for IOS");

        userAgentInfo = matcherChain.match(uaFromPC);
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "2.9.108");
        Assert.assertEquals(userAgentInfo.getBrowserName(), "BaiduBrowser");
    }
}