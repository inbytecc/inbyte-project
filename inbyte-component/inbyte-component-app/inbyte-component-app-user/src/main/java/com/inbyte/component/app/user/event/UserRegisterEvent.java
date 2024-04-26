package com.inbyte.component.app.user.event;


import com.inbyte.commons.model.dict.AppTypeEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

/**
 * 用户注册事件
 *
 * @author chenjw
 * @date 20231222
 */
@Getter
@Setter
public class UserRegisterEvent extends ApplicationEvent {

    private Integer userId;
    private Integer eid;
    private AppTypeEnum appType;
    private LocalDateTime registerTime;

    public UserRegisterEvent(Object source, Integer userId, Integer eid, AppTypeEnum appType, LocalDateTime registerTime) {
        super(source);
        this.userId = userId;
        this.eid = eid;
        this.appType = appType;
        this.registerTime = registerTime;
    }
}
