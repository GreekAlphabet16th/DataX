
import com.cetiti.core.support.UUIDGenerator;
import com.cetiti.dataX.entity.DataProperties;
import com.cetiti.dataX.service.impl.ApiService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ApiInfoTest extends BaseTest {
    @Autowired
    private ApiService apiServiceImpl;
    @Test
    public void testInsertApiInfo(){
        DataProperties dataProperties = new DataProperties();
        dataProperties.setDataId(UUIDGenerator.generate());
        dataProperties.setDataCenter("pdDsdb");
        dataProperties.setSqlType("mysql");
        dataProperties.setDriver("com.mysql.jdbc.Driver");
        dataProperties.setUrl("jdbc:mysql://10.0.30.207:3306/pd_dsdb?useUnicode=true&characterEncoding=utf8&useSSL=false");
        dataProperties.setUserName("pd");
        dataProperties.setPassWord("123456");
        List<String> mappers = new ArrayList<>();
        mappers.add("file:///D:/zly7056/Desktop/CompanyMapper.xml");
        apiServiceImpl.insertApiService(dataProperties,mappers);
    }
}
