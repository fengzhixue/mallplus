package com.zscat.mallplus.sys.service.impl;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.exception.BusinessMallException;
import com.zscat.mallplus.sys.entity.SysAlipayConfig;
import com.zscat.mallplus.sys.mapper.SysAlipayConfigMapper;
import com.zscat.mallplus.sys.service.ISysAlipayConfigService;
import com.zscat.mallplus.sys.vo.TradeVo;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-11-30
 */
@Service
public class SysAlipayConfigServiceImpl extends ServiceImpl<SysAlipayConfigMapper, SysAlipayConfig> implements ISysAlipayConfigService {

    @Override
    public String toPayAsPC(SysAlipayConfig alipay, TradeVo trade) throws Exception {

        if (alipay.getId() == null) {
            throw new BusinessMallException("请先添加相应配置，再操作");
        }
        AlipayClient alipayClient = new DefaultAlipayClient(alipay.getGatewayUrl(), alipay.getAppId(), alipay.getPrivateKey(), alipay.getFormat(), alipay.getCharset(), alipay.getPublicKey(), alipay.getSignType());

//        double money = Double.parseDouble(trade.getTotalAmount());

        // 创建API对应的request(电脑网页版)
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();

        // 订单完成后返回的页面和异步通知地址
        request.setReturnUrl(alipay.getReturnUrl());
        request.setNotifyUrl(alipay.getNotifyUrl());
        // 填充订单参数
        request.setBizContent("{" +
                "    \"out_trade_no\":\"" + trade.getOutTradeNo() + "\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":" + trade.getTotalAmount() + "," +
                "    \"subject\":\"" + trade.getSubject() + "\"," +
                "    \"body\":\"" + trade.getBody() + "\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"" + alipay.getSysServiceProviderId() + "\"" +
                "    }" +
                "  }");//填充业务参数
        // 调用SDK生成表单, 通过GET方式，口可以获取url
        return alipayClient.pageExecute(request, "GET").getBody();

    }

    @Override
    public String toPayAsWeb(SysAlipayConfig alipay, TradeVo trade) throws Exception {
        if (alipay.getId() == null) {
            throw new BusinessMallException("请先添加相应配置，再操作");
        }
        AlipayClient alipayClient = new DefaultAlipayClient(alipay.getGatewayUrl(), alipay.getAppId(), alipay.getPrivateKey(), alipay.getFormat(), alipay.getCharset(), alipay.getPublicKey(), alipay.getSignType());

        double money = Double.parseDouble(trade.getTotalAmount());
        if (money <= 0 || money >= 5000) {
            throw new BusinessMallException("测试金额过大");
        }
        // 创建API对应的request(手机网页版)
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setReturnUrl(alipay.getReturnUrl());
        request.setNotifyUrl(alipay.getNotifyUrl());
        request.setBizContent("{" +
                "    \"out_trade_no\":\"" + trade.getOutTradeNo() + "\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":" + trade.getTotalAmount() + "," +
                "    \"subject\":\"" + trade.getSubject() + "\"," +
                "    \"body\":\"" + trade.getBody() + "\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"" + alipay.getSysServiceProviderId() + "\"" +
                "    }" +
                "  }");//填充业务参数
        return alipayClient.pageExecute(request, "GET").getBody();
    }

}
