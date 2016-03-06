package cn.edu.buaa.practice.parser;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;

import cn.edu.buaa.practice.bean.DeviceType;
import cn.edu.buaa.practice.bean.LogRecord;
import cn.edu.buaa.practice.uamatcher.MatcherChain;
import cn.edu.buaa.practice.uamatcher.MatcherChainFactory;
import cn.edu.buaa.practice.uamatcher.UserAgentInfo;

public class LogParser {
	private static MatcherChain BROWSER_MATCHER_CHAIN = MatcherChainFactory.create();
	public static Optional<LogRecord> parse(String value) {
		if (!value.contains("HTTP")) {
			//过滤掉不包含HTTP字样的记录
			return Optional.absent();
		}

		List<String> splitStrings = Arrays.asList(value.split(" "));
//		List<String> splitStrings = Splitter.on(" ").splitToList(value);

		LogRecord record = new LogRecord();
		record.setRemoteAddr(splitStrings.get(0));
		record.setRemoteUser(splitStrings.get(2));
		record.setTimeLocal(splitStrings.get(3).substring(1));
		record.setRequestMethod(splitStrings.get(5).substring(1));
		record.setRequestUrl(splitStrings.get(6));
		record.setNormalizedRequestUri(cn.edu.buaa.practice.util.StringUtils.normalizeUri(record.getRequestUrl()));
		record.setRequestHttpVersion(StringUtils.removeEnd(splitStrings.get(7), "\""));
		
		String request = Joiner.on(" ").join(
				new String[] { record.getRequestMethod(), record.getRequestUrl(), record.getRequestHttpVersion() });
		record.setRequest(request);
		record.setStatus(Integer.valueOf(splitStrings.get(8)));

		record.setBodyBytesSent(Long.valueOf(splitStrings.get(9)));
		record.setHttpReferer(splitStrings.get(10).replace("\"", ""));
		record.setNormalizedReferer(cn.edu.buaa.practice.util.StringUtils.normalizeUri(record.getHttpReferer()));
		
		String userAgent = Joiner.on(" ").join(splitStrings.subList(11, splitStrings.size()));
		userAgent = StringUtils.removeStart(userAgent, "\"");
		userAgent = StringUtils.removeEnd(userAgent, "\"");
		record.setHttpUserAgent(userAgent);

		if(userAgent != null && userAgent.length() > 0) {
			UserAgentInfo userAgentInfo = BROWSER_MATCHER_CHAIN.match(userAgent);
			if(userAgentInfo != null) {
				record.setBrowserName(userAgentInfo.getBrowserName());
				record.setBrowserVersionName(userAgentInfo.getBrowserVersionName());
				record.setDeviceType(userAgentInfo.isMobileDevice() ? DeviceType.MOBILE : DeviceType.PC);
			}
		}
		
        //过滤掉爬虫访问的日志记录
        if (record.getRequestUrl().contains("robots.txt")
            || StringUtils.containsAny(record.getHttpUserAgent(),
                "Sogou web spider", "bingbot", "YandexBot", "msnbot",
                "YoudaoBot", "DNSPod-Monitor", "Googlebot", "CompSpyBot",
                "AhrefsBot", "Ezooms", "coccoc", "MJ12bot", "SurveyBot",
                "360Spider", "ia_archiver", "YisouSpider")) {
            return Optional.absent();
        }

        return Optional.fromNullable(record);
	}

}
