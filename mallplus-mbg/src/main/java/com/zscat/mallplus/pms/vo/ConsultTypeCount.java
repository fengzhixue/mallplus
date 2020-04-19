package com.zscat.mallplus.pms.vo;

import com.zscat.mallplus.utils.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Auther: shenzhuan
 * @Date: 2019/2/23 13:12
 * @Description:
 */
@Data
public class ConsultTypeCount extends BaseEntity implements Serializable {
    private int all;
    private int goods;
    private int general;
    private int bad;
    private int other;

    private BigDecimal persent;

}
