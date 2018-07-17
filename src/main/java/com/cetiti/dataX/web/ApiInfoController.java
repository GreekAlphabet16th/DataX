package com.cetiti.dataX.web;

import com.cetiti.core.controller.BaseController;
import com.cetiti.core.support.OpenApiResult;
import com.cetiti.core.support.PageModel;
import com.cetiti.dataX.service.impl.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
@RequestMapping("/api")
public class ApiInfoController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiInfoController.class);

    @Autowired
    private ApiService apiServiceImpl;

    /**
     * rest方式接口访问 http://Endpoint/api/?format=rest&pageNum&pageSize&parameters
     * 示例：http://localhost:8080/dataX/api/com.cetiti.dataX.dao.UserDao/selectUserList/?format=rest&pageNum=1&pageSize=10
     * */
    @RequestMapping(value = "/{mapper}/{method}/", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> apiMethodResult(@PathVariable("mapper") String mapper,
                                         @PathVariable("method")String method,
                                         @RequestParam Map<String,String> parameters){
        String format = parameters.get("format");
        OpenApiResult result = OpenApiResult.getDefaultOpenResult();
        try {
            if(format.equals("rest")){
                PageModel<Map> resultList = apiServiceImpl.RestApiService(mapper,method,parameters);
                result.setPageInfo(resultList);
                result.setMsg("success");
                return result.getResultMap();
            }else {
                result.setMsg("URL请求格式错误");
                return result.getResultMap();
            }
        } catch (Exception e){
            LOGGER.error(e.getMessage());
            result.setMsg("接口数据返回失败");
            result.setReslutCode(OpenApiResult.CODE.RET_EXCEPTION);
            return result.getResultMap();
        }


    }
}
