package cn.edu.buaa.binarywang.hadoop.practice;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

import cn.edu.buaa.practice.bean.LogRecord;
import cn.edu.buaa.practice.parser.LogParser;

import com.google.common.base.Optional;
import com.google.common.io.Files;

public class LogParserTest {

	@Test
	public void testParse() throws IOException {
		List<String> readLines = Files.readLines(new File("src/test/resources/access.log.10"),
				Charset.forName("utf-8"));
		int count = 0;
		for (String line : readLines) {
			try {
				Optional<LogRecord> logRecord = LogParser.parse(line);
				if (logRecord.isPresent()) {
					System.err.println(ToStringBuilder.reflectionToString(logRecord.get(), ToStringStyle.MULTI_LINE_STYLE));
					System.err.println(logRecord.get().getAccessTime());
				} else {
					count++;
				}
			} catch (Exception e) {
				System.err.println(line);
			}
		}
		System.out.println(count);
	}
}
