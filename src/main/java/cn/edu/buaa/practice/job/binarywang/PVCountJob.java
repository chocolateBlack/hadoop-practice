package cn.edu.buaa.practice.job.binarywang;

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

public class PVCountJob {

    private static final String HADOOP_ROOT = "F:\\hadoop\\hadoop-2.6.3";
    private static final String HDFS_ROOT_PATH = "hdfs://192.168.158.130:9000";

    public static void main(String[] args)
            throws IOException, ClassNotFoundException, InterruptedException {
        System.setProperty("hadoop.home.dir", HADOOP_ROOT);

        BasicConfigurator.configure();
        String input = HDFS_ROOT_PATH + "/user/rt/input";///access.log.10
        String output = HDFS_ROOT_PATH + "/user/rt/pv_output";

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "pv count");

        job.setJarByClass(PVCountJob.class);

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