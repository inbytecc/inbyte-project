package com.inbyte.component.admin.marketing.service;

import com.inbyte.component.admin.marketing.model.UserLocationBrief;
import com.inbyte.component.admin.marketing.model.UserTrendBrief;
import com.inbyte.component.admin.marketing.model.qrcode.*;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;

import java.util.List;

/**
 * 商户二维码服务
 *
 * @author chenjw
 * @date 2023-03-30 10:24:38
 **/
public interface QrcodeMerchantService {

    /**
     * 新增
     *
     * @param insert
     * @return Result
     **/
    R insert(QrcodeMerchantInsert insert);

    /**
     * 删除
     *
     * @param qcid
     * @return Result
     **/
    R delete(Integer qcid);

    /**
     * 修改
     *
     * @param update
     * @return Result
     **/
    R update(QrcodeMerchantUpdate update);

    /**
     * 详情
     *
     * @param qcid
     * @return QrcodeMerchantDetail
     **/
    R<QrcodeMerchantDetail> detail(Integer qcid);

    /**
     * 列表
     *
     * @param query
     * @return Result<Page<List<QrcodeMerchantBrief>>>
     **/
    R<Page<QrcodeMerchantBrief>> list(QrcodeMerchantQuery query);

    R<List<UserLocationBrief>> userDistribution(Integer qcid);

    R<List<UserTrendBrief>> userTrend(QrcodeMerchantUserTrendQuery query);

    R<String> downloadQrCode(QrCodeDownloadParam param);

    R<String> getScheme(Integer qcid);

    R<String> getUrlLink(Integer qcid);

    R<String> getShortLink(Integer qcid, Integer showQrName);
}
