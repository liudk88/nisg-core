package com.hcxinan.core.testdemo;

import com.hcxinan.core.inte.ICollectService;
import org.apache.log4j.Logger;

import java.util.List;

/**
*@Description 提供给测试ICollectService接口的原型，其他这个接口的实现类继承本类从而实现自动化测试
*@Param 
*@Return 
*@Author liudk
*@DateTime 20-10-31 下午2:57
*/
public abstract class ICollectServiceTest<S,D> {
    protected static final Logger log = Logger.getLogger(ICollectService.class);

    protected ICollectService collectService;

    public abstract void initializationData();

    protected void saveSourceToDist(){
        initializationData();
        //1.采集第三方提供的源数据结果
        log.info("=========采集“"+collectService.getProviderName()+"”的数据开始=========");
        List<S> sourceDatas= collectService.getShouldConvertSourceDatas();
        int total=sourceDatas==null?0:sourceDatas.size();
        log.info("采集到"+total+"条数据！");

        if(total>0){
            //2.保存数据
            collectService.saveSourceToDist(sourceDatas);
        }
    }
}
