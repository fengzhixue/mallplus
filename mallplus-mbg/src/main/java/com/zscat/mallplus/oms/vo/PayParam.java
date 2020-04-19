package com.zscat.mallplus.oms.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 生成订单时传入的参数
 * https://github.com/shenzhuan/mallplus on 2018/8/30.
 */
@Data
public class PayParam {

    //"balancepay"
    String payment_code;
    //
    PaymentParam params;
    private BigDecimal payAmount;
    private BigDecimal balance;
    private Integer payment_type;
    //
    private Long orderId;
    private Long memberGroupId;

}
