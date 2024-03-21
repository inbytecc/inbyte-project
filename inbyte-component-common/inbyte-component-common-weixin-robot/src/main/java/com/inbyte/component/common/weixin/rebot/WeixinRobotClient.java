package com.inbyte.component.common.weixin.rebot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 微信机器人客户端
 *
 * @author chenjw
 */
@Slf4j
@Component
public class WeixinRobotClient {

    // 创建 RestTemplate 实例
    RestTemplate restTemplate = new RestTemplate();

    /**
     * 微信消息内容最大长度
     */
    private static final int WeixinMessageMaxLength = 3000;

    /**
     * 发送文本消息
     *
     * @return
     */
    public void sendRoomMsg(String topic, String friendName, String message) {

    }

    /**
     * 发送文本消息
     *
     * @return
     */
    public void sendFriendMsg(String topic, String friendName, String message) {
        // 设置请求头部信息
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("access-key", "inbyte-root-accesskey");

        // 请求数据 JSON 字符串
        String requestData = "{\"name\": \"玩一玩\", \"message\": \"有点东西\"}";

        // 创建请求实体，将请求数据作为字符串传递给 HttpEntity
        HttpEntity<String> requestEntity = new HttpEntity<>(requestData, headers);

        // 发送 POST 请求
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "https://robot.inbyte.cc/msg/friend",
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // 获取响应状态码和响应内容
        HttpStatusCode statusCode = responseEntity.getStatusCode();
        String responseBody = responseEntity.getBody();

        // 打印响应状态码和响应内容
        System.out.println("Response Status Code: " + statusCode);
        System.out.println("Response Body: " + responseBody);
    }


}
