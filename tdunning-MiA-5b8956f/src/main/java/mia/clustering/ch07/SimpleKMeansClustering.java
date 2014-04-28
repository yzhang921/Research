package mia.clustering.ch07;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.mahout.clustering.WeightedVectorWritable;
import org.apache.mahout.clustering.kmeans.Cluster;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.math.NamedVector;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;

public class SimpleKMeansClustering {
    public static final double[][] points = { { 1, 1 }, { 2, 1 }, { 1, 2 }, { 2, 2 }, { 3, 3 }, { 8, 8 }, { 9, 8 },
            { 8, 9 }, { 9, 9 } };

    public static void writePointsToFile(List<Vector> points, String fileName, FileSystem fs, Configuration conf)
            throws IOException {
        Path path = new Path(fileName);
        SequenceFile.Writer writer = new SequenceFile.Writer(fs, conf, path, LongWritable.class, VectorWritable.class);
        long recNum = 0;
        VectorWritable vec = new VectorWritable();
        for (Vector point : points) {
            vec.set(point);
            writer.append(new LongWritable(recNum++), vec);
        }
        writer.close();
    }

    public static List<Vector> getPoints(double[][] raw) {
        List<Vector> points = new ArrayList<Vector>();
        NamedVector nv;
        for (int i = 0; i < raw.length; i++) {
            double[] fr = raw[i];
            // 这里选择创建 RandomAccessSparseVector
            Vector vec = new RandomAccessSparseVector(fr.length);
            // 将数据存放在创建的 Vector 中
            vec.assign(fr);
            nv = new NamedVector(vec, "ID:" + i);
            points.add(nv);
        }
        return points;
    }

    public static void main(String args[]) throws Exception {

        int k = 2;
        
        // Generate Vectors from input data
        List<Vector> vectors = getPoints(points);
        
        // Prepare work folder
        File testData = new File("testdata");
        if (!testData.exists()) {
            testData.mkdir();
        }
        testData = new File("testdata/points");
        if (!testData.exists()) {
            testData.mkdir();
        }
        
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        
        // Write Vectors to input directory
        writePointsToFile(vectors, "testdata/points/file1", fs, conf);

        Path path = new Path("testdata/clusters/part-00000");
        SequenceFile.Writer writer = new SequenceFile.Writer(fs, conf, path, Text.class, Cluster.class);
        
        // Write initial cluster centers
        for (int i = 0; i < k; i++) {
            Vector vec = vectors.get(i);
            Cluster cluster = new Cluster(vec, i, new EuclideanDistanceMeasure());
            writer.append(new Text(cluster.getIdentifier()), cluster);
        }
        writer.close();
        
        // Run k-means algorithm
        KMeansDriver.run(
                conf, new Path("testdata/points"), new Path("testdata/clusters"), new Path("output"),
                new EuclideanDistanceMeasure(), 0.001, 10, true, false);

        SequenceFile.Reader reader = new SequenceFile.Reader(fs, new Path("output/" + Cluster.CLUSTERED_POINTS_DIR
                + "/part-m-00000"), conf);
        
        
        // Read output, print vector, cluster ID
        IntWritable key = new IntWritable();
        WeightedVectorWritable value = new WeightedVectorWritable();
        while (reader.next(key, value)) {
            System.out.println(value.toString() + " belongs to cluster " + key.toString());
        }
        reader.close();
    }

}
