package com.hcxinan.core.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ChineseUtilTest {
    public void getPingYin(){

    }
    @Test
    public void getPinYinHeadChar(){
        String str=null;
        str=ChineseUtil.getPinYinHeadChar("北京师范大学");
        assertThat(str).isEqualTo("bjsfdx");

        str=ChineseUtil.getPinYinHeadChar("北京师 范大学");
        assertThat(str).isEqualTo("bjs fdx");

        str=ChineseUtil.getPinYinHeadChar("北京师1范大学ab");
        assertThat(str).isEqualTo("bjs1fdxab");
    }
}
