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
public class MerchantQrcodeViewedEvent extends ApplicationEvent {

    private Integer quid;
    private Integer eid;
    private AppTypeEnum appType;

    public MerchantQrcodeViewedEvent(Object source, Integer quid, Integer eid, AppTypeEnum appType) {
        super(source);
        this.quid = quid;
        this.eid = eid;
        this.appType = appType;
    }
}
