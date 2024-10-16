package com.inbyte.component.admin.marketing.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.admin.marketing.model.marketing.distributor.MarketingDistributorBrief;
import com.inbyte.component.admin.marketing.model.marketing.distributor.MarketingDistributorDetail;
import com.inbyte.component.admin.marketing.model.marketing.distributor.MarketingDistributorPo;
import com.inbyte.component.admin.marketing.model.marketing.distributor.MarketingDistributorQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 分销商
 *
 * 表名 marketing_distributor
 * @author chenjw
 * @date 2024-10-15 14:29:46
 */
public interface MarketingDistributorMapper extends BaseMapper<MarketingDistributorPo> {

    /**
     * 详情
     *
     * @param distributorId
     * @return MarketingDistributorDetail
     **/
    MarketingDistributorDetail detail(@Param("distributorId")Integer distributorId,
                                      @Param("mctNo") String mctNo);

    /**
     * 查询列表
     * @param query
     * @return List<MarketingDistributorBrief>
     **/
    List<MarketingDistributorBrief> list(MarketingDistributorQuery query);
}
