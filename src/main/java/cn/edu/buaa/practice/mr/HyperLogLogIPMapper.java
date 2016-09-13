package cn.edu.buaa.practice.mr;

import java.io.IOException;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Mapper;

import cn.edu.buaa.practice.bean.LogRecord;
import cn.edu.buaa.practice.parser.LogParser;

import com.google.common.base.Optional;

public class HyperLogLogIPMapper extends Mapper<Object, Text, Text, BytesWritable> {  
  
        private Text requestUrl = new Text();  
        //Text ip = new Text();
  
        public void map(Object key, Text value, Context context)  
                throws IOException, InterruptedException {  
            String line = value.toString();  
            Optional<LogRecord> logRecord = LogParser.parse(line);
            
            if (logRecord.isPresent()) {
                LogRecord log =  logRecord.get();
                requestUrl.set(log.getNormalizedRequestUri());
                // ip.set(log.getRemoteAddr());
                context.write(requestUrl, new BytesWritable(log.getRemoteAddr().getBytes()));
			} else {
				Counter count = context.getCounter("LogRecord_Parser", "logRecord_Value_Is_Absent");
				count.increment(1);
			}
        }  
    }  