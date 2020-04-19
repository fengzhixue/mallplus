package com.zscat.mallplus.sys.service.impl;

import cn.hutool.extra.mail.Mail;
import cn.hutool.extra.mail.MailAccount;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.exception.BusinessMallException;
import com.zscat.mallplus.sys.entity.SysEmailConfig;
import com.zscat.mallplus.sys.mapper.SysEmailConfigMapper;
import com.zscat.mallplus.sys.service.ISysEmailConfigService;
import com.zscat.mallplus.sys.vo.EmailVo;
import com.zscat.mallplus.utils.EncryptUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-11-30
 */
@Service
public class SysEmailConfigServiceImpl extends ServiceImpl<SysEmailConfigMapper, SysEmailConfig> implements ISysEmailConfigService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void send(EmailVo emailVo, SysEmailConfig emailConfig) {
        if (emailConfig == null) {
            throw new BusinessMallException("请先配置，再操作");
        }
        // 封装
        MailAccount account = new MailAccount();
        account.setHost(emailConfig.getHost());
        account.setPort(Integer.parseInt(emailConfig.getPort()));
        account.setAuth(true);
        try {
            // 对称解密
            account.setPass(EncryptUtils.desDecrypt(emailConfig.getPass()));
        } catch (Exception e) {
            throw new BusinessMallException(e.getMessage());
        }
        account.setFrom(emailConfig.getUser() + "<" + emailConfig.getFromUser() + ">");
        // ssl方式发送
        account.setSslEnable(true);
        String content = emailVo.getContent();
        // 发送
        try {
            int size = emailVo.getTos().size();
            Mail.create(account)
                    .setTos(emailVo.getTos().toArray(new String[size]))
                    .setTitle(emailVo.getSubject())
                    .setContent(content)
                    .setHtml(true)
                    //关闭session
                    .setUseGlobalSession(false)
                    .send();
        } catch (Exception e) {
            throw new BusinessMallException(e.getMessage());
        }
    }
}
