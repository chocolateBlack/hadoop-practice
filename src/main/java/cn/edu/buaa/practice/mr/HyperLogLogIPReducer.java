package cn.edu.buaa.practice.mr;

import java.io.IOException;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.clearspring.analytics.stream.cardinality.CardinalityMergeException;
import com.clearspring.analytics.stream.cardinality.HyperLogLog;
/**
 * 使用HyperLogLog算法优化UV统计。
 * 考虑到海量数据的场景下，通过使用哈希表存储IP并去重计算UV的方式耗费大量内存，使用HyperLogLog算法可以使用很少的内存计算亿级UV。
 * 
 * http://blog.codinglabs.org/articles/algorithms-for-cardinality-estimation-part-iv.html
 * @author 廖雄杰
 *
 */
public class HyperLogLogIPReducer extends  
            Reducer<Text, BytesWritable, Text, IntWritable> {  
  
		public void reduce(Text key, Iterable<BytesWritable> hlls,  
                Context context) throws IOException, InterruptedException {
			HyperLogLog hllMerged = new HyperLogLog(16);
			for (BytesWritable val : hlls) {
				HyperLogLog hll = HyperLogLog.Builder.build(val.copyBytes());
            	try {
					hllMerged.addAll(hll);
				} catch (CardinalityMergeException e) {
					System.err.println("CardinalityMergeException: " + e.getMessage());
				}
            	System.out.println("reduce key:" + key + ", value=" + hllMerged.cardinality());
            }  
            context.write(key, new IntWritable((int)hllMerged.cardinality()));  
        }  
    }  