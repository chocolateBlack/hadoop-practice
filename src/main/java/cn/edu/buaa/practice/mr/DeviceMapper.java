package cn.edu.buaa.practice.mr;

import java.io.IOException;

import org.apache.hadoop.io.ByteWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Mapper;

import cn.edu.buaa.practice.bean.LogRecord;
import cn.edu.buaa.practice.parser.LogParser;

import com.google.common.base.Optional;

public class DeviceMapper extends Mapper<LongWritable, Text, ByteWritable, IntWritable> {

	private final IntWritable one = new IntWritable(1);
	private ByteWritable deviceType = new ByteWritable();

	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		Optional<LogRecord> logRecord = LogParser.parse(line);

		if (logRecord.isPresent()) {
			LogRecord log = logRecord.get();
			deviceType.set(log.getDeviceType());
			context.write(deviceType, one);
		} else {
			Counter count = context.getCounter("LogRecord_Parser",
					"logRecord_Value_Is_Absent");
			count.increment(1);
		}
	}
}