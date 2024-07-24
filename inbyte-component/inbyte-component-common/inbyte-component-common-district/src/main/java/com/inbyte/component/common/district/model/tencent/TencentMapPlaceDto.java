package com.inbyte.component.common.district.model.tencent;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TencentMapPlaceDto {

    /**
     * POI（地点）唯一标识
     */
    @JSONField(name = "id")
    private String id;

    /**
     * POI（地点）名称
     */
    @JSONField(name = "title")
    private String title;

    /**
     * 地址
     */
    @JSONField(name = "address")
    private String address;

    /**
     * 电话
     */
    @JSONField(name = "tel")
    private String tel;

    /**
     * POI（地点）分类
     */
    @JSONField(name = "category")
    private String category;

    /**
     * POI（地点）分类编码，设置added_fields=category_code时返回
     */
    @JSONField(name = "category_code")
    private Integer categoryCode;

    /**
     * POI类型，值说明：0:普通POI / 1:公交车站 / 2:地铁站 / 3:公交线路 / 4:行政区划
     */
    @JSONField(name = "type")
    private int type;

    /**
     * 坐标对象
     */
    @JSONField(name = "location")
    private Location location;

    /**
     * 距离，单位：米，在周边搜索、城市范围搜索传入定位点时返回
     */
    @JSONField(name = "_distance")
    private Double distance;

    /**
     * 行政区划信息对象
     */
    @JSONField(name = "ad_info")
    private AdInfo adInfo;

    @Override
    public String toString() {
        return "POI{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", tel='" + tel + '\'' +
                ", category='" + category + '\'' +
                ", categoryCode=" + categoryCode +
                ", type=" + type +
                ", location=" + location +
                ", distance=" + distance +
                ", adInfo=" + adInfo +
                '}';
    }

    @Getter
    @Setter
    public static class Location {
        /**
         * 纬度
         */
        @JSONField(name = "lat")
        private double lat;

        /**
         * 经度
         */
        @JSONField(name = "lng")
        private double lng;

        @Override
        public String toString() {
            return "Location{" +
                    "lat=" + lat +
                    ", lng=" + lng +
                    '}';
        }
    }

    @Getter
    @Setter
    public static class AdInfo {
        /**
         * 行政区划代码
         */
        @JSONField(name = "adcode")
        private int adcode;

        /**
         * 省
         */
        @JSONField(name = "province")
        private String province;

        /**
         * 市，如果当前城市为省直辖县级区划，此字段会返回为空，由district字段返回。
         */
        @JSONField(name = "city")
        private String city;

        /**
         * 区
         */
        @JSONField(name = "district")
        private String district;

        @Override
        public String toString() {
            return "AdInfo{" +
                    "adcode=" + adcode +
                    ", province='" + province + '\'' +
                    ", city='" + city + '\'' +
                    ", district='" + district + '\'' +
                    '}';
        }
    }
}
