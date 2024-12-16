package com.inbyte.component.app.user.event;


import com.inbyte.commons.model.enums.AppTypeEnum;
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

    private Integer qcid;
    private Integer eid;
    private AppTypeEnum appType;

    public MerchantQrcodeViewedEvent(Object source, Integer qcid, Integer eid, AppTypeEnum appType) {
        super(source);
        this.qcid = qcid;
        this.eid = eid;
        this.appType = appType;
    }
}
