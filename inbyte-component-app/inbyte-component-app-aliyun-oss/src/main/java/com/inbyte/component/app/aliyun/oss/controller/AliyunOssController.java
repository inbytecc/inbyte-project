package com.inbyte.component.app.aliyun.oss.controller;

import com.inbyte.component.app.aliyun.oss.model.AliYunOssSignDto;
import com.inbyte.component.app.aliyun.oss.model.AliYunOssSignParam;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.app.aliyun.oss.service.AliyunOssService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * 阿里云对象存储
 *
 * @author chenjw
 * @date 2020/8/11
 **/
@RestController
@RequestMapping("aliyun/oss")
public class AliyunOssController {

    @Autowired
    private AliyunOssService aliyunOssService;

    /**
     * 获取OSS授权
     *
     * <p>获取STS参考文档：https://help.aliyun.com/document_detail/100624.html?spm=a2c4g.11186623.2.10.455750fdskCKbP#concept-xzh-nzk-2gb
     * <p>JS上传文件参考文档：https://help.aliyun.com/document_detail/31926.html
     * https://help.aliyun.com/document_detail/64041.html?spm=a2c4g.11186623.6.1323.2cff3b49yokLxd
     */
    @CrossOrigin
    @PostMapping("credential")
    public R<AliYunOssSignDto> getCredential(@RequestBody @Valid AliYunOssSignParam param) {
        return aliyunOssService.getCredential(param);
    }

    /**
     * 回调通知
     * @param request
     * @param response
     */
    @PostMapping("callback")
    public void callback(HttpServletRequest request, HttpServletResponse response) {
        try {
            aliyunOssService.callback(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

