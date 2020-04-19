package com.zscat.mallplus.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.sms.entity.SmsRedPacket;

/**
 * <p>
 * 红包 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
public interface ISmsRedPacketService extends IService<SmsRedPacket> {

    int createRedPacket(SmsRedPacket smsRedPacket);

    int acceptRedPacket(Integer id);
}
