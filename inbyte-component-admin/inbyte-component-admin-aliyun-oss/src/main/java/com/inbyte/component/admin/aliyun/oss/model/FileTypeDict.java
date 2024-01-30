package com.inbyte.component.admin.aliyun.oss.model;

/**
 * 文件类型
 *
 * @author chenjw
 * @date 2023/03/14
 */
public enum FileTypeDict {

    Other(0, "其它"),
    Image(1, "图片"),
    Video(2, "视频"),
    Audio(3, "音频"),
    Word(4, "WORD 文档"),
    Pdf(5, "PDF 文档"),
    ;

    public final Integer code;
    public final String name;

    FileTypeDict(int code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 根据code值取得订单类型枚举
     * @param code
     * @return
     */
    public static FileTypeDict getByCode(Integer code) {
        FileTypeDict[] values = FileTypeDict.values();
        for (FileTypeDict otc : values) {
            if (otc.code.equals(code)) {
                return otc;
            }
        }
        throw new IllegalArgumentException("订单类型" + code + "的枚举:" + FileTypeDict.class.getName() + "未定义");
    }
}
