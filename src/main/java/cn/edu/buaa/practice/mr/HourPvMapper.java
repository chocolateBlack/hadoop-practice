package cn.edu.buaa.practice.mr;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Mapper;

import cn.edu.buaa.practice.bean.LogRecord;
import cn.edu.buaa.practice.bean.TextTextPair;
import cn.edu.buaa.practice.parser.LogParser;

import com.google.common.base.Optional;

public class HourPvMapper extends Mapper<LongWritable, Text, TextTextPair, IntWritable> {

	private final IntWritable one = new IntWritable(1);
	private TextTextPair pair = new TextTextPair();
	DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 

	@SuppressWarnings("deprecation")
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		Optional<LogRecord> logRecord = LogParser.parse(line);

		if (logRecord.isPresent()) {
			LogRecord log = logRecord.get();
			
			Date date = log.getAccessTime();
			
			pair.set(new Text(format.format(date)), new Text(date.getHours()+""));// 日期 + 小时
			
			context.write(pair, one);
		} else {
			Counter count = context.getCounter("LogRecord_Parser",
					"logRecord_Value_Is_Absent");
			count.increment(1);
		}
	}
}