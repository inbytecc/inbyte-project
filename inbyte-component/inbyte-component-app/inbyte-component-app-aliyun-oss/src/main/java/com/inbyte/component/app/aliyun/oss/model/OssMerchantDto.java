package com.inbyte.component.app.aliyun.oss.model;

/**
 * App 信息
 *
 * @author chenjw
 * @date 2022/12/31
 **/
public class OssMerchantDto {

    /** 所属商户名 */
    private String mctPinyinName;

    /** 所属商户 ID */
    private String mctNo;

    public String getMctNo() {
        return mctNo;
    }

    public void setMctNo(String mctNo) {
        this.mctNo = mctNo;
    }

    public String getMctPinyinName() {
        return mctPinyinName;
    }

    public void setMctPinyinName(String mctPinyinName) {
        this.mctPinyinName = mctPinyinName;
    }
}
