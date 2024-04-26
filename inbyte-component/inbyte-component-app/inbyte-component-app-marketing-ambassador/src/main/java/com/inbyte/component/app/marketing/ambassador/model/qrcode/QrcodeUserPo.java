package com.inbyte.component.app.marketing.ambassador.model.qrcode;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 用户二维码实体
 *
 * @author chenjw
 * @date 2023-03-17 11:42:07
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@TableName("qrcode_user")
public class QrcodeUserPo {

    /**
     * 二维码ID
     */
    @TableId(value = "qcid", type = IdType.AUTO)
    private Integer qcid;

    /**
     * 分享人ID
     */
    private Integer userId;

    /**
     * 外部用户ID
     */
    private Integer eid;

    /**
     * 分享路径
     */
    private String path;

    /**
     * 分享参数
     */
    private String pathParam;

    /**
     * 模块名
     */
    private String pathName;

    /**
     * 查看次数
     */
    private Integer viewCount;

    /**
     * 线索个数
     */
    private Integer clueCount;

    /**
     * 注册用户数
     */
    private Integer registerCount;

    /**
     * 二维码图片
     */
    private byte[] qrCodeImg;

    /**
     * 逻辑删除
     */
    private Integer deleted;

    /**
     * 小程序ID
     */
    private String appId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
