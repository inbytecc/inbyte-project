package com.inbyte.component.app.order.model;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单创建成功响应
 * 杭州易思网络
 *
 * @author dojoy
 * @date 2016年04月27日
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderCreateResponse {

    /**
     * 订单No
     */
    private String orderNo;

}