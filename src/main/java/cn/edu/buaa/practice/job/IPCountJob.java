package cn.edu.buaa.practice.job;  
  
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import cn.edu.buaa.practice.mr.HyperLogLogIPCombiner;
import cn.edu.buaa.practice.mr.HyperLogLogIPMapper;
import cn.edu.buaa.practice.mr.HyperLogLogIPReducer;
import cn.edu.buaa.practice.mr.IPMapper;
  
public class IPCountJob {  
    public static void main(String[] args) throws Exception {
    	if(args.length!=2){
    		System.err.println("Usage: inputPath outputPath");
    		System.exit(1);
    	}
        Configuration conf = new Configuration();  
        Job job = new Job(conf);  
        job.setJarByClass(IPCountJob.class);  
        job.setJobName("STAT_UV");  
  
        job.setOutputKeyClass(Text.class);  
        job.setOutputValueClass(Text.class);  
  
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(BytesWritable.class);
        
        job.setMapperClass(HyperLogLogIPMapper.class);
        job.setCombinerClass(HyperLogLogIPCombiner.class);
        job.setReducerClass(HyperLogLogIPReducer.class);  
  
        job.setInputFormatClass(TextInputFormat.class);  
        job.setOutputFormatClass(TextOutputFormat.class);  
        
        FileInputFormat.addInputPath(job, new Path(args[0]));  
        FileOutputFormat.setOutputPath(job, new Path(args[1]));  
  
        job.waitForCompletion(true);  
    }  
}  