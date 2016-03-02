/**
 * 
 */
package cn.edu.buaa.practice.uamatcher;

/**
 * @author BurningIce
 *
 */
public interface Matcher {
	/**
	 * try match to check whether the matcher supports the user agent
	 * @param userAgent user agent string
	 * @return returns negative value (e.g., -1) for failure, others for success.
	 * 			usually the index of last trying, useful for later match.
	 */
	public int tryMatch(String userAgent);
	/**
	 * 
	 * @param userAgent user agent
	 * @param userAgentInfo
	 * @param tryMatchResult the result returned from last tryMatch
	 */
	public void match(String userAgent, UserAgentInfo userAgentInfo, int tryMatchResult);
}
