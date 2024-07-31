package com.inbyte.component.admin.marketing.controller;

import com.inbyte.component.admin.marketing.model.UserLocationBrief;
import com.inbyte.component.admin.marketing.model.UserTrendBrief;
import com.inbyte.component.admin.marketing.model.qrcode.*;
import com.inbyte.component.admin.marketing.service.QrcodeMerchantService;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商户二维码
 *
 * @author chenjw
 * @date 2023-03-30 10:24:38
 **/
@RestController
@RequestMapping("qrcode/merchant")
public class QrcodeMerchantController {

    @Autowired
    private QrcodeMerchantService qrcodeMerchantService;

    /**
     * 新增
     *
     * @param insert
     * @return Result
     **/
    @PostMapping
    public R insert(@RequestBody @Valid QrcodeMerchantInsert insert) {
        return qrcodeMerchantService.insert(insert);
    }

    /**
     * 删除
     *
     * @param qcid
     * @return Result
     **/
    @DeleteMapping("{qcid}")
    public R delete(@PathVariable("qcid") Integer qcid) {
        return qrcodeMerchantService.delete(qcid);
    }

    /**
     * 更新
     *
     * @param update
     * @return Result
     **/
    @PutMapping
    public R update(@RequestBody @Valid QrcodeMerchantUpdate update) {
        return qrcodeMerchantService.update(update);
    }

    /**
     * 详情
     *
     * @param qcid
     * @return Result<QrcodeMerchantDetail>
     **/
    @GetMapping("{qcid}")
    public R<QrcodeMerchantDetail> detail(@PathVariable("qcid") Integer qcid) {
        return qrcodeMerchantService.detail(qcid);
    }

    /**
     * 列表
     *
     * @param query
     * @return Result<Page<List<QrcodeMerchantBrief>>>
     **/
    @GetMapping
    public R<Page<QrcodeMerchantBrief>> list(@ModelAttribute @Valid QrcodeMerchantQuery query) {
        return qrcodeMerchantService.list(query);
    }

    /**
     * 用户分布
     *
     * @return Result<List<UserLocationBrief>>
     **/
    @GetMapping("{qcid}/location")
    public R<List<UserLocationBrief>> userDistribution(@PathVariable("qcid") Integer qcid) {
        return qrcodeMerchantService.userDistribution(qcid);
    }

    /**
     * 访问量
     * 默认查询最近一个月
     * @param query
     * @return Result<List<UserLocationBrief>>
     **/
    @GetMapping("trend")
    public R<List<UserTrendBrief>> userTrend(@ModelAttribute @Valid QrcodeMerchantUserTrendQuery query) {
        return qrcodeMerchantService.userTrend(query);
    }

    /**
     * 二维码下载
     *
     * @param param
     * @return Result<List<UserLocationBrief>>
     **/
    @PostMapping("download")
    public R<String> downloadQrCode(@RequestBody @Valid QrCodeDownloadParam param) {
        return qrcodeMerchantService.downloadQrCode(param);
    }

    @GetMapping("{qcid}/scheme")
    public R<String> getScheme(@PathVariable("qcid") Integer qcid) {
        return qrcodeMerchantService.getScheme(qcid);
    }

    @GetMapping("{qcid}/url-link")
    public R<String> getUrlLink(@PathVariable("qcid") Integer qcid) {
        return qrcodeMerchantService.getUrlLink(qcid);
    }

    @GetMapping("{qcid}/short-link")
    public R<String> getShortLink(@PathVariable("qcid") Integer qcid,
                                  @RequestParam(required = false, defaultValue = "0") Integer showQrName) {
        return qrcodeMerchantService.getShortLink(qcid, showQrName);
    }

}
