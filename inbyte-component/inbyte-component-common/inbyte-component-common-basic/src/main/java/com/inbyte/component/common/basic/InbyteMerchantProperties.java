package com.inbyte.component.common.basic;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "inbyte.merchant")
public class InbyteMerchantProperties {


    private String mctName;
    private String mctNo;

    public String getMctName() {
        return mctName;
    }

    public void setMctName(String mctName) {
        this.mctName = mctName;
    }

    public String getMctNo() {
        return mctNo;
    }

    public void setMctNo(String mctNo) {
        this.mctNo = mctNo;
    }
}