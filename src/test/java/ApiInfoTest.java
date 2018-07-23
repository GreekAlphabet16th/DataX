
import com.cetiti.core.support.PageModel;
import com.cetiti.dataX.entity.ApiMethodInfo;
import com.cetiti.dataX.entity.XmlResource;
import com.cetiti.dataX.service.OpenApiService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ApiInfoTest extends BaseTest {
    @Autowired
    private OpenApiService openApiService;
/*    @Test
    public void testInsertApiInfo(){
        XmlResource xmlResource = new XmlResource();
        xmlResource.setSqlId(4);
        xmlResource.setProvider("cetiti");
        xmlResource.setDescription("测试xml文件解析");
        List<String> mappers = new ArrayList<>();
        mappers.add("file:///D:/zly7056/Desktop/CompanyMapper.xml");
        openApiService.insertOpenApiService(xmlResource, mappers);
    }*/

    @Test
    public void testQuery(){
        PageModel<ApiMethodInfo> result = openApiService.ApiMethodInfoList(0,10);
        System.out.println(result.getApiInfo());
        PageModel<XmlResource> result1 = openApiService.XmlResourceList(0,10);
        System.out.println(result1.getApiInfo());
    }
}
