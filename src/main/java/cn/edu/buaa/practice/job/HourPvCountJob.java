package cn.edu.buaa.practice.job;  
  
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import cn.edu.buaa.practice.bean.TextTextPair;
import cn.edu.buaa.practice.mr.HourPvMapper;
import cn.edu.buaa.practice.mr.HourPvReducer;
  
public class HourPvCountJob {  
    public static void main(String[] args) throws Exception {
    	if(args.length!=2){
    		System.err.println("Usage: inputPath outputPath");
    		System.exit(1);
    	}
        Configuration conf = new Configuration();  
        @SuppressWarnings("deprecation")
		Job job = new Job(conf);  
        job.setJarByClass(HourPvCountJob.class);  
        job.setJobName("STAT_TIME");  		
  
        job.setOutputKeyClass(Text.class);  
        job.setOutputValueClass(IntWritable.class);  
  
        job.setMapOutputKeyClass(TextTextPair.class);
        job.setMapOutputValueClass(IntWritable.class);
        
        job.setMapperClass(HourPvMapper.class);
        job.setReducerClass(HourPvReducer.class);  
  
        job.setInputFormatClass(TextInputFormat.class);  
        job.setOutputFormatClass(TextOutputFormat.class);  
        
        FileInputFormat.addInputPath(job, new Path(args[0]));  
        FileOutputFormat.setOutputPath(job, new Path(args[1]));  
  
        job.waitForCompletion(true);  
    }  
}  