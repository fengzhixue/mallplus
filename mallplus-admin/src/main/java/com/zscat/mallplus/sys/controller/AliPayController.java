package com.zscat.mallplus.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zscat.mallplus.sys.entity.SysAlipayConfig;
import com.zscat.mallplus.sys.service.ISysAlipayConfigService;
import com.zscat.mallplus.sys.util.AliPayStatusEnum;
import com.zscat.mallplus.sys.util.AlipayUtils;
import com.zscat.mallplus.sys.vo.TradeVo;
import com.zscat.mallplus.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author mallplus
 * @date 2018-12-31
 */
@Slf4j
@RestController
@RequestMapping("/api/aliPay")
@Api(tags = "工具：支付宝管理")
public class AliPayController {

    @Resource
    private AlipayUtils alipayUtils;

    @Resource
    private ISysAlipayConfigService alipayService;


    @GetMapping(value = "/{id}")
    public Object get() {
        return new CommonResult().success(alipayService.getOne(new QueryWrapper<>()));
    }


    @ApiOperation("配置支付宝")
    @PostMapping(value = "/update")
    public Object payConfig(@Validated @RequestBody SysAlipayConfig alipayConfig) {

        alipayService.updateById(alipayConfig);
        return new CommonResult().success();
    }


    @ApiOperation("PC网页支付")
    @PostMapping(value = "/toPayAsPC")
    public Object toPayAsPC(@Validated @RequestBody TradeVo trade) throws Exception {
        log.info("toPayAsPC");
        SysAlipayConfig alipay = alipayService.getOne(new QueryWrapper<>());
        trade.setOutTradeNo(alipayUtils.getOrderCode());
        String payUrl = alipayService.toPayAsPC(alipay, trade);
        return new CommonResult().success(payUrl);
    }


    @ApiOperation("手机网页支付")
    @PostMapping(value = "/toPayAsWeb")
    public Object toPayAsWeb(@Validated @RequestBody TradeVo trade) throws Exception {
        log.info("toPayAsWeb");
        SysAlipayConfig alipay = alipayService.getOne(new QueryWrapper<>());
        trade.setOutTradeNo(alipayUtils.getOrderCode());
        String payUrl = alipayService.toPayAsWeb(alipay, trade);
        return new CommonResult().success(payUrl);
    }

    @ApiIgnore
    @GetMapping("/return")
    @ApiOperation("支付之后跳转的链接")
    public Object returnPage(HttpServletRequest request, HttpServletResponse response) {
        log.info("returnPage");
        SysAlipayConfig alipay = alipayService.getOne(new QueryWrapper<>());
        response.setContentType("text/html;charset=" + alipay.getCharset());
        //内容验签，防止黑客篡改参数
        if (alipayUtils.rsaCheck(request, alipay)) {
            //商户订单号
            String outTradeNo = new String(request.getParameter("out_trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            //支付宝交易号
            String tradeNo = new String(request.getParameter("trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            System.out.println("商户订单号" + outTradeNo + "  " + "第三方交易号" + tradeNo);

            // 根据业务需要返回数据，这里统一返回OK
            return new CommonResult().success("payment successful", HttpStatus.OK);
        } else {
            // 根据业务需要返回数据
            return new CommonResult().success(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiIgnore
    @RequestMapping("/notify")
    @ApiOperation("支付异步通知(要公网访问)，接收异步通知，检查通知内容app_id、out_trade_no、total_amount是否与请求中的一致，根据trade_status进行后续业务处理")
    public Object notify(HttpServletRequest request) {
        log.info("notify");
        SysAlipayConfig alipay = alipayService.getOne(new QueryWrapper<>());
        Map<String, String[]> parameterMap = request.getParameterMap();
        //内容验签，防止黑客篡改参数
        if (alipayUtils.rsaCheck(request, alipay)) {
            //交易状态
            String tradeStatus = new String(request.getParameter("trade_status").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            // 商户订单号
            String outTradeNo = new String(request.getParameter("out_trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            //支付宝交易号
            String tradeNo = new String(request.getParameter("trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            //付款金额
            String totalAmount = new String(request.getParameter("total_amount").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            //验证
            if (tradeStatus.equals(AliPayStatusEnum.SUCCESS.getValue()) || tradeStatus.equals(AliPayStatusEnum.FINISHED.getValue())) {
                // 验证通过后应该根据业务需要处理订单
            }
            return new CommonResult().success(HttpStatus.OK);
        }
        return new CommonResult().success(HttpStatus.BAD_REQUEST);
    }
}
