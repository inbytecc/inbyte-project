package com.inbyte.component.common.district.model.tencent;

import lombok.Getter;
import lombok.Setter;

/**
 * 腾讯地图地点搜索参数
 */
@Getter
@Setter
public class TencentMapPlaceSearchParam {

    /**
     * 搜索关键字，长度最大96个字节，注意键值要进行URL编码
     */
    private String keyword;

    /**
     * 搜索边界参数，格式为：
     * boundary=nearby(lat,lng,radius[, auto_extend])
     */
    private String boundary;

//    /**
//     * 是否返回子地点，如大厦停车场、出入口等，0不返回，1返回
//     */
//    private Integer getSubpois;

    /**
     * 筛选条件，支持指定分类筛选和排除指定分类
     */
    private String filter;

//    /**
//     * 返回指定标准附加字段，如category_code - poi分类编码
//     */
//    private String addedFields;

//    /**
//     * 排序方式，支持按距离由近到远排序，取值：_distance
//     */
//    private String orderby;
//
//    /**
//     * 每页条目数，最大限制为20条，默认为10条
//     */
//    private Integer pageSize;
//
//    /**
//     * 第x页，默认第1页
//     */
//    private Integer pageIndex;


}
