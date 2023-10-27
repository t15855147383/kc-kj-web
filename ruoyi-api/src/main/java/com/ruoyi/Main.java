package com.ruoyi;

import com.ruoyi.framework.web.domain.server.Sys;
import com.tencent.wework.Finance;

import java.io.IOException;
import java.text.ParseException;

public class Main {
    public static void main(String[] arge) {
        System.out.println("111");
        System.out.println(System.getProperty("java.library.path"));
        long ret = 0;
        long sdk = Finance.NewSdk();
        ret = Finance.Init(sdk, "wwd08c8e7c775ab44d", "zJ6k0naVVQ--gt9PUSSEvs03zW_nlDVmjLCTOTAfrew");
        System.out.println(ret);
    }
}