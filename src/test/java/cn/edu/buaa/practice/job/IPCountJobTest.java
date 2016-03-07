package cn.edu.buaa.practice.job;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.log4j.BasicConfigurator;

import cn.edu.buaa.practice.mr.HyperLogLogIPCombiner;
import cn.edu.buaa.practice.mr.HyperLogLogIPMapper;
import cn.edu.buaa.practice.mr.HyperLogLogIPReducer;

public class IPCountJobTest {

    public static void main(String[] args)
            throws IOException, ClassNotFoundException, InterruptedException {
        System.setProperty("hadoop.home.dir", HadoopConfig.HADOOP_ROOT);

        BasicConfigurator.configure();
        String input = HadoopConfig.HDFS_ROOT_PATH + "/user/rt/input";///access.log.10
        String output = HadoopConfig.HDFS_ROOT_PATH + "/user/rt/ip_output";

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "ip count");

        job.setJarByClass(IPCountJobTest.class);

        job.setMapperClass(HyperLogLogIPMapper.class);
        job.setCombinerClass(HyperLogLogIPCombiner.class);
        job.setReducerClass(HyperLogLogIPReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(BytesWritable.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.addInputPath(job, new Path(input));
        FileOutputFormat.setOutputPath(job, new Path(output));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

}