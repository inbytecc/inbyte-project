package com.inbyte.component.common.district;

import com.inbyte.component.common.district.model.CommonCity;
import com.inbyte.component.common.district.model.CommonDistrict;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.common.district.model.CommonProvince;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * 行政区
 * 省市区查询接口
 * 行政区划代码
 * <p>
 * 参考国家统计局城市编码规划，http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/
 * git爬虫项目：https://github.com/modood/Administrative-divisions-of-China
 *
 * @author : chenjw
 * @date: 2020/08/12 04:43:45
 **/
@RestController
@RequestMapping("common/district")
public class CommonDistrictController {

    @Autowired
    private CommonDistrictService commonDistrictService;

    /**
     * 省份字典查询
     *
     * @param :
     * @return: Result<List < CommonProvince>>
     **/
    @GetMapping("province")
    public R<List<CommonProvince>> province(@RequestParam(name = "keyword", required = false) String keyword) {
        return commonDistrictService.province(keyword);
    }

    /**
     * 城市字典查询
     *
     * @param :
     * @return: Result<List < CommonCity>>
     **/
    @GetMapping("city")
    public R<List<CommonCity>> city(@RequestParam(name = "keyword", required = false) String keyword,
                                    @RequestParam(name = "provinceId", required = false) Integer provinceId) {
        return commonDistrictService.city(keyword, provinceId);
    }

    /**
     * 行政区字典查询
     *
     * @param :
     * @return: Result<List < CommonDistrict>>
     **/
    @GetMapping("district")
    public R<List<CommonDistrict>> district(@RequestParam(name = "keyword", required = false) String keyword,
                                            @RequestParam(name = "cityId", required = false) Integer cityId) {
        return commonDistrictService.district(keyword, cityId);
    }

}