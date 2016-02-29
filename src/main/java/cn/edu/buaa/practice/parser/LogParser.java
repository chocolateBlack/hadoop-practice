package cn.edu.buaa.practice.parser;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.edu.buaa.practice.bean.LogRecord;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;

public class LogParser {
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
		record.setRequestHttpVersion(StringUtils.removeEnd(splitStrings.get(7), "\""));

		String request = Joiner.on(" ").join(
				new String[] { record.getRequestMethod(), record.getRequestUrl(), record.getRequestHttpVersion() });
		record.setRequest(request);
		record.setStatus(Integer.valueOf(splitStrings.get(8)));

		record.setBodyBytesSent(Long.valueOf(splitStrings.get(9)));
		record.setHttpReferer(splitStrings.get(10).replace("\"", ""));

		String userAgent = Joiner.on(" ").join(splitStrings.subList(11, splitStrings.size()));
		userAgent = StringUtils.removeStart(userAgent, "\"");
		userAgent = StringUtils.removeEnd(userAgent, "\"");
		record.setHttpUserAgent(userAgent);

		Optional<LogRecord> result = Optional.fromNullable(record);
		return result;
	}

}
