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

import cn.edu.buaa.practice.mr.SourceMapper;
import cn.edu.buaa.practice.mr.SourceReducer;
  
public class SourceCountJob {  
    public static void main(String[] args) throws Exception {
    	if(args.length!=2){
    		System.err.println("Usage: inputPath outputPath");
    		System.exit(1);
    	}
        Configuration conf = new Configuration();  
        Job job = new Job(conf);  
        job.setJarByClass(SourceCountJob.class);  
        job.setJobName("STAT_SOURCE");  
  
        job.setOutputKeyClass(Text.class);  
        job.setOutputValueClass(IntWritable.class);  
  
        job.setMapperClass(SourceMapper.class);  
        job.setReducerClass(SourceReducer.class);  
  
        job.setInputFormatClass(TextInputFormat.class);  
        job.setOutputFormatClass(TextOutputFormat.class);  
  
        FileInputFormat.addInputPath(job, new Path(args[0]));  
        FileOutputFormat.setOutputPath(job, new Path(args[1]));  
  
        job.waitForCompletion(true);  
    }  
}  