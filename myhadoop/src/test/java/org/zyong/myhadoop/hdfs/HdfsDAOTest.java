package org.zyong.myhadoop.hdfs;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.apache.hadoop.mapred.JobConf;
import org.junit.Test;

public class HdfsDAOTest {

    private static final String HDFS = "hdfs://sh02svr2882.hadoop.sh2.ctripcorp.com:54310/";
    private JobConf conf = HdfsDAO.config();
    private HdfsDAO hdfs = new HdfsDAO(conf);

    @Test
    public void testConfig() {
        fail("Not yet implemented");
    }

    @Test
    public void testMkdirs() throws IOException {
        hdfs.mkdirs(HDFS + "/user/biuser/warehouse/etl/Source_MobDB.db/mobileservertrace/test");
    }

    @Test
    public void testRmr() throws IOException {
        String prefix = "2013-12-1";
        for (int i = 0; i <= 9; i++) {
            hdfs.rmr(HDFS + "/user/biuser/warehouse/etl/Source_MobDB.db/mobileservertrace/" + prefix + i, true);
        }
    }

    @Test
    public void testRmr2() throws IOException {
        hdfs.rmr(HDFS + "/user/bimob/warehouse/etl/source_mobdb.db/zbak_factmbservertrace", true);
    }

    @Test
    public void testLs() {
        fail("Not yet implemented");
    }

    @Test
    public void testCreateFile() {
        fail("Not yet implemented");
    }

    @Test
    public void testCopyFile() {
        fail("Not yet implemented");
    }

}
