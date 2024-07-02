package com.inbyte.component.admin.aliyun.oss.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.aliyun.oss.*;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.inbyte.component.admin.aliyun.oss.dao.ObjectStorageMapper;
import com.inbyte.component.admin.aliyun.oss.model.AliYunOssSignDto;
import com.inbyte.component.admin.aliyun.oss.model.AliYunOssSignParam;
import com.inbyte.commons.model.enums.UploadByEnum;
import com.inbyte.component.admin.aliyun.oss.model.object.storage.InbyteObjectStoragePo;
import com.inbyte.component.admin.aliyun.oss.service.AliyunOssService;
import com.inbyte.commons.model.dict.WhetherDict;
import com.inbyte.commons.model.dto.R;
import com.inbyte.commons.util.StringUtil;
import com.inbyte.commons.util.WebUtil;
import com.inbyte.component.admin.system.user.SessionUser;
import com.inbyte.component.admin.system.user.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLDecoder;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * 阿里云授权
 *
 * @author: chenjw
 * @date: 2023/3/14
 */
@Slf4j
@Service
public class AliyunOssServiceImpl implements AliyunOssService {

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.AccessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${aliyun.oss.bucket}")
    private String bucketName;
    @Value("${inbyte.app.server}")
    private String server;

    @Autowired
    private ObjectStorageMapper objectStorageMapper;

