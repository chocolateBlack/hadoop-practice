package cn.edu.buaa.practice.mr;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class IPReducer extends  
            Reducer<Text, Text, Text, IntWritable> {  
  
        public void reduce(Text key, Iterable<Text> ips,  
                Context context) throws IOException, InterruptedException {  
            Set<Text> ipSet = new HashSet<Text>();
            for (Text val : ips) { 
            	if(!ipSet.contains(val)){
            		ipSet.add(val);
            	}  
            }  
            context.write(key, new IntWritable(ipSet.size()));  
        }  
    }  