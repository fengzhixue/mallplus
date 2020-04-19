package com.zscat.mallplus.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.sys.entity.SysAlipayConfig;
import com.zscat.mallplus.sys.vo.TradeVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-11-30
 */
public interface ISysAlipayConfigService extends IService<SysAlipayConfig> {


    /**
     * 处理来自PC的交易请求
     *
     * @param alipay 支付宝配置
     * @param trade  交易详情
     * @return String
     * @throws Exception 异常
     */
    String toPayAsPC(SysAlipayConfig alipay, TradeVo trade) throws Exception;

    /**
     * 处理来自手机网页的交易请求
     *
     * @param alipay 支付宝配置
     * @param trade  交易详情
     * @return String
     * @throws Exception 异常
     */
    String toPayAsWeb(SysAlipayConfig alipay, TradeVo trade) throws Exception;
}