    /**
     * 获取OSS授权
     *
     * <p>获取STS参考文档：https://help.aliyun.com/document_detail/100624.html?spm=a2c4g.11186623.2.10.455750fdskCKbP#concept-xzh-nzk-2gb
     * <p>JS上传文件参考文档：https://help.aliyun.com/document_detail/31926.html
     * https://help.aliyun.com/document_detail/64041.html?spm=a2c4g.11186623.6.1323.2cff3b49yokLxd
     */
    public R<AliYunOssSignDto> getCredential(AliYunOssSignParam param) {
        LocalDateTime now = LocalDateTime.now();
        SessionUser sessionUser = SessionUtil.getSessionUser();


        String fileName = param.getFileName().replaceAll("[^\\p{L}\\p{N}]+", "");
        /**
         * 文件格式
         * 商户空间/商户名/模块路径/年/月/日/模块参数/防重复随机数
         */
        String dir = new StringBuilder()
                .append("mct-space/")
                .append(sessionUser.getMctPinYinName()).append("/")
                .append(param.getPath()).append("/")
                .append(now.getYear()).append("/")
                .append(now.getMonthValue()).append("/")
                .append(now.getDayOfMonth()).append("/")
                .append(new Random().nextInt(100000)).append("-")
                .append(StringUtil.defaultIfEmpty(fileName, ""))
                .toString()
                .replace("//", "/");

        // 资源访问路径
        String host = "https://" + bucketName + "." + endpoint + "/" + dir;

        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            InbyteObjectStoragePo inbyteObjectStoragePo = InbyteObjectStoragePo.builder()
                    .url(host)
                    .endPoint(endpoint)
                    .fileType(param.getFileType())
                    .uploadBy(UploadByEnum.MERCHANT)
                    .bucket(bucketName)
                    .name(fileName)
                    .path(param.getPath())
                    .mctNo(sessionUser.getMctNo())
                    .createTime(now)
                    .creator(sessionUser.getUserName())
                    .build();
            objectStorageMapper.insert(inbyteObjectStoragePo);

            long expireTime = 10;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConditions = new PolicyConditions();
            policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConditions.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = client.generatePostPolicy(expiration, policyConditions);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = client.calculatePostSignature(postPolicy);

            JSONObject jasonCallback = new JSONObject();
            jasonCallback.put("callbackUrl", server + "/api/aliyun/oss/callback");
            jasonCallback.put("callbackBody",
                    "object=${object}&" +
                            "size=${size}&" +
                            "etag=${etag}&" +
                            "mimeType=${mimeType}&" +
                            "height=${imageInfo.height}&" +
                            "width=${imageInfo.width}&" +
                            "objectId=" + inbyteObjectStoragePo.getObjectId());
            jasonCallback.put("callbackBodyType", "application/x-www-form-urlencoded");
            String base64CallbackBody = BinaryUtil.toBase64String(jasonCallback.toString().getBytes());

            AliYunOssSignDto aliYunOssSignDto = AliYunOssSignDto.builder()
                    .accessId(accessKeyId)
                    .policy(encodedPolicy)
                    .signature(postSignature)
                    .dir(dir)
                    .host("https://" + bucketName + "." + endpoint)
                    .expire(expireEndTime / 1000)
                    .callback(base64CallbackBody)
                    .build();
            return R.ok(aliYunOssSignDto);
        } catch (Exception e) {
            log.error("获取阿里云 OSS 文件上传授权异常", e);
            return R.failure("获取授权失败");
        }
    }


    /**
     * 获取public key
     *
     * @param url
     * @return
     */
    @SuppressWarnings({"finally"})
    private String executeGet(String url) {
        BufferedReader in = null;

        String content = null;
        try {
            // 定义HttpClient
            @SuppressWarnings("resource")
            DefaultHttpClient client = new DefaultHttpClient();
            // 实例化HTTP方法
            HttpGet request = new HttpGet();
            request.setURI(new URI(url));
            HttpResponse response = client.execute(request);

            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            content = sb.toString();
        } catch (Exception e) {
            log.error("阿里云OSS, GET请求错误", e);
        } finally {
            if (in != null) {
                try {
                    in.close();// 最后要关闭BufferedReader
                } catch (Exception e) {
                    log.error("阿里云OSS, 关闭流", e);
                }
            }
            return content;
        }
    }

    /**
     * 验证上传回调的Request
     *
     * @param request
     * @param ossCallbackBody
     * @return
     * @throws NumberFormatException
     * @throws IOException
     */
    protected boolean VerifyOSSCallbackRequest(HttpServletRequest request, String ossCallbackBody)
            throws NumberFormatException, IOException {
        boolean ret = false;
        String authorizationInput = request.getHeader("Authorization");
        String pubKeyInput = request.getHeader("x-oss-pub-key-url");
        byte[] authorization = BinaryUtil.fromBase64String(authorizationInput);
        byte[] pubKey = BinaryUtil.fromBase64String(pubKeyInput);
        String pubKeyAddr = new String(pubKey);
        if (!pubKeyAddr.startsWith("http://gosspublic.alicdn.com/")
                && !pubKeyAddr.startsWith("https://gosspublic.alicdn.com/")) {
            log.info("pub key addr must be oss addrss");
            return false;
        }
        String retString = executeGet(pubKeyAddr);
        retString = retString.replace("-----BEGIN PUBLIC KEY-----", "");
        retString = retString.replace("-----END PUBLIC KEY-----", "");
        String queryString = request.getQueryString();
        /**
         * 特别注意： 因为接口签名时需要以 /api 前缀访问回调接口, 且 Nginx 反向代理时去掉了 /api 前缀, 导致 doCheck 验证不通过
         * 所以此处补充 /api 前缀，使校验通过
         */
        String uri = "/api" + request.getRequestURI();
        String decodeUri = URLDecoder.decode(uri, "UTF-8");
        String authStr = decodeUri;
        if (queryString != null && !queryString.equals("")) {
            authStr += "?" + queryString;
        }
        authStr += "\n" + ossCallbackBody;
        ret = doCheck(authStr, authorization, retString);
        return ret;
    }

    /**
     * Post请求
     */
    public void callback(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        String ossCallbackBody = WebUtil.getRequestBodyString(request);
        log.info("阿里云 OSS 回调参数:{}", ossCallbackBody);
        boolean ret = VerifyOSSCallbackRequest(request, ossCallbackBody);
        log.info("verify result : " + ret);
        if (ret) {
            String decode = URLDecoder.decode(ossCallbackBody, "UTF-8");
            JSONObject json = StringUtil.strToJson(decode);
            String object = json.getString("object");

            InbyteObjectStoragePo inbyteObjectStoragePo = InbyteObjectStoragePo.builder()
                    .objectId(json.getInteger("objectId"))
                    .name(object.substring(object.lastIndexOf("/") + 1))
                    .mimeType(json.getString("mimeType"))
                    .height(json.getInteger("height"))
                    .width(json.getInteger("width"))
                    .size(json.getInteger("size"))
                    .uploaded(WhetherDict.Yes.code)
                    .updateTime(LocalDateTime.now())
                    .build();
            objectStorageMapper.updateById(inbyteObjectStoragePo);
        }
        if (ret) {
            response(request, response, "{\"Status\":\"OK\"}", HttpServletResponse.SC_OK);
        } else {
            response(request, response, "{\"Status\":\"verify not ok\"}", HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    /**
     * 验证RSA
     *
     * @param content
     * @param sign
     * @param publicKey
     * @return
     */
    public static boolean doCheck(String content, byte[] sign, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = BinaryUtil.fromBase64String(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            java.security.Signature signature = java.security.Signature.getInstance("MD5withRSA");
            signature.initVerify(pubKey);
            signature.update(content.getBytes());
            return signature.verify(sign);
        } catch (Exception e) {
            log.error("OSS 验证RSA异常", e);
        }

        return false;
    }

    /**
     * 服务器响应结果
     *
     * @param request
     * @param response
     * @param results
     * @param status
     * @throws IOException
     */
    private void response(HttpServletRequest request, HttpServletResponse response, String results, int status)
            throws IOException {
        String callbackFunName = request.getParameter("callback");
        response.addHeader("Content-Length", String.valueOf(results.length()));
        if (callbackFunName == null || callbackFunName.equalsIgnoreCase("")) {
            response.getWriter().println(results);
        } else {
            response.getWriter().println(callbackFunName + "( " + results + " )");
        }
        response.setStatus(status);
        response.flushBuffer();
    }


//    public Result<String> uploadFile(AliYunOssUploadFileParam param) {
//        LocalDateTime now = LocalDateTime.now();
//
//        String randomNum = param.getCoverable() != null && param.getCoverable() != WhetherDict.No.code
//                ? String.valueOf(new Random().nextInt(1000000))
//                : "";
//        /**
//         * 文件格式
//         * 商户空间/商户名/模块路径/年/月/日/模块参数/防重复随机数
//         */
//        String objectName = new StringBuilder()
//                .append("mct-space/")
//                .append(param.getMerchantName()).append("/")
//                .append(param.getPage()).append("/")
//                .append(now.getYear()).append(now.getMonthValue()).append(now.getDayOfMonth())
//                .append(randomNum).append("-")
//                .append(param.getFileName())
//                .toString()
//                .replace("//", "/");
//
//        // 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//        try {
//            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(param.getFileBytes()));
//        } catch (OSSException oe) {
//            log.error("Caught an OSSException, which means your request made it to OSS, "
//                    + "but was rejected with an error response for some reason.", oe);
//        } catch (ClientException ce) {
//            log.error("Caught an ClientException, which means the client encountered "
//                    + "a serious internal problem while trying to communicate with OSS, "
//                    + "such as not being able to access the network.", ce);
//        } finally {
//            if (ossClient != null) {
//                ossClient.shutdown();
//            }
//        }
//
//        // 文件地址
//        String url = "https://" + bucketName + "." + endpoint + "/" + objectName;
//
//        // 存储数据管理
//        ObjectStoragePo objectStoragePo = ObjectStoragePo.builder()
//                .url(url)
//                .endPoint(endpoint)
//                .fileName(param.getFileName())
//                .filePath(objectName)
//                .fileType(param.getFileType())
//                .mimeType(FileTypeDict.getByCode(param.getFileType()).name)
//                .uploadBy(UploadSourceDict.User.code)
//                .size(param.getFileBytes().length)
//                .bucket(bucketName)
//                .path(param.getPage())
//                .pathParam(param.getScene())
//                .uploaded(WhetherDict.Yes.code)
//                .createTime(now)
//                .creator(param.getUserName())
//                .build();
//        objectStorageMapper.insert(objectStoragePo);
//
//        return Result.success("上传成功", url);
//    }

}