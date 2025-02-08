package com.inbyte.util.weixin.mp.open.client;

import com.inbyte.util.weixin.mp.client.WxOcrClient;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.ocr.*;
import me.chanjar.weixin.open.api.WxOpenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 微信OCR开放平台客户端实现
 * 服务商模式
 *
 * @date: 2024/1/5
 */
@Slf4j
@Component
public class WxOcrOpenClient implements WxOcrClient {

    @Autowired
    private WxOpenService wxOpenService;

    @Override
    public WxOcrCommResult commonOcr(String appId, String imgUrl) {
        try {
            WxOcrCommResult result = wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(appId).getOcrService().comm(imgUrl);
            log.info("通用印刷体识别结果: {}", result);
            return result;
        } catch (Exception e) {
            log.error("通用印刷体识别失败", e);
            throw new RuntimeException("通用印刷体识别失败");
        }
    }

    @Override
    public WxOcrDrivingResult vehicleLicenseOcr(String appId, String imgUrl) {
        try {
            WxOcrDrivingResult driving = wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(appId).getOcrService().driving(imgUrl);
            log.info("行驶证识别结果: {}", driving);
            return driving;
        } catch (Exception e) {
            log.error("行驶证识别失败", e);
            throw new RuntimeException("行驶证识别失败");
        }
    }

    @Override
    public WxOcrBankCardResult bankCardOcr(String appId, String imgUrl) {
        try {
            WxOcrBankCardResult result = wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(appId).getOcrService().bankCard(imgUrl);
            log.info("银行卡识别结果: {}", result);
            return result;
        } catch (Exception e) {
            log.error("银行卡识别失败", e);
            throw new RuntimeException("银行卡识别失败");
        }
    }

    @Override
    public WxOcrBizLicenseResult businessLicenseOcr(String appId, String imgUrl) {
        try {
            WxOcrBizLicenseResult result = wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(appId).getOcrService().bizLicense(imgUrl);
            log.info("营业执照识别结果: {}", result);
            return result;
        } catch (Exception e) {
            log.error("营业执照识别失败", e);
            throw new RuntimeException("营业执照识别失败");
        }
    }

    @Override
    public WxOcrDrivingLicenseResult driverLicenseOcr(String appId, String imgUrl) {
        try {
            WxOcrDrivingLicenseResult result = wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(appId).getOcrService().drivingLicense(imgUrl);
            log.info("驾驶证识别结果: {}", result);
            return result;
        } catch (Exception e) {
            log.error("驾驶证识别失败", e);
            throw new RuntimeException("驾驶证识别失败");
        }
    }

    @Override
    public WxOcrIdCardResult idCardOcr(String appId, String imgUrl, Boolean isFront) {
        try {
            WxOcrIdCardResult result = wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(appId).getOcrService().idCard(imgUrl);
            log.info("身份证识别结果: {}", result);
            return result;
        } catch (Exception e) {
            log.error("身份证识别失败", e);
            throw new RuntimeException("身份证识别失败");
        }
    }
} 