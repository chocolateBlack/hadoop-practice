package cn.edu.buaa.practice.uamatcher.matchers;

import cn.edu.buaa.practice.uamatcher.MatcherChain;
import cn.edu.buaa.practice.uamatcher.MatcherChainFactory;
import cn.edu.buaa.practice.uamatcher.UserAgentInfo;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by wushang on 15/9/17.
 */
public class ChromeBrowserMatcherTest {

    String uaFromPc = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36";
    String uaFromAndroid = "Mozilla/5.0 (Linux; Android 5.0; Lenovo K50-t5 Build/LRX21M) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/33.0.0.0 Mobile Safari/537.36";
    UserAgentInfo userAgentInfo = null;

    @Test
    public void testMatchChain() throws Exception {
        MatcherChain matcherChain = MatcherChainFactory.create();
        userAgentInfo = matcherChain.match(uaFromPc);

        Assert.assertEquals(userAgentInfo.getBrowserName(), "Chrome");
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "45.0.2454.85");

        userAgentInfo = matcherChain.match(uaFromAndroid);
        Assert.assertEquals(userAgentInfo.getBrowserName(), "Chrome for Android");
        Assert.assertEquals(userAgentInfo.getBrowserVersionName(), "33.0.0.0");
    }

}