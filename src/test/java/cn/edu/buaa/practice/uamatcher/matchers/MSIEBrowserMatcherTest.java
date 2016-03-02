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
 * Created by wushang on 15/10/9.
 */
public class MSIEBrowserMatcherTest {

    MSIEBrowserMatcher matcher = null;
    UserAgentInfo userAgentInfo = null;
    String ua = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET CLR 2.0.50727; NetworkBench/6.0.0.136-3615397-2059837)";
    String uaMobile = " Mozilla/5.0 (Windows Phone 8.1; ARM; Trident/8.0; Touch; rv:11.0; WebBrowser/8.1; IEMobile/11.0; NOKIA; Nokia 920) like Gecko";

    String uaTest = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.3; WOW64; Trident/5.0; .NET4.0E; .NET4.0C; .NET CLR 3.5.30729; .NET CLR 2.0.50727; .NET CLR 3.0.30729; GWX:DOWNLOADED)";
    @BeforeMethod
    public void setUp() throws Exception {
        userAgentInfo = new UserAgentInfo();
        matcher = new MSIEBrowserMatcher();
    }

    @Test
    public void testMatchChain() throws Exception {
        MatcherChain matcherChain = MatcherChainFactory.create();
        userAgentInfo = matcherChain.match(ua);
        Assert.assertEquals(userAgentInfo.getBrowserName(), "Internet Explorer");
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "8.0");

        userAgentInfo = matcherChain.match(uaMobile);
        Assert.assertEquals(userAgentInfo.getBrowserName(), "IE Mobile");
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "11.0");

        userAgentInfo = matcherChain.match(uaTest);
        Assert.assertEquals(userAgentInfo.getBrowserName(), "Internet Explorer");
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "9");
    }

    @Test
    public void testTryMatch() throws Exception {

    }
}