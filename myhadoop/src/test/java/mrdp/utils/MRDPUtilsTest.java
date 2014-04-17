package mrdp.utils;

import java.util.Map;

import org.junit.Test;

public class MRDPUtilsTest {

    @Test
    public void testTransformXmlToMap() {
        String xmlpath = "<row Id=\"2\" PostId=\"2\" Text=\"Do you mean in private industry or in an instructional capacity?  I suspect that these answers will vary wildly based on geographic region(s) your interested in, so it'd be nice to have that information.\" CreationDate=\"2012-02-14T21:06:14.900\" UserId=\"30\" />";
        Map<String, String> result = MRDPUtils.transformXmlToMap(xmlpath);
        
    }

}
