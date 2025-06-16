# 百胜停车场SDK (BisenPark)

基于Java 17和Spring Boot 3.0开发的江西百胜停车场系统对接SDK。

## 功能特性

### 主动调用接口
- 获取停车场车位信息
- 查询车辆在场记录（分页）
- 查询车辆进出场记录（分页）
- 权限车辆管理
- 用户组管理
- 黑名单管理（查询、新增、移出）
- 在场车辆强制离场
- 修改在场车辆车牌

### 被动接收推送
- 车辆出入场数据推送
- 车辆通道识别数据推送
- 小黄人数据推送

## 快速开始

### 1. 配置参数

在 `application.yml` 中添加以下配置：

```yaml
inbyte:
  iot:
    bisenpark:
      enabled: true
      base-url: https://cloud.bisenaccess.com
      client-id: your-client-id
      client-secret: your-client-secret
      connect-timeout: 30000
      read-timeout: 30000
```

### 2. 使用示例

#### 获取停车场车位信息

```java
@Autowired
private BisenParkService bisenParkService;

public void getParkInfo() {
    try {
        ParkSpaceResponse spaceInfo = bisenParkService.getParkSpaceInfo(57);
        System.out.println("停车场名称: " + spaceInfo.getParkName());
        System.out.println("总车位: " + spaceInfo.getAllCarport());
        System.out.println("剩余车位: " + spaceInfo.getFreeCarport());
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```

#### 查询车辆在场记录

```java
@Autowired
private BisenVehicleService bisenVehicleService;

public void getVehicleInRecords() {
    VehicleInRecordQuery query = VehicleInRecordQuery.builder()
            .parkingId(57)
            .plateNum("赣QQ1111")
            .current(1)
            .size(20)
            .build();

    PageResponse<VehicleInRecordDetail> result = bisenVehicleService.getVehicleInRecords(query);
    System.out.println("总记录数: " + result.getTotal());
}
```

#### 黑名单管理

```java
// 查询黑名单
BlacklistQuery query = BlacklistQuery.builder()
        .parkCode("TCC57310578416")
        .current(1)
        .size(10)
        .build();
PageResponse<BlacklistDetail> result = bisenVehicleService.getBlacklistPage(query);

// 新增黑名单
BlacklistCreate blacklistCreate = BlacklistCreate.builder()
        .parkCode("TCC57310578416")
        .plateNum("赣QQ1111")
        .remark("测试黑名单")
        .build();
Boolean addResult = bisenVehicleService.addBlacklist(blacklistCreate);

// 移出黑名单
Boolean removeResult = bisenVehicleService.removeBlacklist("TCC57310578416", "赣QQ1111");
```

### 3. 接收推送数据

SDK会自动创建以下Webhook接口来接收百胜停车场推送的数据：

- `POST /bisenpark/webhook/uploadRecord` - 车辆出入场数据推送
- `POST /bisenpark/webhook/discernInfo` - 车辆通道识别数据推送
- `POST /bisenpark/webhook/uploadYellowMan` - 小黄人数据推送

你可以通过监听Spring事件来处理这些推送数据：

```java
@Component
public class VehicleEventHandler {
    
    @EventListener
    public void handleVehicleRecord(BisenParkWebhookController.VehicleRecordPushEvent event) {
        VehicleRecordPushData data = event.getData();
        // 处理车辆出入场记录
        System.out.println("车牌: " + data.getPlateNum());
        System.out.println("操作类型: " + data.getPassType()); // 1进 2出
    }
    
    @EventListener
    public void handleVehicleDiscern(BisenParkWebhookController.VehicleDiscernPushEvent event) {
        VehicleDiscernPushData data = event.getData();
        // 处理车辆通道识别数据
        System.out.println("车牌: " + data.getPlateNum());
        System.out.println("通道ID: " + data.getLaneId());
    }
    
    @EventListener
    public void handleYellowMan(BisenParkWebhookController.YellowManPushEvent event) {
        Map<String, Object> data = event.getData();
        // 处理小黄人数据
        System.out.println("车牌: " + data.get("plateno"));
        System.out.println("车辆类型: " + data.get("carType"));
    }
}
```

## 核心组件

### 配置类
- `BisenParkProperties` - 配置属性
- `ComponentIotBisenParkConfiguration` - 自动配置

### 服务类
- `BisenParkClient` - HTTP客户端（包含token管理）
- `BisenParkService` - 停车场相关服务
- `BisenVehicleService` - 车辆相关服务

### 数据模型
- `model/dto/auth/` - 认证相关DTO
- `model/dto/park/` - 停车场相关DTO
- `model/dto/vehicle/` - 车辆相关DTO
- `model/dto/ApiResponse` - 通用API响应包装
- `model/dto/PageResponse` - 分页响应包装

### 控制器
- `BisenParkWebhookController` - 接收推送数据的Webhook控制器

## 技术特性

1. **使用JDK 17的HttpClient** - 无需引入额外的HTTP客户端依赖
2. **自动token管理** - 自动获取和刷新访问令牌
3. **线程安全** - 使用读写锁确保token管理的线程安全
4. **事件驱动** - 使用Spring事件机制处理推送数据
5. **条件装配** - 支持通过配置启用/禁用功能
6. **完善的错误处理** - 统一的异常处理和日志记录

## 接口对照表

| 功能 | 接口路径 | 方法 | 说明 |
|------|----------|------|------|
| 获取停车场车位信息 | `/api/park/space` | POST | 查询停车场总车位和剩余车位 |
| 车辆在场记录 | `/api/car/inRecord` | POST | 分页查询在场车辆记录 |
| 车辆进出场记录 | `/api/car/record` | POST | 分页查询历史进出场记录 |
| 权限车辆管理 | `/api/park/freecar` | POST | 新增/修改/删除权限车辆 |
| 用户组管理 | `/api/park/teams` | POST | 新增/修改/删除用户组 |
| 权限车辆列表 | `/api/car/carList` | POST | 分页查询权限车辆列表 |
| 黑名单列表 | `/api/car/blackPage` | POST | 分页查询黑名单列表 |
| 黑名单新增 | `/api/car/saveBlack` | POST | 添加车辆到黑名单 |
| 黑名单移出 | `/api/car/deleteBlack` | GET | 从黑名单移出车辆 |
| 强制离场 | `/api/car/clearPresenceCar` | POST | 强制车辆离场 |
| 修改车牌 | `/api/car/modifyPresentCarNum` | POST | 修改在场车辆车牌 |

## 注意事项

1. 所有接口调用都需要有效的token，SDK会自动处理token的获取和刷新
2. 推送接口需要在百胜停车场系统中配置你的服务器地址
3. 时间格式统一为字符串（yyyy-MM-dd HH:mm:ss）
4. 分页查询的size参数最大值为100
5. 确保网络连通性，建议配置合适的超时时间

## 许可证

本项目采用MIT许可证。 