package com.inbyte.commons.model.dict;

/**
 * 文件类型
 *
 * @author chenjw
 * @date 2023/03/14
 */
public enum FileTypeEnum {

    OTHER("其它"),
    IMAGE("图片"),
    VIDEO("视频"),
    AUDIO("音频"),
    DOC("文档"),
    PDF("PDF"),
    ;

    public final String name;

    FileTypeEnum(String name) {
        this.name = name;
    }

}
