package cn.edu.buaa.practice.mr;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import cn.edu.buaa.practice.bean.TextTextPair;

public class HourPvReducer extends Reducer<TextTextPair, IntWritable, Text, IntWritable> {
	Text outputKey = new Text();
	public void reduce(TextTextPair key, Iterable<IntWritable> values, Context context)
			throws IOException, InterruptedException {
		int sum = 0;
		for (IntWritable val : values) {
			sum += val.get();
		}
		outputKey.set(key.toString());
		context.write(outputKey, new IntWritable(sum));
	}
}