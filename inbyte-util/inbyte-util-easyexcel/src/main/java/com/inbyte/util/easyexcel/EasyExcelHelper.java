package com.inbyte.util.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.inbyte.commons.util.WebUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 枚举对象转换器
 *
 * @author chenjw
 * @date 2024/10/24
 */
@Slf4j
public class EasyExcelHelper {

    public static void write(Class clz, List<?> data) {
        HttpServletResponse response = WebUtil.getCurrentResponse();

        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=report.xlsx");

            EasyExcel.write(response.getOutputStream(), clz)
                    .sheet("模板")
                    .doWrite(data);
        } catch (Exception e) {
            // 记录日志
            log.error("Excel写入失败", e);
            throw new RuntimeException("导出Excel失败", e);
//            response.setContentType("application/json; charset=UTF-8");
//            response.setCharacterEncoding("UTF-8");
//            response.setStatus(400);
//            PrintWriter writer = null;
//            try {
//                writer = response.getWriter();
//                writer.write("{\"status\":400,\"msg\":\"导出Excel文件出错\"}");
//            } catch (IOException ex) {
//                log.error("WebUtil 输出 400 responseBody 数据异常", ex);
//            } finally {
//                if (writer != null) {
//                    writer.close();
//                }
//            }
        }
    }

}
