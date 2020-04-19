package com.zscat.mallplus.oms.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 生成订单时传入的参数
 * https://github.com/shenzhuan/mallplus on 2018/8/30.
 */
@Data
public class PaymentParam {
    //"balancepay"
    String trade_type;
    //
    String return_url;
    String url;
    private BigDecimal money;
    //
    private Long orderId;


}
