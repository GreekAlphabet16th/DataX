import com.cetiti.dataX.entity.DataCenter;
import com.cetiti.dataX.service.DataCenterService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DataCenterTest extends BaseTest {

    @Autowired
    private DataCenterService dataCenterService;

    @Test
    public void testInsert(){
        DataCenter dataCenter = new DataCenter();
        dataCenter.setSqlName("测试数据库");
        dataCenter.setSqlType(1);
        dataCenter.setUrl("10.0.30.207:3306/pd_dsdb");
        dataCenter.setUserName("pd");
        dataCenter.setPassWord("123456");
        dataCenterService.insertDataCenter(dataCenter);
    }
}
