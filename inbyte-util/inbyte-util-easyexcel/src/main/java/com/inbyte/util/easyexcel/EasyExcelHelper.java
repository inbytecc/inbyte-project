package com.inbyte.util.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.inbyte.commons.util.WebUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.net.URLEncoder;
import java.util.List;

/**
 * 数据报表导出
 *
 * @author chenjw
 * @date 2024/10/24
 */
@Slf4j
public class EasyExcelHelper {

    /**
     * 导出Excel
     *
     * @param clz
     * @param data
     */
    public static void write(Class clz, List<?> data) {
        write(clz, data, "报表");
    }

    /**
     * 导出Excel
     *
     * @param clz
     * @param data
     * @param fileName
     */
    public static void write(Class clz, List<?> data, String fileName) {
        HttpServletResponse response = WebUtil.getCurrentResponse();

        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode( fileName, "UTF-8") + ".xlsx");

            EasyExcel.write(response.getOutputStream(), clz)
                    .sheet("数据")
                    .doWrite(data);
        } catch (Exception e) {
            // 记录日志
            log.error("Excel写入失败", e);
        }
    }

}
