package com.hcxinan.core.util;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class CollectionUtils {
    /**
     *@Description 对两个map进行差异项对比，如果不一致，那么将以键和数组的形式返回结果
     *@Param [source, dist, isNullEqBlank]
     *@Return java.util.Map<K,V[]>
     *@Author liudk
     *@DateTime 21-8-10 下午5:17
    */
    public static <K,V> Map<K,V[]> getDiff(Map<K,V> source,Map<K,V> dist,boolean isNullEqBlank){
        Map<K,V[]> resultMap=new LinkedHashMap<>();
        if(source==null && dist==null){
            return resultMap;
        }else{
            source=source==null?new HashMap():source;
            dist=dist==null?new HashMap():dist;
        }

        Set sameKeys=new HashSet();//记录相同的键

        V source_val=null;
        V dist_val=null;
        for(K key:source.keySet()){
            sameKeys.add(key);
            source_val=source.get(key);
            dist_val=dist.get(key);

            if((source_val==null && dist_val==null) || (source_val!=null && source_val.equals(dist_val))){//值相同
                continue;
            }else{
                if(isNullEqBlank){//空值与空字符串是等价，那么一个是空，另一个是空字符也是认为是相等的
                    if(source_val==null && StringUtils.isBlank(dist_val+"")){
                        continue;
                    }
                    if(dist_val==null && StringUtils.isBlank(source_val+"")){
                        continue;
                    }
                }
                resultMap.put(key, (V[]) new Object[]{source_val,dist_val});
            }
        }
        for(K key:dist.keySet()){
            if(!sameKeys.contains(key)){
                /*
                * 之前没有对比过的，这肯定是dist比source多出的，在source必然是空，但是如果是isNullEqBlank=true时，
                * 那么在dist里如果是空字符串，那么是认为相等的,所以被判定为不相等的情况是：
                * isNullEqBlank=false（因为此时是多出的，如果任务空值不等价与空字符串，那么必然是不等的） 或 isNullEqBlank=true时，dist_val并不是空字符串
                * */
                dist_val=dist.get(key);
                if(!isNullEqBlank || StringUtils.isNotBlank(dist_val+"")){
                    resultMap.put(key, (V[]) new Object[]{null,dist_val});
                }
            }
        }
        return resultMap;
    }
}
