package com.inbyte.component.app.user.event;

import com.inbyte.commons.model.dict.AppTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.context.ApplicationEvent;

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
     * 外部用户ID
     */
    @NotNull
    private Integer eid;
    /**
     * 外部用户ID类型
     */
    @NotNull
    private AppTypeEnum etp;
    /**
     * 外部用户ID
     */
    @NotNull
    private Integer recommendEid;
    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    public UserFirstTimeLoginEvent(Object source, Integer qcid, Integer qctp, Integer eid, AppTypeEnum appType, Integer recommendEid, LocalDateTime loginTime) {
        super(source);
        this.qcid = qcid;
        this.qctp = qctp;
        this.eid = eid;
        this.etp = appType;
        this.recommendEid = recommendEid;
        this.loginTime = loginTime;
    }

}
