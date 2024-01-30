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

    /** 首页配置 */
    private JSONArray homePageConfig;

    /** 加入我们 */
    private JSONArray joinUs;

    /** 黄金广告位 */
    private JSONArray goldenBanner;

    /** 公司介绍 */
    private JSONArray companyIntroduce;

    /** 关于我们 */
    private JSONArray aboutUs;

    /** 销售合伙人 */
    private JSONArray salesPartner;

    /** 技术合伙人 */
    private JSONArray itPartner;

    /** 技术共创 */
    private JSONArray itEntrepreneurship;

    /** Boss直聊 */
    private JSONArray chatToBoss;



}
