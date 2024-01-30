package com.inbyte.component.common.district.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 省市区名称
 * @author: chenjw
 * @date: 2020/8/13
 */
@Getter
@Setter
public class ProvinceCityDistrictDto {

    /**
     * 省份名称
     */
    private String provinceName;
    /**
     * 市区名称
     */
    private String cityName;
    /**
     * 行政区名称
     */
    private String districtName;

    /**
     * 直辖市
     */
    private boolean municipality;

    /**
     * 获取省市名称
     * 直辖市只显示市名称
     * @return
     */
    public String getProvinceCityName() {
        return isMunicipality()
                ? cityName
                : provinceName + cityName;
    }

    /**
     * 获取省市名称
     * 直辖市只显示市名称
     * @return
     */
    public String getFullName() {
        return isMunicipality()
                ? cityName + districtName
                : provinceName + cityName + districtName;
    }

}
