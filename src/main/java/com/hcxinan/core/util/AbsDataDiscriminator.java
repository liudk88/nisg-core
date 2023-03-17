package com.hcxinan.core.util;

import com.hcxinan.core.inte.IDataDiscriminator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
*@Description 设置好了新增数据和更新数据的设置和获取实现
*@Param 
*@Return 
*@Author liudk
*@DateTime 20-10-31 上午11:43
*/
public abstract class AbsDataDiscriminator<T> implements IDataDiscriminator<T> {

    protected List<T> addDatas=new ArrayList();//新增的数据
    protected List<T> updateDatas=new ArrayList();//更新的数据

    public void clear() {
        addDatas=new ArrayList<>();
        updateDatas=new ArrayList<>();
    }

    @Override
    public void addAddedData(T objData) {
        addDatas.add(objData);
    }

    @Override
    public void addUpdatedData(T objData) {
        updateDatas.add(objData);
    }

    @Override
    public List<T> getAddedDatas() {
        return addDatas;
    }

    @Override
    public List<T> getUpdatedDatas() {
        return updateDatas;
    }

}
