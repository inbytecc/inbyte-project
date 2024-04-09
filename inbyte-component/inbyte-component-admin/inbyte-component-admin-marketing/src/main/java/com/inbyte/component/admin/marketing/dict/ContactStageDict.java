package com.inbyte.component.admin.marketing.dict;

/**
 * 跟进阶段
 *
 * @author chenjw
 * @date 2023-03-09 11:33:27
 */
public enum ContactStageDict {

    Waiting_For_Contact(0, "待联系"),
    Establish_Relation(1, "建立联系"),
    Intention_Confirmed(2, "确认意向"),
    Promised_Visit(3, "承诺到访"),
    Trial_Class_Booked(4, "预约试听"),
    Trial_Class_Finished(5, "完成试听"),
    Absent(6, "未到课回访"),
    Made_A_Deal(7, "成交"),
    Abandon(-1, "放弃线索"),
    ;

    public final int code;
    public final String name;

    ContactStageDict(int code, String name) {
        this.code = code;
        this.name = name;
    }

}
