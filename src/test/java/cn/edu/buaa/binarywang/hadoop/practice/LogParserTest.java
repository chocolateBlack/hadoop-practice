package cn.edu.buaa.binarywang.hadoop.practice;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

import cn.edu.buaa.practice.bean.LogRecord;
import cn.edu.buaa.practice.parser.LogParser;

public class LogParserTest {

    @Test
    public void testParse() throws IOException {
        List<String> readLines = Files.readLines(
            new File("src/test/resources/access.log.10"),
            Charset.forName("utf-8"));
        List<String> illegalLogRecords = Lists.newArrayList();
        List<String> robotLines = Lists.newArrayList();

        for (String line : readLines) {
            try {
                Optional<LogRecord> logRecord = LogParser.parse(line);
                if (logRecord.isPresent()) {
                    System.err.println(ToStringBuilder.reflectionToString(
                        logRecord.get(), ToStringStyle.MULTI_LINE_STYLE));
                    System.err.println(logRecord.get().getAccessTime());
                    if (logRecord.get().getRequestUrl().contains("robots.txt")
                        || StringUtils.containsAny(
                            logRecord.get().getHttpUserAgent(),
                            "Sogou web spider", "bingbot", "YandexBot",
                            "msnbot", "YoudaoBot", "DNSPod-Monitor",
                            "Googlebot", "CompSpyBot", "AhrefsBot", "Ezooms",
                            "coccoc", "MJ12bot", "SurveyBot", "360Spider",
                            "ia_archiver", "YisouSpider")) {
                        robotLines.add(line);
                    }
                } else {
                    illegalLogRecords.add(line);
                }
            } catch (Exception e) {
                illegalLogRecords.add(line);
            }

        }

        System.err.println("==============illegal logs================");
        System.err.println("无用日志条数：" + illegalLogRecords.size());
//        System.err.println(Joiner.on("\n").join(illegalLogRecords));

        System.err.println("==============robot logs================");
        System.err.println("爬虫数据已被清洗，在parser程序里");
        System.err.println("爬虫日志条数：" + robotLines.size());
        System.err.println(Joiner.on("\n").join(robotLines));
    }

}
