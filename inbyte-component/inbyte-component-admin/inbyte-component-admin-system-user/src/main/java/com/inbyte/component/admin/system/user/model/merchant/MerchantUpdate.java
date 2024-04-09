package com.inbyte.component.admin.system.user.model.merchant;

import com.alibaba.fastjson2.JSONArray;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.NotNull;


/**
 * 商户修改
 *
 * @author chenjw
 * @date 2023-11-05 12:29:19
 **/
@Getter
@Setter
public class MerchantUpdate {

    /** 加入我们 */
    private JSONArray joinUs;

    /** 黄金广告位 */
    private JSONArray goldenBanner;

}
