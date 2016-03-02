package cn.edu.buaa.practice.uamatcher.matchers;

import cn.edu.buaa.practice.uamatcher.MatcherChain;
import cn.edu.buaa.practice.uamatcher.MatcherChainFactory;
import cn.edu.buaa.practice.uamatcher.UserAgentInfo;
import org.junit.Test;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import static org.testng.Assert.*;

/**
 * Created by wushang on 15/10/28.
 */
public class WebKitBrowserMatcherTest {

    String uaAndroid = "Lenovo-A880/S100 Linux/3.4.5 Android/4.2 Release/08.07.2013 Browser/AppleWebKit 534.30 Profile/ Configuration";
    String uaIphone = "Mozilla/5.0 (iPhone; iPhone OS 7_0_4 like Mac OS X) AppleWebkit/537.51.1 (KHTML, like Gecko) Mobile/11B554a hao123/042_2.2.2_dapi_420";
    String uaOther = "Mozilla/5.0 (iPad; CPU OS 7_0_4 like Mac OS X) appleWebKit/537.51.1 (KHTML, like Gecko) Mobile/11B554a hao123/042_2.2.2_dapi_420";
    UserAgentInfo userAgentInfo = null;

    @BeforeMethod
    public void setUp() throws Exception {
        userAgentInfo = new UserAgentInfo();
    }

    @Test
    public void testMatchChain() throws Exception {
        MatcherChain matcherChain = MatcherChainFactory.create();
        userAgentInfo = matcherChain.match(uaAndroid);
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "534.30");
        Assert.assertEquals(userAgentInfo.getBrowserName(), "WebKit for Android");

        userAgentInfo = matcherChain.match(uaIphone);
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "537.51.1");
        Assert.assertEquals(userAgentInfo.getBrowserName(), "WebKit for IOS");

        userAgentInfo = matcherChain.match(uaOther);
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "537.51.1");
        Assert.assertEquals(userAgentInfo.getBrowserName(), "WebKit");

    }
}