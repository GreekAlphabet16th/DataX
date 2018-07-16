package com.cetiti.dataX.web;

import com.cetiti.core.controller.BaseController;
import com.cetiti.core.support.PageModel;
import com.cetiti.dataX.service.ApiService;
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
    private ApiService apiService;

    /**
     * rest方式接口访问 http://Endpoint/api/?format=rest&pageNum&pageSize&parameters
     * 示例：http://localhost:8080/dataX/api/com.cetiti.dataX.dao.UserDao/selectUserList/?format=rest&pageNum=1&pageSize=10
     * */
    @RequestMapping(value = "/{mapper}/{method}/", method = RequestMethod.POST)
    @ResponseBody
    public PageModel<Map> apiMethodResult(@PathVariable("mapper") String mapper,
                                          @PathVariable("method")String method,
                                          @RequestParam Map<String,String> parameters){
        String format = parameters.get("format");
        if(format.equals("rest")){
            PageModel resultList = apiService.RestApiService(mapper,method,parameters);
            return resultList;
        }else {
            return null;
        }

    }
}
