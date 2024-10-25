package com.inbyte.util.easyexcel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * 枚举对象转换器
 *
 * @author chenjw
 * @date 2024/10/24
 */
public class GenericEnumNameConverter implements Converter<Object> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return Object.class;  // 支持任意类型，稍后通过反射确定是否是枚举类型
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;  // 我们假设 Excel 中的数据是字符串类型
    }

    @Override
    public Object convertToJavaData(ReadCellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        String value = cellData.getStringValue();
        Class<?> fieldType = contentProperty.getField().getType();

        // 判断字段类型是否是枚举类型
        if (fieldType.isEnum()) {
            // 通过枚举的 name 来匹配
            for (Object enumConstant : fieldType.getEnumConstants()) {
                if (enumConstant.toString().equalsIgnoreCase(value)) {
                    return enumConstant;
                }
            }
            throw new IllegalArgumentException("Unknown enum value: " + value + " for enum: " + fieldType.getName());
        }

        // 如果字段不是枚举类型，可以添加其他处理逻辑
        throw new IllegalArgumentException("Field is not an enum: " + fieldType.getName());
    }

    @Override
    public WriteCellData<String> convertToExcelData(Object value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        // 确认 value 是枚举类型并返回枚举的 name 值
        if (value != null && value.getClass().isEnum()) {
            return new WriteCellData<>(((Enum<?>) value).name());
        }

        // 如果不是枚举类型，可以添加其他处理逻辑
        throw new IllegalArgumentException("Value is not an enum: " + value.getClass().getName());
    }
}
