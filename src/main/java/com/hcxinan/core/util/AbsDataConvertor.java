package com.hcxinan.core.util;

import com.hcxinan.core.inte.IDataConvertor;

import java.util.HashMap;
import java.util.Map;

public abstract class AbsDataConvertor<SOURCE, DIST> implements IDataConvertor<SOURCE, DIST> {

    private Map<SOURCE,Map<String, Object>> marks=new HashMap<>();//记录痕迹

    //建立联系
    public abstract Map<String, Object> buildRelation(SOURCE source, DIST dist);
    /*
    * TODO:
    * 这种写法存在问题，如果distObj是个map，那么以map对象作为map的键是不好的，
     * 因为map会发生改变（里面的元素变化），那么就很容易再次取的时候就取不到了
    * @liudk 21-10-7
    * */
    @Override
    public void recordMark(SOURCE source, DIST distObj) {
        marks.put(source,buildRelation(source,distObj));//把联系记录下为痕迹
    }

    @Override
    public Map<String, Object> getMark(SOURCE source) {
        return marks.get(source);
    }
}
