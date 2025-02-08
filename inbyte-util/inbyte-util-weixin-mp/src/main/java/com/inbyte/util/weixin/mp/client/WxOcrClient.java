package com.inbyte.util.weixin.mp.client;

import com.inbyte.commons.model.dict.WhetherDict;
import me.chanjar.weixin.common.bean.ocr.*;

/**
 * 微信OCR客户端
 * 服务商模式
 */
public interface WxOcrClient {

    /**
     * 通用印刷体识别
     *
     * @param appId 小程序appId
     * @param imgUrl 图片地址
     * @return OCR识别结果
     */
    WxOcrCommResult commonOcr(String appId, String imgUrl);

    /**
     * 行驶证识别
     *
     * @param appId 小程序appId
     * @param imgUrl 图片地址
     * @return OCR识别结果
     */
    WxOcrDrivingResult vehicleLicenseOcr(String appId, String imgUrl);

    /**
     * 银行卡识别
     *
     * @param appId 小程序appId
     * @param imgUrl 图片地址
     * @return OCR识别结果
     */
    WxOcrBankCardResult bankCardOcr(String appId, String imgUrl);

    /**
     * 营业执照识别
     *
     * @param appId 小程序appId
     * @param imgUrl 图片地址
     * @return OCR识别结果
     */
    WxOcrBizLicenseResult businessLicenseOcr(String appId, String imgUrl);

    /**
     * 驾驶证识别
     *
     * @param appId 小程序appId
     * @param imgUrl 图片地址
     * @return OCR识别结果
     */
    WxOcrDrivingLicenseResult driverLicenseOcr(String appId, String imgUrl);

    /**
     * 身份证识别
     *
     * @param appId 小程序appId
     * @param imgUrl 图片地址
     * @param isFront 是否正面，true-正面，false-反面
     * @return OCR识别结果
     */
    WxOcrIdCardResult idCardOcr(String appId, String imgUrl, Boolean isFront);
}
