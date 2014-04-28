package cn.dataguru.hadoop.ch12;

import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class UserFilterMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
    
    private Text outkey;
    private float duration, callOut;
    public static final Pattern DELIMITER = Pattern.compile("[\t,]");
    public static final String CDELIMITER = ","; 
    
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // id,durtime,call_out
        String[] columns = DELIMITER.split(value.toString());
        
        // Keep only users with durtime<0.3, call_out>0.6
        
        duration = Float.parseFloat(columns[1]);
        callOut  = Float.parseFloat(columns[2]);
        
        if ( duration<0.3 && callOut>0.6 ) {
            outkey = new Text(columns[0] + CDELIMITER + duration + CDELIMITER + callOut);
            context.write(outkey, NullWritable.get());
        } else {
            context.getCounter("UserFilter", "SkipUserCount").increment(1);
            return;
        }
    }

}
