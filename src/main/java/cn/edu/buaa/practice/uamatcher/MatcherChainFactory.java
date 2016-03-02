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
                .addOsMatcher(new WindowsOSMatcher())
                .addOsMatcher(new MacintoshOSMatcher())
                .addOsMatcher(new RedHatOSMatcher())
                .addOsMatcher(new CentOSMatcher())
                .addOsMatcher(new UbuntuOSMatcher())
                .addOsMatcher(new SUSEOSMatcher())
                .addOsMatcher(new FedoraOSMatcher())
                .addOsMatcher(new IphoneOSMatcher())
                .addOsMatcher(new AndroidOSMatcher())
                .addOsMatcher(new SymbianOSMatcher())
                .addOsMatcher(new LinuxOSMatcher())
                .addBrowserMatcher(new MSIEBrowserMatcher())
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
                .addBrowserMatcher(new MaxthonBrowserMatcher())
                .addBrowserMatcher(new BaiduMobileBrowserMatcher())
                .addBrowserMatcher(new ChromeBrowserMatcher())
                .addBrowserMatcher(new SafariBrowserMatcher())
                .addBrowserMatcher(new BotMatcher())
                .addBrowserMatcher(new TencentTravelerBrowserMatcher())
                .addBrowserMatcher(new DefaultBrowserMatcher("GreenBrowser", "GreenBrowser"))
                .addBrowserMatcher(new DefaultBrowserMatcher("MyIE", "MyIE"))
                .addBrowserMatcher(new KonquerorBrowserMatcher())
                .addBrowserMatcher(new MaemoBrowserMatcher())
                .addBrowserMatcher(new IE11BrowserMatcher())
                .addBrowserMatcher(new WebKitBrowserMatcher())
                .addDeviceMatcher(new NokiaDeviceMatcher())
                .addDeviceMatcher(new SamsungDeviceMatcher())
                .addDeviceMatcher(new HTCDeviceMatcher())
                .addDeviceMatcher(new DopodDeviceMatcher())
                .addDeviceMatcher(new BlackBerryDeviceMatcher())
                .addDeviceMatcher(new MotorolaDeviceMatcher())
                .addDeviceMatcher(new SonyEricssonDeviceMatcher())
                .addDeviceMatcher(new LGDeviceMatcher())
                .addDeviceMatcher(new SiemensDeviceMatcher())
                .addDeviceMatcher(new SagemDeviceMatcher())

                .build();
    }
}
