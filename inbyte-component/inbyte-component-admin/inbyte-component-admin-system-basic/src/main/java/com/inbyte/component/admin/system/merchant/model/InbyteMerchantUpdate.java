package com.inbyte.component.admin.system.merchant.model;

import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 商户实体
 *
 * @author chenjw
 * @date 2024-03-11 17:14:03
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("inbyte_merchant")
public class InbyteMerchantUpdate {

    /**
     * 联系人
     */
    private String contact;

    /**
     * 手机号
     */
    private String tel;

    /**
     * Logo
     */
    private String logo;

    /**
     * 关于我们
     */
    private JSONArray aboutUs;

}
