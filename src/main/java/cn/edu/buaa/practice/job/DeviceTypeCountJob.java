package cn.edu.buaa.practice.job;  
  
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.ByteWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import cn.edu.buaa.practice.mr.DeviceCombiner;
import cn.edu.buaa.practice.mr.DeviceMapper;
import cn.edu.buaa.practice.mr.DeviceReducer;
  
public class DeviceTypeCountJob {  
    public static void main(String[] args) throws Exception {
    	if(args.length!=2){
    		System.err.println("Usage: inputPath outputPath");
    		System.exit(1);
    	}
        Configuration conf = new Configuration();  
        Job job = new Job(conf);  
        job.setJarByClass(DeviceTypeCountJob.class);  
        job.setJobName("STAT_DEVICE");  
        
        job.setMapOutputKeyClass(ByteWritable.class);
        job.setMapOutputValueClass(IntWritable.class);
        
        job.setOutputKeyClass(Text.class);  
        job.setOutputValueClass(IntWritable.class);  
  
        job.setMapperClass(DeviceMapper.class); 
        job.setCombinerClass(DeviceCombiner.class);  
        job.setReducerClass(DeviceReducer.class);  
  
        job.setInputFormatClass(TextInputFormat.class);  
        job.setOutputFormatClass(TextOutputFormat.class);  
  
        FileInputFormat.addInputPath(job, new Path(args[0]));  
        FileOutputFormat.setOutputPath(job, new Path(args[1]));  
  
        job.waitForCompletion(true);  
    }  
}  