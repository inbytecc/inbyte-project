package com.inbyte.component.common.district;

import com.inbyte.component.common.district.model.CommonCity;
import com.inbyte.component.common.district.model.CommonDistrict;
import com.inbyte.component.common.district.model.CommonProvince;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.common.district.dao.CommonDistrictMapper;
import com.inbyte.component.common.district.model.ProvinceCityDistrictDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用城市服务
 *
 * @author chenjw
 * @date 2020/8/6
 **/
@Service
public class CommonDistrictService {

    @Autowired
    private CommonDistrictMapper commonDistrictMapper;

    private static List<Integer> Municipality_List;

    static {
        Municipality_List = new ArrayList<>();
        Municipality_List.add(11);
        Municipality_List.add(1101);
        Municipality_List.add(12);
        Municipality_List.add(1201);
        Municipality_List.add(31);
        Municipality_List.add(3101);
        Municipality_List.add(50);
        Municipality_List.add(5001);
    }

    /**
     * 是否直辖市
     *
     * @return
     */
    public boolean isMunicipality(Integer provinceOrCityId) {
        return Municipality_List.contains(provinceOrCityId);
    }

    /**
     * 查询地区名称
     * 直辖市不显示省份名称
     *
     * @param provinceId
     * @param cityId
     * @return
     */
    public String getRegionName(Integer provinceId, Integer cityId) {
        if (isMunicipality(provinceId)) {
            return commonDistrictMapper.queryCityNameById(cityId);
        } else {
            return commonDistrictMapper.queryProvinceNameById(provinceId) +
                    commonDistrictMapper.queryCityNameById(cityId);
        }
    }

    /**
     * 查询省市区名称
     *
     * @param provinceId
     * @param cityId
     * @param districtId
     * @return
     */
    public String getFullName(Integer provinceId, Integer cityId, Integer districtId) {
        if (isMunicipality(provinceId)) {
            return commonDistrictMapper.queryCityNameById(cityId);
        } else {
            return commonDistrictMapper.queryProvinceNameById(provinceId) +
                    commonDistrictMapper.queryCityNameById(cityId) +
                    commonDistrictMapper.queryDistrictNameById(districtId);
        }
    }

    /**
     * 获取省市区
     *
     * @param cityId
     * @return
     */
    public ProvinceCityDistrictDto getProvinceCity(Integer cityId) {
        Integer provinceId = getProvinceIdByCity(cityId);
        ProvinceCityDistrictDto pcd = new ProvinceCityDistrictDto();
        pcd.setProvinceName(commonDistrictMapper.queryNameById(provinceId));
        pcd.setCityName(commonDistrictMapper.queryNameById(cityId));
        pcd.setMunicipality(isMunicipality(provinceId));
        return pcd;
    }

    /**
     * 获取省市区
     *
     * @param districtId
     * @return
     */
    public ProvinceCityDistrictDto getProvinceCityDistrict(Integer districtId) {
        ProvinceCityDistrictDto pc = getProvinceCity(getProvinceIdByCity(districtId));
        pc.setDistrictName(commonDistrictMapper.queryDistrictNameById(districtId));
        return pc;
    }

    public R<List<CommonProvince>> province(String keyword) {
        return R.success(commonDistrictMapper.queryProvince(keyword));
    }


    public R<List<CommonCity>> city(String keyword, Integer provinceId) {
        return R.success(commonDistrictMapper.queryCity(keyword, provinceId));
    }

    public R<List<CommonDistrict>> district(String keyword, Integer cityId) {
        return R.success(commonDistrictMapper.queryDistrict(keyword, cityId));
    }

    /**
     * 获取省份名称
     *
     * @param provinceId
     * @return
     */

    public String getProvinceNameById(Integer provinceId) {
        return commonDistrictMapper.queryProvinceNameById(provinceId);
    }

    /**
     * 获取市区名称
     *
     * @param cityId 市区id
     * @return
     */

    public String getCityNameById(Integer cityId) {
        return commonDistrictMapper.queryCityNameById(cityId);
    }

    /**
     * 获取行政区名称
     *
     * @param districtId 行政区Id
     * @return
     */
    public String queryDistrictNameById(Integer districtId) {
        return commonDistrictMapper.queryDistrictNameById(districtId);
    }

    /**
     * 使用城市或区域ID，获取省份ID
     *
     * @param cityOrDistrictId
     * @return
     */
    private Integer getProvinceIdByCity(Integer cityOrDistrictId) {
        return Integer.valueOf(cityOrDistrictId.toString().substring(0, 2));
    }

    /**
     * 使用城市或区域ID，获取城市ID
     *
     * @param cityOrDistrictId
     * @return
     */
    private Integer getCityIdByDistrict(Integer cityOrDistrictId) {
        return Integer.valueOf(cityOrDistrictId.toString().substring(0, 4));
    }
}
