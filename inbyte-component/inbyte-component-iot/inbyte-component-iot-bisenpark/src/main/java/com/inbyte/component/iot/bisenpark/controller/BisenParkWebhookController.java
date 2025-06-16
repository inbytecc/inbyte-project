package com.inbyte.component.iot.bisenpark.controller;

import com.inbyte.commons.model.dto.R;
import com.inbyte.component.iot.bisenpark.model.dto.vehicle.VehicleDiscernPushData;
import com.inbyte.component.iot.bisenpark.model.dto.vehicle.VehicleRecordPushData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 百胜停车场推送数据接收控制器
 *
 * @author chenjw
 * @date 2025-01-16
 */
@Slf4j
@RestController
@RequestMapping("/bisenpark/webhook")
@ConditionalOnProperty(prefix = "inbyte.iot.bisenpark", name = "enabled", havingValue = "true", matchIfMissing = true)
public class BisenParkWebhookController {

    private final ApplicationEventPublisher eventPublisher;

    public BisenParkWebhookController(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * 接收车辆出入场数据推送
     *
     * @param pushData 推送数据
     * @return 处理结果
     */
    @PostMapping("/uploadRecord")
    public R<Void> receiveVehicleRecord(@RequestBody VehicleRecordPushData pushData) {
        try {
            log.info("接收到车辆出入场数据推送，车牌号: {}, 停车场: {}, 类型: {}", 
                    pushData.getPlateNum(), pushData.getParkName(), pushData.getPassType());
            
            // 发布事件，让业务系统处理
            eventPublisher.publishEvent(new VehicleRecordPushEvent(pushData));
            
            return R.ok();
        } catch (Exception e) {
            log.error("处理车辆出入场数据推送异常", e);
            return R.error("处理失败");
        }
    }

    /**
     * 接收车辆通道识别数据推送
     *
     * @param pushData 推送数据
     * @return 处理结果
     */
    @PostMapping("/discernInfo")
    public R<Void> receiveDiscernInfo(@RequestBody VehicleDiscernPushData pushData) {
        try {
            log.info("接收到车辆通道识别数据推送，车牌号: {}, 停车场ID: {}, 类型: {}", 
                    pushData.getPlateNum(), pushData.getParkingId(), pushData.getPassType());
            
            // 发布事件，让业务系统处理
            eventPublisher.publishEvent(new VehicleDiscernPushEvent(pushData));
            
            return R.ok();
        } catch (Exception e) {
            log.error("处理车辆通道识别数据推送异常", e);
            return R.error("处理失败");
        }
    }

    /**
     * 接收小黄人数据推送
     *
     * @param pushData 推送数据
     * @return 处理结果
     */
    @PostMapping("/uploadYellowMan")
    public R<Map<String, Object>> receiveYellowManData(@RequestBody Map<String, Object> pushData) {
        try {
            log.info("接收到小黄人数据推送，车牌号: {}, 停车场ID: {}", 
                    pushData.get("plateno"), pushData.get("parkId"));
            
            // 发布事件，让业务系统处理
            eventPublisher.publishEvent(new YellowManPushEvent(pushData));
            
            // 按照文档要求返回特定格式
            return R.ok(Map.of(
                "code", 0,
                "timestamp", String.valueOf(System.currentTimeMillis()),
                "msg", "success"
            ));
        } catch (Exception e) {
            log.error("处理小黄人数据推送异常", e);
            return R.ok(Map.of(
                "code", 1,
                "timestamp", String.valueOf(System.currentTimeMillis()),
                "msg", "处理失败: " + e.getMessage()
            ));
        }
    }

    /**
     * 车辆出入场记录推送事件
     */
    public static class VehicleRecordPushEvent {
        private final VehicleRecordPushData data;

        public VehicleRecordPushEvent(VehicleRecordPushData data) {
            this.data = data;
        }

        public VehicleRecordPushData getData() {
            return data;
        }
    }

    /**
     * 车辆通道识别推送事件
     */
    public static class VehicleDiscernPushEvent {
        private final VehicleDiscernPushData data;

        public VehicleDiscernPushEvent(VehicleDiscernPushData data) {
            this.data = data;
        }

        public VehicleDiscernPushData getData() {
            return data;
        }
    }

    /**
     * 小黄人数据推送事件
     */
    public static class YellowManPushEvent {
        private final Map<String, Object> data;

        public YellowManPushEvent(Map<String, Object> data) {
            this.data = data;
        }

        public Map<String, Object> getData() {
            return data;
        }
    }
} 