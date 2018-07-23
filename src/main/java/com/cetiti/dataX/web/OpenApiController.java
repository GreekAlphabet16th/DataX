package com.cetiti.dataX.web;

import com.cetiti.core.controller.BaseController;
import com.cetiti.core.support.ApiResult;
import com.cetiti.core.support.PageModel;
import com.cetiti.dataX.service.OpenApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
public class OpenApiController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpenApiController.class);
    @Autowired
    private OpenApiService openApiService;
    private final static String SUCCESS = "success";
    private final static String FAILED = "failed";

    @RequestMapping(value = "/console/xmlResource", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> xmlResourceList(int pageNum, int pageSize, String keyWord){
        ApiResult result =ApiResult.getDefaultApiResult();
        try {
            result.setPageInfo(openApiService.XmlResourceList(pageNum,pageSize));
            result.setMsg(SUCCESS);
            return result.getResultMap();
        } catch (Exception e){
            LOGGER.error(e.getMessage());
            result.setReslutCode(ApiResult.CODE.RET_NO);
            result.setMsg(FAILED);
            return result.getResultMap();
        }

    }

    @RequestMapping(value = "/console/apiMethodInfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> apiMethodInfoList(int pageNum, int pageSize, String keyWord){
        ApiResult result =ApiResult.getDefaultApiResult();
        try {
            result.setPageInfo(openApiService.ApiMethodInfoList(pageNum,pageSize));
            result.setMsg(SUCCESS);
            return result.getResultMap();
        } catch (Exception e){
            LOGGER.error(e.getMessage());
            result.setReslutCode(ApiResult.CODE.RET_NO);
            result.setMsg(FAILED);
            return result.getResultMap();
        }

    }


    /**
     * rest方式接口访问 http://Endpoint/api/?format=rest&pageNum&pageSize&parameters
     * 示例：http://localhost:8080/dataX/api/com.cetiti.dataX.dao.UserDao/selectUserList/?format=rest&pageNum=1&pageSize=10
     * */
    @RequestMapping(value = "/api/{mapper}/{method}/", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> apiMethodResult(@PathVariable("mapper") String mapper,
                                               @PathVariable("method")String method,
                                               @RequestParam Map<String,String> parameters){
        String format = parameters.get("format");
        ApiResult result = ApiResult.getDefaultApiResult();
        try {
            if(format.equals("rest")){
                PageModel<Map> resultList = openApiService.RestOpenApiService(mapper,method,parameters);
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
            result.setReslutCode(ApiResult.CODE.RET_EXCEPTION);
            return result.getResultMap();
        }


    }


}
