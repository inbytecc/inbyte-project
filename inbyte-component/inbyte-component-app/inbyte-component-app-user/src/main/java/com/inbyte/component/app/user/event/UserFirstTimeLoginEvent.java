package com.inbyte.component.app.user.event;

import com.inbyte.commons.model.enums.AppTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.context.ApplicationEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户首次登录事件
 * 记录推荐关系用
 *
 * @author: chenjw
 * @create: 2024-04-05 16:35
 */
@Getter
@Setter
public class UserFirstTimeLoginEvent extends ApplicationEvent {

    /**
     * 二维码ID
     */
    @NotNull
    private Integer qcid;
    /**
     * 二维码类型
     */
    @NotNull
    private Integer qctp;

    /**
     * 介绍人外部用户ID
     */
    private Integer introducerEid;

    /**
     * 被推荐人
     */
    private Integer referredEid;
    /**
     * 外部用户ID类型
     */
    @NotNull
    private AppTypeEnum appType;
    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * appId
     */
    private String appId;

    /**
     * 商户号
     */
    private String mctNo;

    private BigDecimal longitude;
    private BigDecimal latitude;

    public UserFirstTimeLoginEvent(Object source, Integer qcid, Integer qctp,
                                   Integer introducerEid, AppTypeEnum appType, Integer referredEid, LocalDateTime loginTime,
                                   BigDecimal longitude, BigDecimal latitude, String appId, String mctNo) {
        super(source);
        this.qcid = qcid;
        this.qctp = qctp;
        this.introducerEid = introducerEid;
        this.appType = appType;
        this.referredEid = referredEid;
        this.loginTime = loginTime;
        this.longitude = longitude;
        this.latitude = latitude;
        this.appId = appId;
        this.mctNo = mctNo;
    }

}
