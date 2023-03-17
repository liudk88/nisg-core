package com.hcxinan.core.inte;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
*@Description 数据采集整体流程设计的接口
*@Param SOURCE代表源数据类，DIST代表目标数据类
*@Return 
*@Author liudk
*@DateTime 20-10-29 下午4:57
*/
public interface ICollectService<SOURCE, DIST> {
    Logger log = Logger.getLogger(ICollectService.class);
    //获取第三方厂商名称
    String getProviderName();
    //获取数据转换器
    IDataConvertor<SOURCE, DIST> getDataConvertor();
    /**
    *@Description 获取数据区分器。这个方法不是很好，这里提供模板数据作为支持，目的是为了获取参照物，
     * 但参照物不一定都需要，也不一定是用目标数据就能获取到，但目前没有其他办法，目标数据是经过转换后才能得到的，
     * 子类只能通过方法传入才能获取（或者把数据转换的让子类自己写，但这样做不到逻辑封装）
    *@Param [distDatas:目标数据]
    *@Return com.hcxinan.core.inte.IDataDiscriminator<D>
    *@Author liudk
    *@DateTime 20-11-1 下午5:58
    */
    IDataDiscriminator<DIST> getDataDiscriminator(List<DIST> distDatas);

    /**
    *@Description 获取需要转化的源数据（包括新增和更新两种情况）,这里的源数据不仅仅包含第三方
     * 厂商的原始数据信息,还应该包括后面做转换的时候需要用到的一切信息(因为设计上做转换和数据库无关),而这部分
     * 信息可能需要重新从系统中获取,再重新封装
    *@Param []
    *@Return java.util.List<S>
    *@Author liudk
    *@DateTime 20-11-27 上午9:20
    */
    List<SOURCE> getShouldConvertSourceDatas();
    /**
    *@Description 保存新增的数据
    *@Param [addDatas：新增的数据]
    *@Return void
    *@Author liudk
    *@DateTime 20-10-31 下午11:17
    */
    void saveAddDatas(List<DIST> addDatas);

    //保存关系的痕迹
    void saveMark(List<Map<String,Object>> relationMsgs);

    /**
    *@Description 保存更新的数据
    *@Param [updateDatas]
    *@Return void
    *@Author liudk
    *@DateTime 20-10-31 下午11:20
    */
    void saveUpdateDatas(List<DIST> updateDatas);

    /**
    *@Description 采集源数据到系统整体流程。
     * 1.把源数据转换为资产数据，并建立源数据和资产数据的关联关系
     * 2.分析资产数据，那些数据是新增的，那些数据是更新的
     * 3.新增的数据做新增操作，更新的数据做更新操作
     * 4.关联关系持久化处理
     * 5.更新源数据信息（如状态）
    *@Param [convertSourceDatas]
    *@Return void
    *@Author liudk
    *@DateTime 20-10-31 下午8:49
    */
    default void saveSourceToDist(List<SOURCE> sourceDatas){
        log.info("保存采集源数据...");

        if(sourceDatas ==null || sourceDatas.size()==0){
            throw new NullPointerException("保存的源数据不能为空！");
        }
        IDataConvertor<SOURCE, DIST> dataConvertor=getDataConvertor();
        if(dataConvertor == null){
            throw new NullPointerException("未设置数据转换器！");
        }

        List<DIST> distDatas=new ArrayList<>();
        DIST dist=null;
        for(SOURCE sourceData: sourceDatas){
            dist=dataConvertor.convertWithMark(sourceData);//转换并记录痕迹
            if(dist!=null){
                distDatas.add(dist);
            }
        }
        IDataDiscriminator<DIST> discriminator=getDataDiscriminator(distDatas);
        if(discriminator==null){
            throw new NullPointerException("未设置数据区分器");
        }
        discriminator.analyseDatas(distDatas);//分析数据
        List<DIST> addDatas=discriminator.getAddedDatas();
        List<DIST> updateDatas=discriminator.getUpdatedDatas();
        
        if(addDatas!=null && addDatas.size()>0){
            log.info("有"+addDatas.size()+"条源数据需要转化为新增数据！");
            saveAddDatas(addDatas);
        }
        if(updateDatas!=null && updateDatas.size()>0){
            log.info("有"+updateDatas.size()+"条源数据需要转化为更新数据！");
            saveUpdateDatas(updateDatas);
        }

        List<Map<String,Object>> relationMsgs=new ArrayList<>();
        for(SOURCE source: sourceDatas){
            relationMsgs.add(dataConvertor.getMark(source));
        }
        log.info("保存痕迹（关系）信息（"+relationMsgs.size()+"条!）");
        saveMark(relationMsgs);
    }
}
