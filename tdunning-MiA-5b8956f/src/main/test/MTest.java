

import java.io.File;

import junit.framework.TestCase;

import org.apache.lucene.benchmark.utils.ExtractReuters;
import org.junit.Test;

public class MTest extends TestCase {
    
    @Test
    public void test1() {
        ExtractReuters er = new ExtractReuters(new File("reuters/"), new File("reuters-extracted/"));
        er.main(new String[]{"reuters/", "reuters-extracted/"});
    }
}
