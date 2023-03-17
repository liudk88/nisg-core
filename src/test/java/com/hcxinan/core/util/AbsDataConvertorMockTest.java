package com.hcxinan.core.util;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
*@Description
*@Param
*@Return
*@Author liudk
*@DateTime 20-11-1 下午5:12
*/
@RunWith(MockitoJUnitRunner.class)
public class AbsDataConvertorMockTest {
    private static final Logger log = Logger.getLogger(AbsDataConvertor.class);
    @Test
    public void test(){
        log.info("=========测试AbsDataConvertor=========");
        AbsDataConvertor<String[], Map> convertor=new AbsDataConvertor<String[], Map>() {
            @Override
            public Map<String, Object> buildRelation(String[] source, Map dist) {
                return new HashMap<String, Object>(){{
                    put("id",dist.get("id"));
                    put("source",source);
                }};
            }
            @Override
            public Map convert(String[] source) {
                Map map=new HashMap(){{
                    put("id","id"+source[0]);
                    put("name",source[1]);
                }};
                return map;
            }
        };
        String[] arr1=new String[]{"001","漏洞1"};
        String[] arr2=new String[]{"002","漏洞2"};
        String[] arr3=new String[]{"003","漏洞3"};
        Map m1=convertor.convert(arr1);
        log.info("测试不留痕迹的转换");
        assertThat(m1.get("id")).isEqualTo("id001");
        assertThat(m1.get("name")).isEqualTo("漏洞1");
        assertThat(convertor.getMark(arr1)).isEqualTo(null);

        log.info("测试留痕迹的转换");
        Map m2=convertor.convertWithMark(arr2);
        Map m3=convertor.convertWithMark(arr3);
        assertThat(convertor.getMark(arr2).get("id")).isEqualTo("id"+arr2[0]);
        assertThat(convertor.getMark(arr3).get("source")).isEqualTo(arr3);
    }
}
