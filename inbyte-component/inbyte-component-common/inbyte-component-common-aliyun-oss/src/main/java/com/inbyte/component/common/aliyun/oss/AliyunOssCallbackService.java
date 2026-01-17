package com.inbyte.component.common.aliyun.oss;

import com.aliyun.oss.common.utils.BinaryUtil;
import com.inbyte.component.common.aliyun.oss.model.AliyunOssCallbackDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URI;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 * 阿里云OSS POST签名服务
 * 用于Web端服务端签名直传
 *
 * @author chenjw
 * @date 2024/12/27
 */
@Slf4j
@Service
public class AliyunOssCallbackService {

    /**
     * OSS回调成功响应
     */
    private static final AliyunOssCallbackDto CALLBACK_SUCCESS = new AliyunOssCallbackDto("OK");

    /**
     * OSS回调验证失败响应
     */
    private static final AliyunOssCallbackDto CALLBACK_VERIFY_FAILED = new AliyunOssCallbackDto("verify not ok");

    @SuppressWarnings({ "finally" })
    public String executeGet(String url) {
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
        } finally {
            if (in != null) {
                try {
                    in.close();// 最后要关闭BufferedReader
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return content;
        }
    }

    public String GetPostBody(InputStream is, int contentLen) {
        if (contentLen > 0) {
            int readLen = 0;
            int readLengthThisTime = 0;
            byte[] message = new byte[contentLen];
            try {
                while (readLen != contentLen) {
                    readLengthThisTime = is.read(message, readLen, contentLen - readLen);
                    if (readLengthThisTime == -1) {// Should not happen.
                        break;
                    }
                    readLen += readLengthThisTime;
                }
                return new String(message);
            } catch (IOException e) {
            }
        }
        return "";
    }


    protected boolean VerifyOSSCallbackRequest(HttpServletRequest request, String ossCallbackBody) {
        String authorizationInput = new String(request.getHeader("Authorization"));
        String pubKeyInput = request.getHeader("x-oss-pub-key-url");
        byte[] authorization = BinaryUtil.fromBase64String(authorizationInput);
        byte[] pubKey = BinaryUtil.fromBase64String(pubKeyInput);
        String pubKeyAddr = new String(pubKey);
        if (!pubKeyAddr.startsWith("http://gosspublic.alicdn.com/") &&
                !pubKeyAddr.startsWith("https://gosspublic.alicdn.com/")) {
            log.warn("pub key addr must be oss addrss");
            return false;
        }

        String retString = executeGet(pubKeyAddr);
        retString = retString.replace("-----BEGIN PUBLIC KEY-----", "");
        retString = retString.replace("-----END PUBLIC KEY-----", "");
        String queryString = request.getQueryString();
        String uri = request.getRequestURI();
        String decodeUri;
        try {
            decodeUri = java.net.URLDecoder.decode(uri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("decode uri error", e);
            return false;
        }

        String authStr = decodeUri;
        if (queryString != null && !queryString.equals("")) {
            authStr += "?" + queryString;
        }
        authStr += "\n" + ossCallbackBody;
        return doCheck(authStr, authorization, retString);
    }

    public AliyunOssCallbackDto callbackVerify(HttpServletRequest request) {
        try {
            String ossCallbackBody = GetPostBody(request.getInputStream(), Integer.parseInt(request.getHeader("content-length")));
            boolean ret = VerifyOSSCallbackRequest(request, ossCallbackBody);
            log.info("verify result:{}", ret);
            log.info("OSS Callback Body:{}", ossCallbackBody);
            if (ret) {
                return CALLBACK_SUCCESS;
            } else {
                return CALLBACK_VERIFY_FAILED;
            }
        } catch (Exception e) {
            log.error("verify oss callback error", e);
            return CALLBACK_VERIFY_FAILED;
        }
    }

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
            log.error("doCheck oss error", e);
            return false;
        }
    }

}