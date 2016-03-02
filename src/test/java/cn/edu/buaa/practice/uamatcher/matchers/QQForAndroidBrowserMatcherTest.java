package cn.edu.buaa.practice.uamatcher.matchers;

import cn.edu.buaa.practice.uamatcher.MatcherChain;
import cn.edu.buaa.practice.uamatcher.MatcherChainFactory;
import cn.edu.buaa.practice.uamatcher.UserAgentInfo;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by wushang on 15/10/23.
 */
public class QQForAndroidBrowserMatcherTest {

    QQForAndroidBrowserMatcher matcher = null;
    String ua = "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-cn; Lenovo K910 Build/KOT49H) AppleWebKit/533.1 (KHTML, like Gecko)Version/4.0 MQQBrowser NetType/3G";
    UserAgentInfo userAgentInfo = null;

    @BeforeMethod
    public void setUp() throws Exception {
        matcher = new QQForAndroidBrowserMatcher();
        userAgentInfo = new UserAgentInfo();
    }

    @Test
    public void testMatchChain() throws Exception {
        MatcherChain matcherChain = MatcherChainFactory.create();
        userAgentInfo = matcherChain.match(ua);
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "4.0");
        Assert.assertEquals(userAgentInfo.getBrowserName(), "QQBrowser for Android");
    }

}