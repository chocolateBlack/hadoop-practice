/**
 * 
 */
package cn.edu.buaa.practice.uamatcher.matchers;

import cn.edu.buaa.practice.uamatcher.Matcher;
import cn.edu.buaa.practice.uamatcher.UserAgentInfo;
import cn.edu.buaa.practice.util.StringUtils;

/**
 * @author BurningIce
 *
 */
public class MSIEBrowserMatcher implements Matcher {
	private final static String BROWSER_NAME = "Internet Explorer";
	private final static String[] KEYWORDS = {"MSIE","IEMobile","msie"};
	private final char[] DILIMITERS = new char[] {' ', ';', ')'};
	private final int KEYWORD_LENGTH = KEYWORDS[0].length();
	private final static String TRIDENT = "Trident/";
	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#match(java.lang.String, cn.edu.buaa.practice.uamatcher.UserAgentInfo, int)
	 */
	public void match(String userAgent, UserAgentInfo userAgentInfo, int beginIndex) {
		//check IEMobile
		int mobBeginIndex = userAgent.indexOf("IEMobile");
		if(mobBeginIndex == -1) {
			userAgentInfo.setBrowserName(BROWSER_NAME);
			beginIndex += KEYWORD_LENGTH;
		} else {
			userAgentInfo.setBrowserName("IE Mobile");
			beginIndex = mobBeginIndex + 8;
		}
		
		if(beginIndex >= userAgent.length())
			return;
		
		char nextChar = userAgent.charAt(beginIndex); 
		if(nextChar == ' ' || nextChar == '/') {
			String browserVersion = StringUtils.getNextToken(userAgent, DILIMITERS, ++beginIndex, 8);
			userAgentInfo.setBrowserVersionName(browserVersion);
		}

		//对ie版本进行修正
		int tridentIndex = userAgent.indexOf(TRIDENT);
		if(mobBeginIndex == -1 && tridentIndex != -1) { //非移动端，且存在“Trident/”
			String tridentVesion = StringUtils.getNextToken(userAgent, DILIMITERS, tridentIndex+8, 8);
			System.out.println(userAgent);
			System.out.println(userAgent.substring(0,tridentIndex) + "#");
			System.out.println("vv=" + tridentVesion);
			if("7.0".equals(tridentVesion)) {
				userAgentInfo.setBrowserVersionName("11");
			}else if("6.0".equals(tridentVesion)) {
				userAgentInfo.setBrowserVersionName("10");
			}else if("5.0".equals(tridentVesion)) {
				userAgentInfo.setBrowserVersionName("9");
			}
		}
	}

	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#tryMatch(java.lang.String)
	 */
	public int tryMatch(String userAgent) {
		return StringUtils.indexOfAny(userAgent, KEYWORDS);
	}
}
