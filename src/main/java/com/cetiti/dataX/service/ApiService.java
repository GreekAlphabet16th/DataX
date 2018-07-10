package com.cetiti.dataX.service;

import com.cetiti.dataX.dao.DataPropertiesDao;
import com.cetiti.dataX.dao.ServiceResourceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiService {
    @Autowired
    private DataPropertiesDao DataPropertiesDao;
    @Autowired
    private ServiceResourceDao serviceResourceDao;


}
