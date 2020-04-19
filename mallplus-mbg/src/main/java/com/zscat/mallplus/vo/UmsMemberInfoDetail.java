package com.zscat.mallplus.vo;

import com.zscat.mallplus.sms.entity.SmsCouponHistory;
import com.zscat.mallplus.ums.entity.UmsMember;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 会员表
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Data
public class UmsMemberInfoDetail implements Serializable {

    private UmsMember member;
    private List<SmsCouponHistory> histories;
}
