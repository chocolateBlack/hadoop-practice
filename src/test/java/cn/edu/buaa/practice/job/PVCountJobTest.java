package cn.edu.buaa.practice.job;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.log4j.BasicConfigurator;

import cn.edu.buaa.practice.mr.PVMapper;
import cn.edu.buaa.practice.mr.PVReducer;

public class PVCountJobTest {

    public static void main(String[] args)
            throws IOException, ClassNotFoundException, InterruptedException {
        System.setProperty("hadoop.home.dir", HadoopConfig.HADOOP_ROOT);

        BasicConfigurator.configure();
        String input = HadoopConfig.HDFS_ROOT_PATH + "/user/rt/input";///access.log.10
        String output = HadoopConfig.HDFS_ROOT_PATH + "/user/rt/pv_output";

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "pv count");

        job.setJarByClass(PVCountJobTest.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.setMapperClass(PVMapper.class);
        job.setReducerClass(PVReducer.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.addInputPath(job, new Path(input));
        FileOutputFormat.setOutputPath(job, new Path(output));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

}