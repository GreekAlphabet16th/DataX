package com.cetiti.dataX.web;

import com.cetiti.core.controller.BaseController;
import com.cetiti.core.support.PageModel;
import com.cetiti.dataX.dao.DataPropertiesDao;
import com.cetiti.dataX.dao.UserDao;
import com.cetiti.dataX.entity.DataProperties;
import com.cetiti.dataX.entity.User;
import com.cetiti.dataX.service.impl.TestService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/users")
public class UserController extends BaseController {
    private static Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private DataPropertiesDao dataPropertiesDao;
    @Autowired
    private TestService testService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public PageModel<User> getUserList(){
        this.offsetPage(0,10);
        List<User> userList = userDao.selectUserList();
        return this.resultPage(userList);
    }

    @RequestMapping(value = "/{dataId}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public void getUserList(DataProperties param){
        DataProperties dataProperties = dataPropertiesDao.getDataProperties(param.getDataId());
        System.out.println(dataProperties.toString());
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getUser(){
        return testService.testServiceData();
    }


    
}
