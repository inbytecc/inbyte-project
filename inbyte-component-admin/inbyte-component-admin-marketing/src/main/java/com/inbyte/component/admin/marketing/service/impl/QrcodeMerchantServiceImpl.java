package com.inbyte.component.admin.marketing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.inbyte.component.admin.marketing.dao.QrcodeMerchantMapper;
import com.inbyte.component.admin.marketing.dao.QrcodeMerchantUserMapper;
import com.inbyte.component.admin.marketing.model.UserLocationBrief;
import com.inbyte.component.admin.marketing.model.UserTrendBrief;
import com.inbyte.component.admin.marketing.model.qrcode.*;
import com.inbyte.component.admin.marketing.service.QrcodeMerchantService;
import com.inbyte.component.admin.system.user.SessionUser;
import com.inbyte.component.admin.system.user.SessionUtil;
import com.inbyte.component.admin.system.user.dict.UserSourceTypeDict;
import com.inbyte.commons.model.dict.WhetherDict;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.util.PageUtil;
import com.inbyte.commons.util.StringUtil;
import com.inbyte.util.weixin.mp.client.WxMpLinkClient;
import com.inbyte.util.weixin.mp.client.WxMpQrCodeClient;
import com.inbyte.util.weixin.mp.client.WxMpSchemeClient;
import com.inbyte.util.weixin.mp.model.QrCodeGenerateParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 商户二维码服务
 *
 * @author chenjw
 * @date 2023-03-30 10:24:38
 **/
@Service
public class QrcodeMerchantServiceImpl implements QrcodeMerchantService {

    @Autowired
    private QrcodeMerchantMapper qrcodeMerchantMapper;
    @Autowired
    private QrcodeMerchantUserMapper qrcodeMerchantUserMapper;
    @Autowired
    private WxMpQrCodeClient wxMpQrCodeClient;
    @Autowired
    private WxMpSchemeClient wxMpSchemeClient;
    @Autowired
    private WxMpLinkClient wxMpLinkClient;

    @Value("${wx.miniapp.default}")
    private String defaultAppId;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R insert(QrcodeMerchantInsert insert) {
        SessionUser sessionUser = SessionUtil.getSessionUser();
        QrcodeMerchantPo qrcodeMerchantPo = QrcodeMerchantPo.builder()
                .mctNo(sessionUser.getMctNo())
                .createTime(LocalDateTime.now())
                .createUserId(SessionUtil.getUserId())
                .createUserName(SessionUtil.getUserName())
                .build();
        BeanUtils.copyProperties(insert, qrcodeMerchantPo);
        qrcodeMerchantMapper.insert(qrcodeMerchantPo);

        String scene = "q=" + qrcodeMerchantPo.getQcid() +
                "&t=" + UserSourceTypeDict.Merchant_Share.code +
                "&qr=true";

        if (StringUtil.isNotEmpty(insert.getScene())) {
            scene = scene + "&" + insert.getScene();
        }

        LambdaUpdateWrapper<QrcodeMerchantPo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(QrcodeMerchantPo::getQcid, qrcodeMerchantPo.getQcid());
        updateWrapper.set(QrcodeMerchantPo::getScene, scene);
        qrcodeMerchantMapper.update(null, updateWrapper);

        return R.ok("新增成功");
    }

    @Override
    public R delete(Integer qcid) {
        LambdaQueryWrapper<QrcodeMerchantPo> queryWrapper = new QueryWrapper<QrcodeMerchantPo>().lambda();
        queryWrapper.eq(QrcodeMerchantPo::getQcid, qcid);
        queryWrapper.eq(QrcodeMerchantPo::getMctNo, SessionUtil.getDefaultMctNo());
        qrcodeMerchantMapper.delete(queryWrapper);
        return R.ok("删除成功");
    }

    @Override
    public R update(QrcodeMerchantUpdate update) {
        QrcodeMerchantPo qrcodeMerchantPo = QrcodeMerchantPo.builder()
                .updateTime(LocalDateTime.now())
                .build();
        BeanUtils.copyProperties(update, qrcodeMerchantPo);

        LambdaQueryWrapper<QrcodeMerchantPo> queryWrapper = new LambdaQueryWrapper<QrcodeMerchantPo>();
        queryWrapper.eq(QrcodeMerchantPo::getQcid, update.getQcid());
        queryWrapper.eq(QrcodeMerchantPo::getMctNo, SessionUtil.getDefaultMctNo());
        qrcodeMerchantMapper.update(qrcodeMerchantPo, queryWrapper);
        return R.ok("修改成功");
    }

    @Override
    public R<QrcodeMerchantDetail> detail(Integer qcid) {
        return R.ok(qrcodeMerchantMapper.detail(qcid));
    }

    @Override
    public R<Page<QrcodeMerchantBrief>> list(QrcodeMerchantQuery query) {
        if (query.getEndDate() != null) {
            query.setEndDate(query.getEndDate().plusDays(1));
        }
        PageUtil.startPage(query);
        query.setMctNo(SessionUtil.getDefaultMctNo());
        return R.page(qrcodeMerchantMapper.list(query));
    }

    @Override
    public R<List<UserLocationBrief>> userDistribution(Integer qcid) {
        return R.ok(qrcodeMerchantUserMapper.userDistribution(qcid));
    }

    @Override
    public R<List<UserTrendBrief>> userTrend(QrcodeMerchantUserTrendQuery query) {
        return R.ok(qrcodeMerchantUserMapper.userTrend(query));
    }

    @Override
    public R<String> downloadQrCode(QrCodeDownloadParam param) {
        QrcodeMerchantDetail detail = qrcodeMerchantMapper.detail(param.getQcid());
        if (detail == null) {
            return R.failure("二维码ID不存在");
        }
        QrCodeGenerateParam qrCodeGenerateParam = new QrCodeGenerateParam();
        BeanUtils.copyProperties(param, qrCodeGenerateParam);
        qrCodeGenerateParam.setPage(detail.getPage());
        qrCodeGenerateParam.setScene(detail.getScene());
        return wxMpQrCodeClient.qrCodeBase64(defaultAppId, qrCodeGenerateParam);
    }

    @Override
    public R<String> getScheme(Integer qcid) {
        QrcodeMerchantDetail detail = qrcodeMerchantMapper.detail(qcid);
        if (detail == null) {
            return R.failure("二维码ID不存在");
        }
        return wxMpSchemeClient.generateScheme(defaultAppId, detail.getPage(), detail.getScene());
    }

    @Override
    public R<String> getUrlLink(Integer qcid) {
        QrcodeMerchantDetail detail = qrcodeMerchantMapper.detail(qcid);
        if (detail == null) {
            return R.failure("二维码ID不存在");
        }
        return wxMpLinkClient.generateUrlLink(defaultAppId, detail.getPage(), detail.getScene());
    }

    @Override
    public R<String> getShortLink(Integer qcid) {
        QrcodeMerchantDetail detail = qrcodeMerchantMapper.detail(qcid);
        if (detail == null) {
            return R.failure("二维码ID不存在");
        }
        return wxMpLinkClient.generateShortLink(defaultAppId,
                detail.getPage() + "?" + detail.getScene(),
                detail.getName(),
                WhetherDict.No);
    }
}
