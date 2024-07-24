package com.inbyte.component.common.district.controller;

import com.inbyte.commons.model.dto.R;
import com.inbyte.component.common.district.model.CommonCity;
import com.inbyte.component.common.district.model.tencent.TencentMapPlaceDto;
import com.inbyte.component.common.district.model.tencent.TencentMapPlaceSearchParam;
import com.inbyte.component.common.district.service.TencentGeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 腾讯地图服务
 *
 * <p>
 *
 * @author : chenjw
 * @date: 2024/01/3
 **/
@ConditionalOnProperty(name = "tencent.map.enable", havingValue = "true", matchIfMissing = false)
@RestController
@RequestMapping("common/district/tencent/geo")
public class TencentMapController {

    @Autowired
    private TencentGeoService tencentGeoService;

    /**
     * 经纬度解析城市
     *
     * @param :
     * @return: Result<CommonDistrict>
     **/
    @GetMapping("city/parse")
    public R<CommonCity> locationParseCity(@RequestParam(name = "longitude", required = false) BigDecimal longitude,
                                           @RequestParam(name = "latitude", required = false) BigDecimal latitude) {
        return tencentGeoService.locationParseCity(longitude, latitude);
    }

    /**
     * 地点搜索
     *
     * 腾讯地图接口文档：https://lbs.qq.com/service/webService/webServiceGuide/search/webServiceSearch
     **/
    @GetMapping("place")
    public R<List<TencentMapPlaceDto>> searchPlace(@ModelAttribute TencentMapPlaceSearchParam request) {
        return tencentGeoService.searchPlace(request);
    }
}