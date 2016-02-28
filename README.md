# hadoop-practice


本项目 目前只要存放 nginx日志解析工具类：

调用方法参考单元测试类：/src/test/java/cn/edu/buaa/binarywang/hadoop/practice/LogParserTest.java


		List<String> readLines = Files.readLines(new File("src/test/resources/access.log.10"),
				Charset.forName("utf-8"));
		for (String line : readLines) {
			try {
				Optional<LogRecord> logRecord = LogParser.parse(line);
				if (logRecord.isPresent()) {
					System.err.println(
							ToStringBuilder.reflectionToString(logRecord.get(), ToStringStyle.MULTI_LINE_STYLE));
					System.err.println(logRecord.get().getAccessTime());
				}
			} catch (Exception e) {
				System.err.println(line);
			}

		}