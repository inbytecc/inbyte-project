package com.inbyte.component.common.weixin.enterprise.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WxCpTextMessageParam {

    /**
     * 企业微信群机器人
     */
    private String groupRobotWebHookKey;
    /**
     * 文本内容，最长不超过2048个字节，必须是utf8编码
     */
//    @Length(max = 2048, message = "消息内容最长不超过2048个字节")
    private String text;
    /**
     * userId的列表，提醒群中的指定成员(@某个成员)，@all表示提醒所有人，如果开发者获取不到userId，可以使用mentioned_mobile_list
     */
    private List<String> mentionedList;
    /**
     * 手机号列表，提醒手机号对应的群成员(@某个成员)，@all表示提醒所有人
     */
    private List<String> mobileList;
}