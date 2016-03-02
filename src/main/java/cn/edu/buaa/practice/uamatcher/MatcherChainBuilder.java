/**
 * 
 */
package cn.edu.buaa.practice.uamatcher;

/**
 * @author BurningIce
 *
 */
public class MatcherChainBuilder {
	private MatcherChain matcherChain;
	
	public MatcherChainBuilder() {
		this.matcherChain = new MatcherChain();
	}
	
	public MatcherChainBuilder addOsMatcher(Matcher matcher) {
		this.matcherChain.addOsMatcher(matcher);
		return this;
	}
	
	public MatcherChainBuilder addBrowserMatcher(Matcher matcher) {
		this.matcherChain.addBrowserMatcher(matcher);
		return this;
	}
	
	public MatcherChainBuilder addDeviceMatcher(Matcher matcher) {
		this.matcherChain.addDeviceMatcher(matcher);
		return this;
	}
	
	public MatcherChainBuilder add(Matcher matcher) {
		this.matcherChain.add(matcher);
		return this;
	}
	
	public MatcherChain build() {
		return this.matcherChain;
	}
}
