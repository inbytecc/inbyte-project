package com.inbyte.component.app.user.event;


import com.inbyte.commons.model.dict.AppTypeEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * 用户注册事件
 *
 * @author chenjw
 * @date 20231222
 */
@Getter
@Setter
public class UserQrcodeViewedEvent extends ApplicationEvent {

    private Integer shareEid;
    private Integer eid;
    private AppTypeEnum appType;

    public UserQrcodeViewedEvent(Object source, Integer shareEid, Integer eid, AppTypeEnum appType) {
        super(source);
        this.shareEid = shareEid;
        this.eid = eid;
        this.appType = appType;
    }
}
