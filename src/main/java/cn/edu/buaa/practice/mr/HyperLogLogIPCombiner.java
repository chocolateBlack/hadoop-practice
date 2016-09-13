package cn.edu.buaa.practice.mr;

import java.io.IOException;

import org.apache.hadoop.io.BytesWritable;
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
public class HyperLogLogIPCombiner extends  
            Reducer<Text, BytesWritable, Text, BytesWritable> {  
  
		public void reduce(Text key, Iterable<BytesWritable> ips,  
                Context context) throws IOException, InterruptedException {
			HyperLogLog hllMerged = new HyperLogLog(16);
			for (BytesWritable val : ips) {
				byte[] bytes = val.copyBytes();
				if(bytes.length < 1024) {
					// 来自Mapper的原始IP（非HLL对象），通过序列化后的字节数区分，HLL序列化后的字节数 > 1K，原始IP通常为几十个字节
					hllMerged.offer(bytes);
				} else {
					// 合并combiner/reducer阶段的HLL对象
					HyperLogLog hll = HyperLogLog.Builder.build(val.copyBytes());
	            	try {
						hllMerged.addAll(hll);
					} catch (CardinalityMergeException e) {
						System.err.println("CardinalityMergeException: " + e.getMessage());
					}
				}            	
            }   
            context.write(key, new BytesWritable(hllMerged.getBytes()));  
        }  
    }  