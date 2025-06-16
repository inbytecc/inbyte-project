package com.inbyte.component.iot.bisenpark.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.inbyte.component.iot.bisenpark.model.dto.ApiResponse;
import com.inbyte.component.iot.bisenpark.model.dto.park.ParkSpaceRequest;
import com.inbyte.component.iot.bisenpark.model.dto.park.ParkSpaceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * 百胜停车场业务服务
 *
 * @author chenjw
 * @date 2025-01-16
 */
@Slf4j
@Service
@ConditionalOnProperty(prefix = "inbyte.iot.bisenpark", name = "enabled", havingValue = "true", matchIfMissing = true)
public class BisenParkService {

    @Autowired
    private BisenParkClient bisenParkClient;

    /**
     * 获取停车场车位信息
     *
     * @param parkingId 停车场唯一标识
     * @return 停车场车位信息
     */
    public ParkSpaceResponse getParkSpaceInfo(Integer parkingId) {
        try {
            ParkSpaceRequest request = ParkSpaceRequest.create(parkingId);
            ApiResponse<ParkSpaceResponse> response = bisenParkClient.post("/api/park/space", request, 
                    new TypeReference<ApiResponse<ParkSpaceResponse>>() {});
            
            if (response.isSuccess()) {
                return response.getData();
            } else {
                log.error("获取停车场车位信息失败: {}", response.getMsg());
                throw new RuntimeException("获取停车场车位信息失败: " + response.getMsg());
            }
        } catch (Exception e) {
            log.error("获取停车场车位信息异常，parkingId: {}", parkingId, e);
            throw new RuntimeException("获取停车场车位信息异常", e);
        }
    }
} 