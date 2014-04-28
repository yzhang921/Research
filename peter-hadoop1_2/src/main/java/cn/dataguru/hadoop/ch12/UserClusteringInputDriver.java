package cn.dataguru.hadoop.ch12;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.mahout.clustering.conversion.InputDriver;
import org.apache.mahout.math.VectorWritable;

public class UserClusteringInputDriver {

    public static void runJob(Path input, Path output, String vectorClassName) throws IOException,
            InterruptedException, ClassNotFoundException {

        Configuration conf = new Configuration();
        conf.set("vector.implementation.class.name", vectorClassName);
        Job job = new Job(conf, "Input Driver running over input: " + input);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(VectorWritable.class);
        job.setOutputFormatClass(SequenceFileOutputFormat.class);
        job.setMapperClass(UserClusteringInputMapper.class);
        job.setNumReduceTasks(0);
        job.setJarByClass(InputDriver.class);

        FileInputFormat.addInputPath(job, input);
        FileOutputFormat.setOutputPath(job, output);

        boolean succeeded = job.waitForCompletion(true);
        if (!succeeded) {
            throw new IllegalStateException("Job failed!");
        }
    }

    public static void main(String[] args) throws Exception {

        if (args.length != 2) {
            System.err.println("Usage: UserInputDriver <in> <out>");
            System.exit(2);
        }

        // specify input and output DIRECTORIES
        Path inPath = new Path(args[0]);
        Path outPath = new Path(args[1]);

        runJob(inPath, outPath, "org.apache.mahout.math.DenseVector");
    }
}
