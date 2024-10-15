package com.inbyte.component.admin.marketing.model.marketing.distributor;

import com.inbyte.commons.model.dto.BasePage;
import lombok.Getter;
import lombok.Setter;

/**
 * 分销商查询
 *
 * @author chenjw
 * @date 2024-10-15 14:29:46
 **/
@Getter
@Setter
public class MarketingDistributorQuery extends BasePage {

    /**
     * 查询关键字
     **/
    private String keyword;

}
