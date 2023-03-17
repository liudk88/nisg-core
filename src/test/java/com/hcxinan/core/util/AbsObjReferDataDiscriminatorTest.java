package com.hcxinan.core.util;

import com.hcxinan.core.inte.IDataDiscriminator;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import org.apache.log4j.Logger;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AbsObjReferDataDiscriminatorTest {
    private static final Logger log = Logger.getLogger(AbsObjReferDataDiscriminator.class);

    @Test
    public void testGetDiff(){
        log.info("========测试新增和更新========");

        //可以是模拟前端发送过来的数据
        Map<String,String> m1=new HashMap(){{
            put("pk","001");
            put("name","zhangsan");
        }};
        Map<String,String> m2=new HashMap(){{
            put("pk","002");
            put("name","lisi");
        }};
        Map<String,String> m3=new HashMap(){{
            put("name","xiaohong");
        }};
        //模拟前端传入的数据
        List<Map<String,String>> saveDatas=new ArrayList<Map<String,String>>(){{
            add(m1);
            add(m2);
            add(m3);
        }};
        //可以模拟是数据库中的数据
        Set<String> set=new HashSet<String>(){{
            add("001");
            add("003");
        }};
        AbsObjReferDataDiscriminator<Map<String, String>, Set<String>> absReferObjDataDiff= new AbsObjReferDataDiscriminator<Map<String, String>, Set<String>>() {
            @Override
            public boolean isNew(Map<String, String> data, Set<String> referObj) {
                String pkVal=data.get("pk");
                if(StringUtils.isNotBlank(pkVal) && referObj.contains(pkVal)){
                    return false;
                }
                return true;
            }
        };
        absReferObjDataDiff.setReferObj(set);
        IDataDiscriminator<Map<String, String>> dataDiff=absReferObjDataDiff;

        dataDiff.analyseDatas(saveDatas);

        List<Map<String,String>> addDatas=dataDiff.getAddedDatas();
        List<Map<String,String>> updateDatas=dataDiff.getUpdatedDatas();

        assertThat(addDatas.size()).isEqualTo(2);
        assertThat(updateDatas.size()).isEqualTo(1);
        //新增
        assertThat(addDatas.get(0)).isEqualTo(m2);
        assertThat(addDatas.get(1)).isEqualTo(m3);
        //更新
        assertThat(updateDatas.get(0)).isEqualTo(m1);

        log.info("========测试删除========");
        //反向操作获取新增项，即使删除
        AbsObjReferDataDiscriminator<String,List<Map<String,String>>> dataDiff1=new AbsObjReferDataDiscriminator<String, List<Map<String, String>>>() {
            @Override
            protected boolean isNew(String data, List<Map<String, String>> referObj) {
                for(Map<String, String> refer:referObj){
                    String pkval=refer.get("pk");
                    if(data.equals(pkval)){
                        return false;
                    }
                }
                return true;
            }
        };
        //以前端数据作为源，数据库数据作为参照物，那么结果新增的相当于是需要删除的数据
        dataDiff1.setReferObj(saveDatas);
        dataDiff1.analyseDatas(set);
        List<String> delDatas=dataDiff1.getAddedDatas();

        //删除
        assertThat(delDatas.get(0)).isEqualTo("003");
    }
}
