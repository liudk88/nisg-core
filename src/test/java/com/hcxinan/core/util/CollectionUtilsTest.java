package com.hcxinan.core.util;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CollectionUtilsTest {
    //assertThat( [value], [matcher statement] );
    @Test
    public void getDiff(){
        Map map1=new HashMap();
        map1.put("a","AAAAA");
        map1.put("b","2");
        map1.put(3,"2");
        map1.put(4,11);
        Map map2=new HashMap();
        map2.put("aa","BBBB");
        map2.put("b","2");
        map2.put(3,"2");
        map2.put("c","abcedf");

        Map<Object,Object[]> result= CollectionUtils.getDiff(map1,map2,true);
        assertThat(result.get("a")).isEqualTo(new Object[]{"AAAAA",null});
        assertThat(result.get("aa")).isEqualTo(new Object[]{null,"BBBB"});
        assertThat(result.get(4)).isEqualTo(new Object[]{11,null});
        assertThat(result.get("c")).isEqualTo(new Object[]{null,"abcedf"});
        //测试空值和空字符串的情况
        map1=new HashMap(){{
            put("cid001","");
        }};
        map2=new HashMap(){{
            put("cid002","");
        }};
        result= CollectionUtils.getDiff(map1,map2,true);//空值等同于空字符串
        assertThat(result.size()).isEqualTo(0);

        result= CollectionUtils.getDiff(map1,map2,false);//空值不等同于空字符串
        assertThat(result.get("cid001")).isEqualTo(new Object[]{"",null});
        assertThat(result.get("cid002")).isEqualTo(new Object[]{null,""});

    }
}
