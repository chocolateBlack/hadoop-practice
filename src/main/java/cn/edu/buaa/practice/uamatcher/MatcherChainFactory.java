/**
 * 
 */
package cn.edu.buaa.practice.uamatcher;

import cn.edu.buaa.practice.uamatcher.matchers.*;

/**
 * @author BurningIce
 */
public class MatcherChainFactory {
    public static MatcherChain create() {
        return new MatcherChainBuilder()
                .addOsMatcher(new RedHatOSMatcher())
                .addOsMatcher(new CentOSMatcher())
                .addOsMatcher(new IphoneOSMatcher())
                .addOsMatcher(new AndroidOSMatcher())
                .addBrowserMatcher(new FirefoxBrowserMatcher())
                .addBrowserMatcher(new WeChatBrowserMatcher())
                .addBrowserMatcher(new OperaMiniBrowserMatcher())
                .addBrowserMatcher(new OperaBrowserMatcher())
                .addBrowserMatcher(new UcwebBrowserMatcher())
                .addBrowserMatcher(new EdgeBrowserMatcher())
                .addBrowserMatcher(new QQForAndroidBrowserMatcher())
                .addBrowserMatcher(new QQForIosBrowserMatcher())
                .addBrowserMatcher(new QQBrowserMatcher())
                .addBrowserMatcher(new SogouBrowserMatcher())
                .addBrowserMatcher(new UCMobileBrowserMatcher())
                .addBrowserMatcher(new SogouMobileBrowserMatcher())
                .addBrowserMatcher(new OperaAndroidBrowserMatcher())
                .addBrowserMatcher(new OperaIOSBrowserMatcher())
                .addBrowserMatcher(new LieBaoBrowserMatcher())
                .addBrowserMatcher(new LieBaoMobileBrowserMatcher())
                .addBrowserMatcher(new ChromeIOSBrowserMatcher())
                .addBrowserMatcher(new BaiduMobileBrowserMatcher())
                .addBrowserMatcher(new ChromeBrowserMatcher())
                .addBrowserMatcher(new SafariBrowserMatcher())
                .addBrowserMatcher(new BotMatcher())
                .addBrowserMatcher(new DefaultBrowserMatcher("GreenBrowser", "GreenBrowser"))
                .addBrowserMatcher(new DefaultBrowserMatcher("MyIE", "MyIE"))
                .addBrowserMatcher(new IE11BrowserMatcher())
                .addBrowserMatcher(new WebKitBrowserMatcher())

                .build();
    }
}
