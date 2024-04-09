package com.inbyte.component.admin.marketing.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.admin.marketing.model.qrcode.QrcodeMerchantBrief;
import com.inbyte.component.admin.marketing.model.qrcode.QrcodeMerchantDetail;
import com.inbyte.component.admin.marketing.model.qrcode.QrcodeMerchantPo;
import com.inbyte.component.admin.marketing.model.qrcode.QrcodeMerchantQuery;

import java.util.List;

/**
 * 商户二维码
 *
 * 表名：  qrcode_merchant
 * @author chenjw
 * @date 2023-03-30 10:24:38
 */
public interface QrcodeMerchantMapper extends BaseMapper<QrcodeMerchantPo> {

    /**
     * 详情
     *
     * @param qcid
     * @return QrcodeMerchantDetail
     **/
    QrcodeMerchantDetail detail(Integer qcid);

    /**
     * 查询列表
     * @param query
     * @return List<QrcodeMerchantBrief>
     **/
    List<QrcodeMerchantBrief> list(QrcodeMerchantQuery query);
}
