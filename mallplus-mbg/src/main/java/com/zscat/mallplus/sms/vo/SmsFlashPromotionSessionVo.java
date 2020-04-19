package com.zscat.mallplus.sms.vo;

import com.zscat.mallplus.sms.entity.SmsFlashPromotionSession;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 限时购场次表
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Data
public class SmsFlashPromotionSessionVo implements Serializable {

    private String lovely;
    private Integer seckillTimeIndex;
    private List<SmsFlashPromotionSession> seckillTime;

}
