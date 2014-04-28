package cn.dataguru.hadoop.ch12;

import java.io.IOException;
import java.util.Collection;
import java.util.regex.Pattern;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.NamedVector;
import org.apache.mahout.math.VectorWritable;

import com.google.common.collect.Lists;

public class UserClusteringInputMapper extends Mapper<LongWritable, Text, Text, VectorWritable> {
    
    public static final Pattern DELIMITER = Pattern.compile("[\t,]");
    private double duration, callOut;
    private int userid;
    
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // id,durtime,call_out
        String[] columns = DELIMITER.split(value.toString());
        
        userid = Integer.parseInt(columns[0]);
        duration = Double.parseDouble(columns[1]);
        callOut  = Double.parseDouble(columns[2]);
        
        // sometimes there are multiple separator spaces
        Collection<Double> doubles = Lists.newArrayList();
        
        doubles.add(duration);
        doubles.add(callOut);
        
        
        NamedVector unvc;
        unvc = new NamedVector(new DenseVector(new double[] { duration, callOut }), new String("UserID:"+userid));
        
        VectorWritable vectorWritable = new VectorWritable(unvc);
        context.write(new Text(String.valueOf(1)), vectorWritable);
        
    }

}
