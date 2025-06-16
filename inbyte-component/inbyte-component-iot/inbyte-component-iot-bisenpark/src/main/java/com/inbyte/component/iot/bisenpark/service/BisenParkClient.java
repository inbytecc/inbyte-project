package com.inbyte.component.iot.bisenpark.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inbyte.component.iot.bisenpark.model.BisenParkProperties;
import com.inbyte.component.iot.bisenpark.model.dto.ApiResponse;
import com.inbyte.component.iot.bisenpark.model.dto.auth.TokenRequest;
import com.inbyte.component.iot.bisenpark.model.dto.auth.TokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 百胜停车场HTTP客户端
 *
 * @author chenjw
 * @date 2025-01-16
 */
@Slf4j
@Service
@ConditionalOnProperty(prefix = "inbyte.iot.bisenpark", name = "enabled", havingValue = "true", matchIfMissing = true)
public class BisenParkClient {

    @Autowired
    private BisenParkProperties properties;

    @Autowired
    private ObjectMapper objectMapper;

    private final HttpClient httpClient;
    private volatile String accessToken;
    private volatile LocalDateTime tokenExpireTime;
    private final ReentrantReadWriteLock tokenLock = new ReentrantReadWriteLock();

    public BisenParkClient() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(30000))
                .build();
    }

    /**
     * 获取访问令牌
     */
    private String getAccessToken() throws Exception {
        tokenLock.readLock().lock();
        try {
            if (accessToken != null && tokenExpireTime != null && 
                LocalDateTime.now().isBefore(tokenExpireTime.minusMinutes(5))) {
                return accessToken;
            }
        } finally {
            tokenLock.readLock().unlock();
        }

        tokenLock.writeLock().lock();
        try {
            // 双重检查
            if (accessToken != null && tokenExpireTime != null && 
                LocalDateTime.now().isBefore(tokenExpireTime.minusMinutes(5))) {
                return accessToken;
            }

            // 获取新token
            TokenRequest tokenRequest = TokenRequest.create(properties.getClientId(), properties.getClientSecret());
            String queryString = buildTokenQueryString(tokenRequest);
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(properties.getBaseUrl() + "/auth/oauth/token?" + queryString))
                    .timeout(Duration.ofMillis(properties.getReadTimeout()))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                TokenResponse tokenResponse = objectMapper.readValue(response.body(), TokenResponse.class);
                if (tokenResponse.isSuccess()) {
                    this.accessToken = tokenResponse.getAccessToken();
                    this.tokenExpireTime = LocalDateTime.now().plusSeconds(tokenResponse.getExpiresIn());
                    log.info("百胜停车场token获取成功，过期时间: {}", tokenExpireTime);
                    return this.accessToken;
                } else {
                    throw new RuntimeException("获取token失败: " + tokenResponse.getErrorDescription());
                }
            } else {
                throw new RuntimeException("获取token失败，HTTP状态码: " + response.statusCode());
            }
        } finally {
            tokenLock.writeLock().unlock();
        }
    }

    /**
     * 构建token请求查询字符串
     */
    private String buildTokenQueryString(TokenRequest tokenRequest) {
        StringBuilder sb = new StringBuilder();
        if (tokenRequest.getRandomStr() != null) {
            sb.append("randomStr=").append(URLEncoder.encode(tokenRequest.getRandomStr(), StandardCharsets.UTF_8)).append("&");
        }
        sb.append("scope=").append(URLEncoder.encode(tokenRequest.getScope(), StandardCharsets.UTF_8)).append("&");
        sb.append("grant_type=").append(URLEncoder.encode(tokenRequest.getGrantType(), StandardCharsets.UTF_8)).append("&");
        sb.append("client_id=").append(URLEncoder.encode(tokenRequest.getClientId(), StandardCharsets.UTF_8)).append("&");
        sb.append("client_secret=").append(URLEncoder.encode(tokenRequest.getClientSecret(), StandardCharsets.UTF_8));
        return sb.toString();
    }

    /**
     * 发送POST请求
     */
    public <T> ApiResponse<T> post(String path, Object requestBody, TypeReference<ApiResponse<T>> responseType) throws Exception {
        String token = getAccessToken();
        String requestJson = objectMapper.writeValueAsString(requestBody);
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(properties.getBaseUrl() + path))
                .timeout(Duration.ofMillis(properties.getReadTimeout()))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .POST(HttpRequest.BodyPublishers.ofString(requestJson, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() == 200) {
            return objectMapper.readValue(response.body(), responseType);
        } else {
            log.error("请求失败，HTTP状态码: {}, 响应内容: {}", response.statusCode(), response.body());
            throw new RuntimeException("请求失败，HTTP状态码: " + response.statusCode());
        }
    }

    /**
     * 发送GET请求
     */
    public <T> ApiResponse<T> get(String path, TypeReference<ApiResponse<T>> responseType) throws Exception {
        String token = getAccessToken();
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(properties.getBaseUrl() + path))
                .timeout(Duration.ofMillis(properties.getReadTimeout()))
                .header("Authorization", "Bearer " + token)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() == 200) {
            return objectMapper.readValue(response.body(), responseType);
        } else {
            log.error("请求失败，HTTP状态码: {}, 响应内容: {}", response.statusCode(), response.body());
            throw new RuntimeException("请求失败，HTTP状态码: " + response.statusCode());
        }
    }
} 