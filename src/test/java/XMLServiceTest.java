import com.cetiti.core.dataSource.DataCenterBuilder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class XMLServiceTest extends BaseTest {
    @Test
    public void testXmlServiceBuild(){
        List<String> mappers = new ArrayList<>();
        mappers.add("file:///D:/zly7056/Desktop/UserMapper.xml");
        DataCenterBuilder dataCenterBuilder = new DataCenterBuilder();
        Map<String,Object> map = dataCenterBuilder.xmlServiceBulid(mappers,null);
        System.out.println(map);
    }
}
