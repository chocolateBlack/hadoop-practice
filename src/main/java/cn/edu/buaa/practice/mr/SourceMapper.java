package cn.edu.buaa.practice.mr;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Mapper;

import cn.edu.buaa.practice.bean.LogRecord;
import cn.edu.buaa.practice.parser.LogParser;

import com.google.common.base.Optional;

public class SourceMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

	private final IntWritable one = new IntWritable(1);
	private Text referer = new Text();

	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		Optional<LogRecord> logRecord = LogParser.parse(line);

		if (logRecord.isPresent()) {
			LogRecord log = logRecord.get();
			referer.set(log.getNormalizedReferer());
			context.write(referer, one);
		} else {
			Counter count = context.getCounter("LogRecord_Parser",
					"logRecord_Value_Is_Absent");
			count.increment(1);
		}
	}
}