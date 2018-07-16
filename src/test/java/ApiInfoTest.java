import com.cetiti.core.support.UUIDGenerator;
import com.cetiti.dataX.dao.ApiInfoDao;
import com.cetiti.dataX.entity.ApiInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ApiInfoTest extends BaseTest {
    @Autowired
    private ApiInfoDao apiInfoDao;
    @Test
    public void testInsertApiInfo(){
        ApiInfo apiInfo = new ApiInfo();
        List<String> parameters = new ArrayList<>();
        parameters.add("name");
        parameters.add("districtCode");
        apiInfo.setApiId(UUIDGenerator.generate());
        apiInfo.setApiName("com.cetiti.dataX.dao.UserDao.selectUserList");
        apiInfo.setSelectId("企业信息");
        apiInfo.setCategoryId(UUIDGenerator.generate());
        apiInfo.setParameters(String.join(",",parameters));
        apiInfoDao.insertApiInfo(apiInfo);
    }
}
