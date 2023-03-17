package com.hcxinan.core.util;

import com.alibaba.fastjson.JSON;
import com.hcxinan.core.inte.util.ITree;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author liudk
 * @Description:
 * @date 21-8-26 上午10:59
 */
public class TreeDataTest {

    @Test
    public void treeData() {
        List<Map<String,String>> datas=new ArrayList<>();
        Map<String,String> m1=new HashMap(){{
            put("id","001");
            put("name","节点1");
        }};
        datas.add(m1);
        Map<String,String> m2=new HashMap(){{
            put("id","002");
            put("name","节点2");
        }};
        datas.add(m2);
        Map<String,String> m3=new HashMap(){{
            put("id","003");
            put("name","节点3");
            put("pid","001");
        }};
        datas.add(m3);
        List<ITree<String,Map<String,String>>> treeDatas= TreeData.getTreeData(datas,
                map->new TreeData(map.get("id"),map.get("pid"),map.get("name")));
        assertThat(treeDatas.size()).isEqualTo(2);
        assertThat(treeDatas.get(0).getId()).isEqualTo("001");
        assertThat(treeDatas.get(0).getLabel()).isEqualTo("节点1");
        assertThat(treeDatas.get(0).getChildren().get(0).getId()).isEqualTo("003");
        assertThat(treeDatas.get(0).getChildren().get(0).getLabel()).isEqualTo("节点3");
        String s=JSON.toJSONString(treeDatas,true);
        System.out.println(s);
    }
}