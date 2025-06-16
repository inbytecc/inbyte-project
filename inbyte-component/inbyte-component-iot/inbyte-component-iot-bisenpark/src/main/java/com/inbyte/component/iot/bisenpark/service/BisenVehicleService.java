package com.inbyte.component.iot.bisenpark.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.inbyte.component.iot.bisenpark.model.dto.ApiResponse;
import com.inbyte.component.iot.bisenpark.model.dto.PageResponse;
import com.inbyte.component.iot.bisenpark.model.dto.vehicle.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * 百胜停车场车辆服务
 *
 * @author chenjw
 * @date 2025-01-16
 */
@Slf4j
@Service
@ConditionalOnProperty(prefix = "inbyte.iot.bisenpark", name = "enabled", havingValue = "true", matchIfMissing = true)
public class BisenVehicleService {

    @Autowired
    private BisenParkClient bisenParkClient;

    /**
     * 查询车辆在场记录（分页）
     *
     * @param query 查询条件
     * @return 在场记录分页数据
     */
    public PageResponse<VehicleInRecordDetail> getVehicleInRecords(VehicleInRecordQuery query) {
        try {
            ApiResponse<PageResponse<VehicleInRecordDetail>> response = bisenParkClient.post("/api/car/inRecord", query,
                    new TypeReference<ApiResponse<PageResponse<VehicleInRecordDetail>>>() {});
            
            if (response.isSuccess()) {
                return response.getData();
            } else {
                log.error("查询车辆在场记录失败: {}", response.getMsg());
                throw new RuntimeException("查询车辆在场记录失败: " + response.getMsg());
            }
        } catch (Exception e) {
            log.error("查询车辆在场记录异常，parkingId: {}", query.getParkingId(), e);
            throw new RuntimeException("查询车辆在场记录异常", e);
        }
    }

    /**
     * 查询黑名单列表（分页）
     *
     * @param query 查询条件
     * @return 黑名单分页数据
     */
    public PageResponse<BlacklistDetail> getBlacklistPage(BlacklistQuery query) {
        try {
            ApiResponse<PageResponse<BlacklistDetail>> response = bisenParkClient.post("/api/car/blackPage", query,
                    new TypeReference<ApiResponse<PageResponse<BlacklistDetail>>>() {});
            
            if (response.isSuccess()) {
                return response.getData();
            } else {
                log.error("查询黑名单列表失败: {}", response.getMsg());
                throw new RuntimeException("查询黑名单列表失败: " + response.getMsg());
            }
        } catch (Exception e) {
            log.error("查询黑名单列表异常，parkCode: {}", query.getParkCode(), e);
            throw new RuntimeException("查询黑名单列表异常", e);
        }
    }

    /**
     * 新增黑名单
     *
     * @param blacklistCreate 黑名单信息
     * @return 是否成功
     */
    public Boolean addBlacklist(BlacklistCreate blacklistCreate) {
        try {
            ApiResponse<Boolean> response = bisenParkClient.post("/api/car/saveBlack", blacklistCreate,
                    new TypeReference<ApiResponse<Boolean>>() {});
            
            if (response.isSuccess()) {
                return response.getData();
            } else {
                log.error("新增黑名单失败: {}", response.getMsg());
                throw new RuntimeException("新增黑名单失败: " + response.getMsg());
            }
        } catch (Exception e) {
            log.error("新增黑名单异常，plateNum: {}", blacklistCreate.getPlateNum(), e);
            throw new RuntimeException("新增黑名单异常", e);
        }
    }

    /**
     * 移出黑名单
     *
     * @param parkCode 停车场编号
     * @param plateNum 车牌号
     * @return 是否成功
     */
    public Boolean removeBlacklist(String parkCode, String plateNum) {
        try {
            String path = String.format("/api/car/deleteBlack?parkCode=%s&carNumber=%s", parkCode, plateNum);
            ApiResponse<Boolean> response = bisenParkClient.get(path,
                    new TypeReference<ApiResponse<Boolean>>() {});
            
            if (response.isSuccess()) {
                return response.getData();
            } else {
                log.error("移出黑名单失败: {}", response.getMsg());
                throw new RuntimeException("移出黑名单失败: " + response.getMsg());
            }
        } catch (Exception e) {
            log.error("移出黑名单异常，parkCode: {}, plateNum: {}", parkCode, plateNum, e);
            throw new RuntimeException("移出黑名单异常", e);
        }
    }
} 