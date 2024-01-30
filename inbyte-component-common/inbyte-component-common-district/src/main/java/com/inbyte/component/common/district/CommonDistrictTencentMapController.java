package com.inbyte.component.common.district;

import com.inbyte.commons.model.dto.R;
import com.inbyte.component.common.district.model.CommonCity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * 腾讯地图服务
 * <p>
 *
 * @author : chenjw
 * @date: 2024/01/3
 **/
@ConditionalOnProperty(name = "tencent.map.enable", havingValue = "true", matchIfMissing = false)
@RestController
@RequestMapping("common/district/tencent/geo")
public class CommonDistrictTencentMapController {

    @Autowired
    private CommonTencentGeoCoderService commonDistrictService;

    /**
     * 经纬度解析城市
     *
     * @param :
     * @return: Result<CommonDistrict>
     **/
    @GetMapping("city/parse")
    public R<CommonCity> locationParseCity(@RequestParam(name = "longitude", required = false) BigDecimal longitude,
                                           @RequestParam(name = "latitude", required = false) BigDecimal latitude) {
        return commonDistrictService.locationParseCity(longitude, latitude);
    }
}