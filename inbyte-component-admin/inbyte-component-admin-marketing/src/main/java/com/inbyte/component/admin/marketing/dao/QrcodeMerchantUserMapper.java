package com.inbyte.component.admin.marketing.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.admin.marketing.model.UserLocationBrief;
import com.inbyte.component.admin.marketing.model.UserTrendBrief;
import com.inbyte.component.admin.marketing.model.qrcode.QrcodeMerchantUserTrendQuery;
import com.inbyte.component.admin.marketing.model.qrcode.user.QrcodeMerchantUserBrief;
import com.inbyte.component.admin.marketing.model.qrcode.user.QrcodeMerchantUserDetail;
import com.inbyte.component.admin.marketing.model.qrcode.user.QrcodeMerchantUserPo;
import com.inbyte.component.admin.marketing.model.qrcode.user.QrcodeMerchantUserQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商户二维码注册用户
 *
 * 表名：  qrcode_merchant_user
 * @author chenjw
 * @date 2023-04-04 16:39:21
 */
public interface QrcodeMerchantUserMapper extends BaseMapper<QrcodeMerchantUserPo> {

    /**
     * 详情
     *
     * @param qmUserId
     * @return QrcodeMerchantRegisterDetail
     **/
    QrcodeMerchantUserDetail detail(Integer qmUserId);

    /**
     * 查询列表
     * @param query
     * @return List<QrcodeMerchantRegisterBrief>
     **/
    List<QrcodeMerchantUserBrief> list(QrcodeMerchantUserQuery query);

    /**
     * 查询列表
     * @param qcid
     * @return List<UserLocationBrief>
     **/
    List<UserLocationBrief> userDistribution(@Param("qcid") Integer qcid);

    /**
     * 用户增长趋势
     *
     * @param query
     * @return
     */
    List<UserTrendBrief> userTrend(QrcodeMerchantUserTrendQuery query);
}
