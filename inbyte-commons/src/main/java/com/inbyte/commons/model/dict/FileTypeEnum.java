package com.inbyte.commons.model.dict;

/**
 * 文件类型
 *
 * @author chenjw
 * @date 2023/03/14
 */
public enum FileTypeEnum {

    Other("其它"),
    Image("图片"),
    Video("视频"),
    Audio("音频"),
    Word("WORD 文档"),
    Pdf("PDF 文档"),
    ;

    public final String name;

    FileTypeEnum(String name) {
        this.name = name;
    }

}
