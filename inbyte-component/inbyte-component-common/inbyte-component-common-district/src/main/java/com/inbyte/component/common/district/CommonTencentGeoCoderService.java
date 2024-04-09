package com.inbyte.component.common.district;

import com.alibaba.fastjson2.JSONObject;
import com.inbyte.commons.model.dto.R;
import com.inbyte.component.common.district.model.CommonCity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

/**
 * 腾讯地图服务
 *
 * @author chenjw
 * @date 2024/1/4
 **/
@ConditionalOnProperty(name = "tencent.map.enable", havingValue = "true", matchIfMissing = false)
@Service
public class CommonTencentGeoCoderService {

    @Value("${tencent.map.apikey}")
    private String apiKey;
    @Value("${tencent.map.default.cityId}")
    private Integer cityId;
    @Value("${tencent.map.default.cityName}")
    private String cityName;
    @Autowired
    private RestTemplate restTemplate;
    private String Tencent_Geo_Parse_Url = "https://apis.map.qq.com/ws/geocoder/v1/?key={key}&location={location}";

    public R<CommonCity> locationParseCity(BigDecimal longitude, BigDecimal latitude) {
        CommonCity commonCity = new CommonCity();
        if (longitude == null) {
            commonCity.setCityId(cityId);
            commonCity.setCityName(cityName);
            return R.ok(commonCity);
        }

        JSONObject jsonObject = restTemplate.getForObject(Tencent_Geo_Parse_Url,
                JSONObject.class,
                apiKey,
                latitude.toString() + "," + longitude.toString());
        if (jsonObject == null || jsonObject.getIntValue("status") != 0) {
            commonCity.setCityId(4401);
            commonCity.setCityName("广州市");
            return R.ok(commonCity);
        }

        JSONObject adInfo = jsonObject.getJSONObject("result").getJSONObject("ad_info");
        commonCity.setCityId(Integer.valueOf(adInfo.getString("adcode").substring(0, 4)));
        commonCity.setCityName(adInfo.getString("city"));
        return R.ok(commonCity);
    }
}
