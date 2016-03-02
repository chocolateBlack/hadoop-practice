/**
 * 
 */
package cn.edu.buaa.practice.uamatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * @author BurningIce
 *
 */
public class MatcherChain {	
	private List<Matcher> osMatchers;
	private List<Matcher> browserMatchers;
	private List<Matcher> deviceMatchers;
	private List<Matcher> wildMatchers;
	
	public MatcherChain() {
		this.osMatchers = new ArrayList<Matcher>(16);
		this.browserMatchers = new ArrayList<Matcher>(32);
		this.deviceMatchers = new ArrayList<Matcher>(32);
		this.wildMatchers = new ArrayList<Matcher>(16);
	}
	
	public MatcherChain(List<Matcher> osMatchers, List<Matcher> browserMatchers, List<Matcher> deviceMatchers, List<Matcher> wildMatchers) {
		this.osMatchers = new ArrayList<Matcher>(16);
		this.browserMatchers = new ArrayList<Matcher>(32);
		this.deviceMatchers = new ArrayList<Matcher>(32);
		this.wildMatchers = new ArrayList<Matcher>(16);
		
		if(osMatchers != null) {
			this.osMatchers.addAll(osMatchers);
		}
		
		if(browserMatchers != null) {
			this.browserMatchers.addAll(browserMatchers);
		}
		
		if(deviceMatchers != null) {
			this.deviceMatchers.addAll(deviceMatchers);
		}
		
		if(wildMatchers != null) {
			this.wildMatchers.addAll(wildMatchers);
		}
	}
	
	public void addOsMatcher(Matcher matcher) {
		this.osMatchers.add(matcher);
	}
	
	public void addBrowserMatcher(Matcher matcher) {
		this.browserMatchers.add(matcher);
	}
	
	public void addDeviceMatcher(Matcher matcher) {
		this.deviceMatchers.add(matcher);
	}
	
	public void add(Matcher matcher) {
		this.wildMatchers.add(matcher);
	}
	
	public UserAgentInfo match(String userAgent) {
		return match(userAgent, null);
	}
	
	public UserAgentInfo match(String userAgent, UserAgentInfo userAgentInfo) {
		if(userAgentInfo == null) {
			userAgentInfo = new UserAgentInfo();
		}
		
		int tryResult;
		int length = this.osMatchers.size();
		for(int i = 0; i < length; ++i) {
			if(userAgentInfo.getOsName() != null) {
				// match finished, break anyway
				break;
			}

			Matcher matcher = this.osMatchers.get(i);			
			if((tryResult = matcher.tryMatch(userAgent)) >= 0) {
				matcher.match(userAgent, userAgentInfo, tryResult);
			}
		}
		
		length = this.browserMatchers.size();
		for(int i = 0; i < length; ++i) {
			if(userAgentInfo.getBrowserName() != null) {
				// match finished, break anyway
				break;
			}

			Matcher matcher = this.browserMatchers.get(i);
			if((tryResult = matcher.tryMatch(userAgent)) >= 0) {
				matcher.match(userAgent, userAgentInfo, tryResult);
			}
		}
		
		length = this.deviceMatchers.size();
		for(int i = 0; i < length; ++i) {
			if(userAgentInfo.getBrandName() != null) {
				// match finished, break anyway
				break;
			}

			Matcher matcher = this.deviceMatchers.get(i);
			if((tryResult = matcher.tryMatch(userAgent)) >= 0) {
				matcher.match(userAgent, userAgentInfo, tryResult);
			}
		}
		
		length = this.wildMatchers.size();
		for(int i = 0; i < length; ++i) {
			if(!(userAgentInfo.getOsName() == null || userAgentInfo.getBrowserName() == null || userAgentInfo.getBrandName() == null)) {
				// match finished, break anyway
				break;
			}

			Matcher matcher = this.wildMatchers.get(i);
			if((tryResult = matcher.tryMatch(userAgent)) >= 0) {
				matcher.match(userAgent, userAgentInfo, tryResult);
			}
		}
		
		return userAgentInfo;
	}
}
