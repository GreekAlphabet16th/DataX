package com.cetiti.dataX.web;

import com.cetiti.core.controller.BaseController;
import com.cetiti.core.support.ApiResult;
import com.cetiti.dataX.entity.DataCenter;
import com.cetiti.dataX.service.DataCenterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/console")
public class DataCenterController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataCenterController.class);
    @Autowired
    private DataCenterService dataCenterService;
    private final static String SUCCESS = "success";
    private final static String FAILED = "failed";


    @RequestMapping(value = "/dataCenter", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> dataCenterList(int pageNum, int pageSize, String keyWord){
        ApiResult result =ApiResult.getDefaultApiResult();
        try {
            result.setPageInfo(dataCenterService.dataCenterList(pageNum, pageSize, keyWord));
            result.setMsg(SUCCESS);
            return result.getResultMap();
        } catch (Exception e){
            LOGGER.error(e.getMessage());
            result.setReslutCode(ApiResult.CODE.RET_NO);
            result.setMsg(FAILED);
            return result.getResultMap();
        }

    }

    @RequestMapping(value = "dataCenter", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> insertDataCenter(DataCenter dataCenter){
        ApiResult result =ApiResult.getDefaultApiResult();
        try {
           boolean isConnect = dataCenterService.isConnect(dataCenter);
           if(isConnect){
                dataCenterService.insertDataCenter(dataCenter);
               result.setMsg("数据源插入成功");
           }else {
               result.setMsg("数据源连接测试失败");
               result.setReslutCode(ApiResult.CODE.RET_NO);
           }
        } catch (Exception e){
            LOGGER.error(e.getMessage());
            result.setMsg("数据源插入失败");
            result.setReslutCode(ApiResult.CODE.RET_NO);

        }
        return result.getResultMap();
    }

    @RequestMapping(value = "dataCenter", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> updateDataCenter(DataCenter dataCenter){
        ApiResult result =ApiResult.getDefaultApiResult();
        try {
            dataCenterService.updateDataCenter(dataCenter);
        } catch (Exception e){
            LOGGER.error(e.getMessage());
            result.setReslutCode(ApiResult.CODE.RET_NO);
        }
        return result.getResultMap();
    }

    @RequestMapping(value = "dataCenter", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Object> deleteDataCenter(long sqlId){
        ApiResult result =ApiResult.getDefaultApiResult();
        try {
            dataCenterService.deleteDataCenter(sqlId);
        } catch (Exception e){
            LOGGER.error(e.getMessage());
            result.setReslutCode(ApiResult.CODE.RET_NO);
        }
        return result.getResultMap();
    }
}
