package com.hcxinan.core.inte;

import com.hcxinan.core.testdemo.ICollectServiceTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

/**
*@Description 采用mock的方式测试接口
*@Param 
*@Return 
*@Author liudk
*@DateTime 20-10-31 下午2:57
*/
@RunWith(MockitoJUnitRunner.class)
public class ICollectServiceMockTest extends ICollectServiceTest {
    @Override
    public void initializationData() {
        collectService=spy(ICollectService.class);

        List<String> mockSourceDatas=new ArrayList<String>(){{
            add("新增的源数据1");
            add("更新的源数据2");
            add("更新的源数据3");
        }};
        when(collectService.getShouldConvertSourceDatas()).thenReturn(mockSourceDatas);
        when(collectService.getProviderName()).thenReturn("mock模拟的工具");
        /*
         * 构造数据区分器接口，由于本测试的重点对象是ICollectService，所以对区分器只做模拟即可，忽略其中的分析过程，
         * 对区分器的单位测试是需要另外建其他的单位测试实例进行，这样避免耦合，符合单元测试思想
         * */
        IDataDiscriminator dataDiscriminator=mock(IDataDiscriminator.class);
        when(dataDiscriminator.getAddedDatas()).thenReturn(mockSourceDatas.subList(0,1));
        when(dataDiscriminator.getUpdatedDatas()).thenReturn(mockSourceDatas.subList(1,3));

        Map<String,String> distMap=new HashMap<String,String>(){{
            put("id","ID001");
            put("name","新增的源数据1");
        }};
        //构造数据转换器
        IDataConvertor dataConvertor=mock(IDataConvertor.class);
//        when(dataConvertor.convert(any())).thenReturn(distMap);

        // when
        when(collectService.getDataDiscriminator(anyList())).thenReturn(dataDiscriminator);
        when(collectService.getDataConvertor()).thenReturn(dataConvertor);
    }

    @Test
    public void saveSourceToDist() {
        super.saveSourceToDist();
    }
}
