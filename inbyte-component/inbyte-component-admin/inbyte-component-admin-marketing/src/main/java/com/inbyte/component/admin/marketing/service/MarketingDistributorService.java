package com.inbyte.component.admin.marketing.service;
import com.inbyte.commons.model.dto.Page;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.admin.marketing.model.marketing.distributor.MarketingDistributorQuery;
import com.inbyte.component.admin.marketing.model.marketing.distributor.MarketingDistributorCreate;
import com.inbyte.component.admin.marketing.model.marketing.distributor.MarketingDistributorUpdate;
import com.inbyte.component.admin.marketing.model.marketing.distributor.MarketingDistributorBrief;
import com.inbyte.component.admin.marketing.model.marketing.distributor.MarketingDistributorDetail;

/**
 * 分销商服务
 *
 * @author chenjw
 * @date 2024-10-15 14:29:46
 **/
public interface MarketingDistributorService {

    /**
     * 创建
     *
     * @param create
     * @return R
     **/
    R create(MarketingDistributorCreate create);

    /**
     * 删除
     *
     * @param distributorId
     * @return R
     **/
    R delete(Integer distributorId);

    /**
     * 修改
     *
     * @param update
     * @return R
     **/
    R update(MarketingDistributorUpdate update);

    /**
     * 详情
     *
     * @param distributorId
     * @return MarketingDistributorDetail
     **/
    R<MarketingDistributorDetail> detail(Integer distributorId);

    /**
     * 列表
     *
     * @param query
     * @return R<Page<MarketingDistributorBrief>>
     **/
    R<Page<MarketingDistributorBrief>> list(MarketingDistributorQuery query);
}
