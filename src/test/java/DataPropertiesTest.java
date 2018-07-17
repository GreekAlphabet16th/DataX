import com.cetiti.core.support.UUIDGenerator;
import com.cetiti.dataX.dao.DataPropertiesDao;
import com.cetiti.dataX.entity.DataProperties;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class DataPropertiesTest extends BaseTest {
    @Autowired
    DataPropertiesDao dataPropertiesDao;

    @Test
    public void testInsertDataProperties(){
        DataProperties dataProperties = new DataProperties();
        dataProperties.setDataId(UUIDGenerator.generate());
        dataProperties.setDataCenter("pdDsdb");
        dataProperties.setSqlType("mysql");
        dataProperties.setDriver("com.mysql.jdbc.Driver");
        dataProperties.setUrl("jdbc:mysql://10.0.30.207:3306/pd_dsdb?useUnicode=true&characterEncoding=utf8&useSSL=false");
        dataProperties.setUserName("pd");
        dataProperties.setPassWord("123456");
        int i =dataPropertiesDao.insertDataProperties(dataProperties);
        System.out.println(i);
    }
/*    @Test
    public void testUpdateDataProperties(){
        DataProperties dataProperties = new DataProperties();
        dataProperties.setDataId(new BigDecimal(3));
        dataProperties.setDataCenter("pdDsdb");
        dataProperties.setSqlType("mysql");
        dataProperties.setDriver("com.mysql.jdbc.Driver");
        dataProperties.setUrl("jdbc:mysql://10.0.30.207:3306/pd_dsdb?useUnicode=true&characterEncoding=utf8&useSSL=false");
        dataProperties.setUserName("pd");
        dataProperties.setPassWord("123456");
        dataPropertiesDao.updateDataProperties(dataProperties);
    }*/
/*    @Test
    public void testDeleteDataProperties(){
        dataPropertiesDao.deleteDataProperties(6);
    }*/
}
