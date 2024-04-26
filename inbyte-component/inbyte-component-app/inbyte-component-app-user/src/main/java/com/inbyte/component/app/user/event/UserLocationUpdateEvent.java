package com.inbyte.component.app.user.event;


import com.inbyte.commons.model.dict.AppTypeEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户定位更新事件
 *
 * @author chenjw
 * @date 20231222
 */
@Getter
@Setter
public class UserLocationUpdateEvent extends ApplicationEvent {
    private Integer userId;
    private Integer eid;
    private AppTypeEnum appType;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private LocalDateTime updateTime;

    public UserLocationUpdateEvent(Object source, Integer userId, Integer eid, AppTypeEnum appType, BigDecimal longitude, BigDecimal latitude, LocalDateTime updateTime) {
        super(source);
        this.userId = userId;
        this.eid = eid;
        this.appType = appType;
        this.longitude = longitude;
        this.latitude = latitude;
        this.updateTime = updateTime;
    }
}
