package cn.dataguru.hadoop.ch12;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.mahout.clustering.canopy.CanopyDriver;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.utils.clustering.ClusterDumper;

public class UserClusteringJob {
    
    private static final String DIRECTORY_CONTAINING_CONVERTED_INPUT = "data";
    
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: UserInputDriver <in> <out>");
            System.exit(2);
        }
        
        Configuration conf = new Configuration();
        
        // specify input and output DIRECTORIES
        Path inPath = new Path(args[0]);
        Path outPath = new Path(args[1]);
        
        double convergenceDelta = 0.001;
        int maxIterations = 10;
        
        run(conf, inPath, outPath, new EuclideanDistanceMeasure(), 0.2, 0.1, convergenceDelta, maxIterations);
    }
    
    public static void run(Configuration conf, Path input, Path output, DistanceMeasure measure, double t1, double t2,
            double convergenceDelta, int maxIterations) throws Exception {
        
        Path directoryContainingConvertedInput = new Path(output, DIRECTORY_CONTAINING_CONVERTED_INPUT);
        System.out.println("Preparing Input");
        
        // 数据向量化
        UserClusteringInputDriver.runJob(input, directoryContainingConvertedInput, "org.apache.mahout.math.DenseVector");
        
        // 用Canopy先找出中心点
        System.out.println("Running Canopy to get initial clusters");
        CanopyDriver.run(conf, directoryContainingConvertedInput, output, measure, t1, t2, false, false);
        
        System.out.println("Running KMeans");
        
        //K-Means聚类
        KMeansDriver.run(conf, 
                directoryContainingConvertedInput, 
                new Path(output, "clusters-0-final"),
                output, 
                measure, convergenceDelta, maxIterations, true, false);
        // run ClusterDumper
        
        // 打印最终结果
        ClusterDumper clusterDumper = new ClusterDumper(
                finalClusterPath(conf, output, maxIterations), 
                new Path(output, "clusteredPoints"));
        clusterDumper.printClusters(null);
    }
    
    /**
     * Return the path to the final iteration's clusters
     */
    private static Path finalClusterPath(Configuration conf, Path output, int maxIterations) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        for (int i = maxIterations; i >= 0; i--) {
            Path clusters = new Path(output, "clusters-" + i + "-final");
            if (fs.exists(clusters)) {
                return clusters;
            }
        }
        return null;
    }
}
