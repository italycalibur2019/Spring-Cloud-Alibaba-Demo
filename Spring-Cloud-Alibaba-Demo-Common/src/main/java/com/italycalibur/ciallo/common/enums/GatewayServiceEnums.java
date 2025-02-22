package com.italycalibur.ciallo.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 模块枚举类
 * @author dhr
 * @date 2025-02-20 10:14:03
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public enum GatewayServiceEnums {
    ADMIN_SERVICE("admin-service", "管理模块"),
    BASIC_SERVICE("basic-service", "基础模块"),
    ORDER_SERVICE("order-service", "订单模块"),
    STOCK_SERVICE("stock-service", "库存模块"),
    FEE_SERVICE("fee-service", "费用模块"),
    GATEWAY_SERVICE("gateway-service", "网关模块"),
    UNKNOWN("unknown", "未知服务");

    private final String code;
    private final String desc;

    public static String getDescByCode(String routeId) {
        for (GatewayServiceEnums value : GatewayServiceEnums.values()) {
            if (value.getCode().equals(routeId)) {
                return value.getDesc();
            }
        }
        return UNKNOWN.getDesc();
    }
}
