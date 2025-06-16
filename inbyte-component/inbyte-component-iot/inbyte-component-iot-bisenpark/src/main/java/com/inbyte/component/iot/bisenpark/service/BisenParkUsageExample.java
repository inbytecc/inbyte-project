package com.inbyte.component.iot.bisenpark.service;

import com.inbyte.component.iot.bisenpark.controller.BisenParkWebhookController;
import com.inbyte.component.iot.bisenpark.model.dto.PageResponse;
import com.inbyte.component.iot.bisenpark.model.dto.park.ParkSpaceResponse;
import com.inbyte.component.iot.bisenpark.model.dto.vehicle.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 百胜停车场SDK使用示例
 * 这个类展示了如何使用百胜停车场SDK的各个功能
 *
 * @author chenjw
 * @date 2025-01-16
 */
@Slf4j
@Component
public class BisenParkUsageExample {

    @Autowired
    private BisenParkService bisenParkService;

    @Autowired
    private BisenVehicleService bisenVehicleService;

    /**
     * 示例：获取停车场车位信息
     */
    public void exampleGetParkSpaceInfo() {
        try {
            // 获取停车场ID为57的车位信息
            ParkSpaceResponse spaceInfo = bisenParkService.getParkSpaceInfo(57);
            log.info("停车场车位信息 - 停车场名称: {}, 总车位: {}, 剩余车位: {}", 
                    spaceInfo.getParkName(), spaceInfo.getAllCarport(), spaceInfo.getFreeCarport());
        } catch (Exception e) {
            log.error("获取停车场车位信息失败", e);
        }
    }

    /**
     * 示例：查询车辆在场记录
     */
    public void exampleGetVehicleInRecords() {
        try {
            VehicleInRecordQuery query = VehicleInRecordQuery.builder()
                    .parkingId(57)
                    .plateNum("赣QQ1111")
                    .current(1)
                    .size(20)
                    .build();

            PageResponse<VehicleInRecordDetail> result = bisenVehicleService.getVehicleInRecords(query);
            log.info("查询到车辆在场记录 {} 条", result.getTotal());
        } catch (Exception e) {
            log.error("查询车辆在场记录失败", e);
        }
    }

    /**
     * 示例：查询黑名单列表
     */
    public void exampleGetBlacklistPage() {
        try {
            BlacklistQuery query = BlacklistQuery.builder()
                    .parkCode("TCC57310578416")
                    .current(1)
                    .size(10)
                    .build();

            PageResponse<BlacklistDetail> result = bisenVehicleService.getBlacklistPage(query);
            log.info("查询到黑名单记录 {} 条", result.getTotal());
        } catch (Exception e) {
            log.error("查询黑名单列表失败", e);
        }
    }

    /**
     * 示例：新增黑名单
     */
    public void exampleAddBlacklist() {
        try {
            BlacklistCreate blacklistCreate = BlacklistCreate.builder()
                    .parkCode("TCC57310578416")
                    .plateNum("赣QQ1111")
                    .remark("测试黑名单")
                    .build();

            Boolean result = bisenVehicleService.addBlacklist(blacklistCreate);
            log.info("新增黑名单结果: {}", result);
        } catch (Exception e) {
            log.error("新增黑名单失败", e);
        }
    }

    /**
     * 示例：移出黑名单
     */
    public void exampleRemoveBlacklist() {
        try {
            Boolean result = bisenVehicleService.removeBlacklist("TCC57310578416", "赣QQ1111");
            log.info("移出黑名单结果: {}", result);
        } catch (Exception e) {
            log.error("移出黑名单失败", e);
        }
    }

    /**
     * 监听车辆出入场记录推送事件
     */
    @EventListener
    public void handleVehicleRecordPush(BisenParkWebhookController.VehicleRecordPushEvent event) {
        VehicleRecordPushData data = event.getData();
        log.info("处理车辆出入场记录推送 - 车牌: {}, 停车场: {}, 操作类型: {}", 
                data.getPlateNum(), data.getParkName(), data.getPassType());
        
        // 在这里实现业务逻辑
        // 例如：保存到数据库、发送通知等
    }

    /**
     * 监听车辆通道识别推送事件
     */
    @EventListener
    public void handleVehicleDiscernPush(BisenParkWebhookController.VehicleDiscernPushEvent event) {
        VehicleDiscernPushData data = event.getData();
        log.info("处理车辆通道识别推送 - 车牌: {}, 停车场ID: {}, 通道ID: {}", 
                data.getPlateNum(), data.getParkingId(), data.getLaneId());
        
        // 在这里实现业务逻辑
    }

    /**
     * 监听小黄人数据推送事件
     */
    @EventListener
    public void handleYellowManPush(BisenParkWebhookController.YellowManPushEvent event) {
        var data = event.getData();
        log.info("处理小黄人数据推送 - 车牌: {}, 停车场ID: {}", 
                data.get("plateno"), data.get("parkId"));
        
        // 在这里实现业务逻辑
    }
} 