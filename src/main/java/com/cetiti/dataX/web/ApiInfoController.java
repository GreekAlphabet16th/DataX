package com.cetiti.dataX.web;

import com.cetiti.core.controller.BaseController;
import com.cetiti.dataX.entity.ApiInfo;
import com.cetiti.dataX.service.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/api")
public class ApiInfoController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiInfoController.class);

    @Autowired
    private ApiService apiService;

    @RequestMapping(value = "/rest/{mapper}/{method}/{params}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryApiMethodResult(@PathVariable("mapper") String mapper,
                                          @PathVariable("method")String method,
                                          @PathVariable("params")String params){
        return apiService.RestApiService(mapper,method,params);
    }
}
