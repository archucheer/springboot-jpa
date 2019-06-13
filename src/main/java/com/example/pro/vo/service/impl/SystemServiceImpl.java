package com.example.pro.vo.service.impl;

import com.example.pro.fra.bean.RetMsg;
import com.example.pro.fra.ex.ResultCode;
import com.example.pro.fra.ex.ResultException;
import com.example.pro.vo.model.KidDailyInfo;
import com.example.pro.vo.repository.KidDailyRepository;
import com.example.pro.vo.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: ccz
 * @Date: 2019/5/7 16:00
 * @Description:
 */
@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    KidDailyRepository kidDailyRepository;

    @Override
    public RetMsg exampleService() {
        KidDailyInfo kidDailyInfo = kidDailyRepository.findById("13652f006f1711e9a44ba994f48035f6").get();
        RetMsg retMsg = RetMsg.newInstance();
        retMsg.setContent(kidDailyInfo);
        if(1==1){
            throw new ResultException(ResultCode.ERROR_NET_RPC);
        }

        return retMsg;
    }
}
