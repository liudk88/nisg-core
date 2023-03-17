package com.hcxinan.core.util.ip;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class IpCompareImpTest {
    @Test
    public void compileIpExp(){
//        IPScope scope=IpCompareImp.compileIpExp("192.168.22.1/18");
        IPScope scope=IpCompareImp.compileIpExp("192.168.0.*");
        System.out.println(scope.getStart());
        System.out.println(scope.getEnd());

        List<String> ips = scope.getIpList();
        for(String ip:ips){
            System.out.println(ip);
        }

        System.out.println(Ipv4Utils.ipToInt("192.168.0.1"));
        System.out.println(Ipv4Utils.ipToInt("192.168.63.254"));
    }

}