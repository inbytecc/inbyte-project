package com.inbyte.component.admin.marketing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.inbyte.commons.exception.InbyteException;
import com.inbyte.commons.model.dict.Whether;
import com.inbyte.commons.model.enums.AppTypeEnum;
import com.inbyte.component.admin.marketing.dao.MarketingQrcodeMerchantMapper;
import com.inbyte.component.admin.marketing.dao.MarketingQrcodeMerchantUserMapper;
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
import com.inbyte.component.common.basic.dao.InbyteAppMapper;
import com.inbyte.component.common.basic.model.InbyteAppPo;
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
    private MarketingQrcodeMerchantMapper marketingQrcodeMerchantMapper;
    @Autowired
    private MarketingQrcodeMerchantUserMapper marketingQrcodeMerchantUserMapper;
    @Autowired
    private InbyteAppMapper appMapper;
    @Autowired
    private WxMpQrCodeClient wxMpQrCodeClient;
    @Autowired
    private WxMpSchemeClient wxMpSchemeClient;
    @Autowired
    private WxMpLinkClient wxMpLinkClient;

    @Value("${wx.miniapp.default:#{null}}")
    private String defaultAppId;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R insert(QrcodeMerchantInsert insert) {
        SessionUser sessionUser = SessionUtil.getSessionUser();
        MarketingQrcodeMerchantPo marketingQrcodeMerchantPo = MarketingQrcodeMerchantPo.builder()
                .mctNo(sessionUser.getMctNo())
                .createTime(LocalDateTime.now())
                .creator(SessionUtil.getUserName())
                .build();
        BeanUtils.copyProperties(insert, marketingQrcodeMerchantPo);
        marketingQrcodeMerchantMapper.insert(marketingQrcodeMerchantPo);

        String scene = "q=" + marketingQrcodeMerchantPo.getQcid() +
                "&t=" + UserSourceTypeDict.Merchant_Share.code +
                "&qr=true";

        if (StringUtil.isNotEmpty(insert.getScene())) {
            scene = scene + "&" + insert.getScene();
        }

        LambdaUpdateWrapper<MarketingQrcodeMerchantPo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(MarketingQrcodeMerchantPo::getQcid, marketingQrcodeMerchantPo.getQcid());
        updateWrapper.set(MarketingQrcodeMerchantPo::getScene, scene);
        marketingQrcodeMerchantMapper.update(null, updateWrapper);

        return R.ok("新增成功");
    }

    @Override
    public R delete(Integer qcid) {
        LambdaQueryWrapper<MarketingQrcodeMerchantPo> queryWrapper = new QueryWrapper<MarketingQrcodeMerchantPo>().lambda();
        queryWrapper.eq(MarketingQrcodeMerchantPo::getQcid, qcid);
        queryWrapper.eq(MarketingQrcodeMerchantPo::getMctNo, SessionUtil.getMctNo());
        marketingQrcodeMerchantMapper.delete(queryWrapper);
        return R.ok("删除成功");
    }

    @Override
    public R update(QrcodeMerchantUpdate update) {
        MarketingQrcodeMerchantPo marketingQrcodeMerchantPo = MarketingQrcodeMerchantPo.builder()
                .updateTime(LocalDateTime.now())
                .build();
        BeanUtils.copyProperties(update, marketingQrcodeMerchantPo);

        LambdaQueryWrapper<MarketingQrcodeMerchantPo> queryWrapper = new LambdaQueryWrapper<MarketingQrcodeMerchantPo>();
        queryWrapper.eq(MarketingQrcodeMerchantPo::getQcid, update.getQcid());
        queryWrapper.eq(MarketingQrcodeMerchantPo::getMctNo, SessionUtil.getMctNo());
        marketingQrcodeMerchantMapper.update(marketingQrcodeMerchantPo, queryWrapper);
        return R.ok("修改成功");
    }

    @Override
    public R<QrcodeMerchantDetail> detail(Integer qcid) {
        return R.ok(marketingQrcodeMerchantMapper.detail(qcid));
    }

    @Override
    public R<Page<QrcodeMerchantBrief>> list(QrcodeMerchantQuery query) {
        if (query.getEndDate() != null) {
            query.setEndDate(query.getEndDate().plusDays(1));
        }
        PageUtil.startPage(query);
        query.setMctNo(SessionUtil.getMctNo());
        return R.page(marketingQrcodeMerchantMapper.list(query));
    }

    @Override
    public R<List<UserLocationBrief>> userDistribution(Integer qcid) {
        return R.ok(marketingQrcodeMerchantUserMapper.userDistribution(qcid));
    }

    @Override
    public R<List<UserTrendBrief>> userTrend(QrcodeMerchantUserTrendQuery query) {
        query.setMctNo(SessionUtil.getMctNo());
        return R.ok(marketingQrcodeMerchantUserMapper.userTrend(query));
    }

    @Override
    public R<String> downloadQrCode(QrCodeDownloadParam param) {
        QrcodeMerchantDetail detail = marketingQrcodeMerchantMapper.detail(param.getQcid());
        if (detail == null) {
            return R.failure("二维码ID不存在");
        }
        QrCodeGenerateParam qrCodeGenerateParam = new QrCodeGenerateParam();
        BeanUtils.copyProperties(param, qrCodeGenerateParam);
        qrCodeGenerateParam.setPage(detail.getPage());
        qrCodeGenerateParam.setScene(detail.getScene());
        return wxMpQrCodeClient.qrCodeBase64(getAppId(), qrCodeGenerateParam);
    }

    @Override
    public R<String> getScheme(Integer qcid) {
        QrcodeMerchantDetail detail = marketingQrcodeMerchantMapper.detail(qcid);
        if (detail == null) {
            return R.failure("二维码ID不存在");
        }
        return wxMpSchemeClient.generateScheme(getAppId(), detail.getPage(), detail.getScene());
    }

    @Override
    public R<String> getUrlLink(Integer qcid) {
        QrcodeMerchantDetail detail = marketingQrcodeMerchantMapper.detail(qcid);
        if (detail == null) {
            return R.failure("二维码ID不存在");
        }
        return wxMpLinkClient.generateUrlLink(getAppId(), detail.getPage(), detail.getScene());
    }

    @Override
    public R<String> getShortLink(Integer qcid, Integer showQrName) {
        QrcodeMerchantDetail detail = marketingQrcodeMerchantMapper.detail(qcid);
        if (detail == null) {
            return R.failure("二维码ID不存在");
        }
        return wxMpLinkClient.generateShortLink(getAppId(),
                detail.getPage() + "?" + detail.getScene(),
                showQrName == Whether.Yes ? detail.getName() : "",
                WhetherDict.No);
    }

    private String getAppId() {
        if (StringUtil.isNotEmpty(defaultAppId)) {
            return defaultAppId;
        }

        InbyteAppPo inbyteAppPo = appMapper.selectOne(new LambdaQueryWrapper<InbyteAppPo>()
                .eq(InbyteAppPo::getAppType, AppTypeEnum.WXMP)
                .eq(InbyteAppPo::getMctNo, SessionUtil.getMctNo())
                .last("limit 1"));
        if (inbyteAppPo == null) {
            throw InbyteException.failure("当前商户未配置微信小程序");
        }
        if (StringUtil.isEmpty(inbyteAppPo.getAppId())) {
            throw InbyteException.failure("当前商户微信小程序信息未完善");
        }
        return inbyteAppPo.getAppId();
    }

}
