package com.zscat.mallplus.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.sys.entity.SysEmailConfig;
import com.zscat.mallplus.sys.vo.EmailVo;
import org.springframework.scheduling.annotation.Async;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-11-30
 */
public interface ISysEmailConfigService extends IService<SysEmailConfig> {

    /**
     * 发送邮件
     *
     * @param emailVo     邮件发送的内容
     * @param emailConfig 邮件配置
     * @throws Exception /
     */
    @Async
    void send(EmailVo emailVo, SysEmailConfig emailConfig) throws Exception;
}
