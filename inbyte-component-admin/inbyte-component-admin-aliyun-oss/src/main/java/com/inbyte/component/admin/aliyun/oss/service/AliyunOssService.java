package com.inbyte.component.admin.aliyun.oss.service;

import com.inbyte.component.admin.aliyun.oss.model.AliYunOssSignDto;
import com.inbyte.component.admin.aliyun.oss.model.AliYunOssSignParam;
import com.inbyte.commons.model.dto.R;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 阿里云授权
 *
 * @author: chenjw
 * @date: 2023/3/14
 */
public interface AliyunOssService {

    /**
     * 获取OSS授权
     *
     * <p>获取STS参考文档：https://help.aliyun.com/document_detail/100624.html?spm=a2c4g.11186623.2.10.455750fdskCKbP#concept-xzh-nzk-2gb
     * <p>JS上传文件参考文档：https://help.aliyun.com/document_detail/31926.html
     * https://help.aliyun.com/document_detail/64041.html?spm=a2c4g.11186623.6.1323.2cff3b49yokLxd
     */
    R<AliYunOssSignDto> getCredential(AliYunOssSignParam param);

    /**
     * Post请求
     */
    void callback(HttpServletRequest request, HttpServletResponse response) throws IOException;


}