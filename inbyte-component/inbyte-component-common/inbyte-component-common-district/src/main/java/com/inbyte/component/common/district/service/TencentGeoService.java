package com.inbyte.component.common.district.service;

import com.alibaba.fastjson2.JSONObject;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.common.district.model.CommonCity;
import com.inbyte.component.common.district.model.tencent.TencentMapPlaceDto;
import com.inbyte.component.common.district.model.tencent.TencentMapPlaceSearchParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * 腾讯地图服务
 *
 * @author chenjw
 * @date 2024/1/4
 **/
@Slf4j
@ConditionalOnProperty(name = "tencent.map.enable", havingValue = "true", matchIfMissing = false)
@Service
public class TencentGeoService {


    @Value("${tencent.map.apikey}")
    private String apiKey;
    @Value("${tencent.map.default.cityId}")
    private Integer defaultCityId;
    @Value("${tencent.map.default.cityName}")
    private String defaultCityName;
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 逆地址解析（坐标位置描述）
     * 接口文档：https://lbs.qq.com/service/webService/webServiceGuide/address/Gcoder
     */
    private String TENCENT_GEO_PARSE_URL = "https://apis.map.qq.com/ws/geocoder/v1/?key={key}&location={location}";

    /**
     * 地点搜索
     *
     * 接口文档：https://lbs.qq.com/service/webService/webServiceGuide/search/webServiceSearch
     */
    private static final String TENCENT_PLACE_SEARCH_URL = "https://apis.map.qq.com/ws/place/v1/search";

    /**
     * 根据经纬度解析城市
     *
     * @param longitude
     * @param latitude
     * @return
     */
    public R<CommonCity> locationParseCity(BigDecimal longitude, BigDecimal latitude) {
        CommonCity commonCity = new CommonCity();
        if (longitude == null) {
            commonCity.setCityId(defaultCityId);
            commonCity.setCityName(defaultCityName);
            return R.ok(commonCity);
        }

        JSONObject jsonObject = restTemplate.getForObject(TENCENT_GEO_PARSE_URL,
                JSONObject.class,
                apiKey,
                latitude.toString() + "," + longitude.toString());
        if (jsonObject == null || jsonObject.getIntValue("status") != 0) {
            commonCity.setCityId(defaultCityId);
            commonCity.setCityName(defaultCityName);
            return R.ok(commonCity);
        }

        JSONObject adInfo = jsonObject.getJSONObject("result").getJSONObject("ad_info");
        commonCity.setCityId(Integer.valueOf(adInfo.getString("adcode").substring(0, 4)));
        commonCity.setCityName(adInfo.getString("city"));
        return R.ok(commonCity);
    }

    public R<List<TencentMapPlaceDto>> searchPlace(TencentMapPlaceSearchParam request) {
        // 构建 URI
        URI uri = UriComponentsBuilder.fromHttpUrl(TENCENT_PLACE_SEARCH_URL)
                .queryParam("key", apiKey)
                .queryParam("keyword", request.getKeyword())
                .queryParam("boundary", request.getBoundary())
                .queryParamIfPresent("filter", Optional.ofNullable(request.getFilter()))
//                .queryParamIfPresent("get_subpois", Optional.ofNullable(request.getGetSubpois()))
//                .queryParamIfPresent("added_fields", Optional.ofNullable(request.getAddedFields()))
//                .queryParamIfPresent("orderby", Optional.ofNullable(request.getOrderby()))
                .queryParam("page_size", 20)
                .queryParam("page_index", 1)
                .build()
                .encode()
                .toUri();

        // 发送GET请求并接收响应
        JSONObject jsonObject = restTemplate.getForObject(uri, JSONObject.class);

        if (jsonObject == null || jsonObject.getIntValue("status") != 0) {
            log.error("腾讯地图位置数据查询失败:{}", jsonObject);
            return R.failure("数据查询失败, 请稍后再试");
        }
        List<TencentMapPlaceDto> list = jsonObject.getList("data", TencentMapPlaceDto.class);
        return R.ok(list);
    }
}
