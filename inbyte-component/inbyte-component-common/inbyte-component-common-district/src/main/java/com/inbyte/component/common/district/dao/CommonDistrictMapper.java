package com.inbyte.component.common.district.dao;


import com.inbyte.component.common.district.model.CommonCity;
import com.inbyte.component.common.district.model.CommonDistrict;
import com.inbyte.component.common.district.model.CommonProvince;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 省市区
 *
 * 缓存LRU 10分钟更新
 * @author chenjw
 * @date 2020/08/12 04:43:45
 */
public interface CommonDistrictMapper {

    /**
     * 省
     *
     * @return CommonProvince
     */
    List<CommonProvince> queryProvince(@Param("keyword") String keyword);
    /**
     * 省
     *
     * @return CommonProvince
     */
    String queryProvinceNameById(Integer provinceId);

    /**
     * 市
     *
     * @param provinceId
     * @return
     */
    List<CommonCity> queryCity(@Param("keyword") String keyword,
                               @Param("provinceId") Integer provinceId);

    /**
     * 市
     *
     * @param cityId
     * @return
     */
    String queryCityNameById(@Param("cityId") Integer cityId);

    /**
     * 区
     *
     * @param cityId
     * @return
     */
    List<CommonDistrict> queryDistrict(@Param("keyword") String keyword,
                                       @Param("cityId") Integer cityId);
    /**
     * 区
     *
     * @param districtId
     * @return
     */
    String queryDistrictNameById(@Param("districtId") Integer districtId);

    /**
     * 使用ID查询省市区名称
     *
     * @param regionId
     * @return
     */
    String queryNameById(Integer regionId);

}