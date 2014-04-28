package cn.dataguru.hadoop.ch12;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class UserFilterDriver {

    public static void main(String[] args) throws Exception {
        // set configuration
        Configuration conf = new Configuration();
        if (args.length != 2) {
            System.err.println("Usage: UserFilterDriver <in> <out>");
            System.exit(2);
        }
        
        // specify input and output DIRECTORIES 
        Path inPath = new Path(args[0]);
        Path outPath = new Path(args[1]);
        
        Job job = new Job(conf, "User Filter");
        
        job.setJarByClass(UserFilterDriver.class);
        
        job.setMapperClass(UserFilterMapper.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);
        
        //job.setNumReduceTasks(0);
        
        FileInputFormat.addInputPath(job, inPath);
        FileOutputFormat.setOutputPath(job, outPath);

        job.waitForCompletion(true);
    }
}
