package com.zscat.mallplus.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.oms.entity.OmsOrder;
import com.zscat.mallplus.oms.vo.*;
import com.zscat.mallplus.utils.CommonResult;
import com.zscat.mallplus.vo.ApplyRefundVo;
import com.zscat.mallplus.vo.CartParam;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-04-17
 */
public interface IOmsOrderService extends IService<OmsOrder> {

    /**
     * 根据提交信息生成订单
     */
    @Transactional
    CommonResult generateOrder(OrderParam orderParam);

    /**
     * 支付成功后的回调
     */
    @Transactional
    CommonResult paySuccess(Long orderId);

    /**
     * 自动取消超时订单
     */
    @Transactional
    CommonResult cancelTimeOutOrder();

    /**
     * 取消单个超时订单
     */
    @Transactional
    void cancelOrder(Long orderId);

    /**
     * 发送延迟消息取消订单
     */
    void sendDelayMessageCancelOrder(Long orderId);

    /**
     * 获取用户可用优惠券列表
     *
     * @param orderParam
     * @return
     */
    Object couponHistoryDetailList(OrderParam orderParam);

    /**
     * 预览订单
     *
     * @param orderParam
     * @return
     */
    ConfirmOrderResult submitPreview(OrderParam orderParam);

    /**
     * 多店铺预览订单
     *
     * @param orderParam
     * @return
     */
    ConfirmListOrderResult submitStorePreview(OrderParam orderParam);

    /**
     * pc 支付
     *
     * @param tbThanks
     * @return
     */
    int payOrder(TbThanks tbThanks);

    /**
     * 添加购物车
     *
     * @param cartParam
     * @return
     */
    Object addCart(CartParam cartParam);

    /**
     * 开团
     *
     * @param orderParam
     * @return
     */
    Object addGroup(OrderParam orderParam);

    /**
     * 参团
     *
     * @param orderParam
     * @return
     */
    Object acceptGroup(OrderParam orderParam);

    /**
     * 积分兑换
     *
     * @param payParam
     * @return
     */
    Object jifenPay(OrderParam payParam);

    /**
     * 关闭订单
     *
     * @param newE
     * @return
     */
    boolean closeOrder(OmsOrder newE);

    /**
     * 释放库存和销量
     *
     * @param newE
     */
    void releaseStock(OmsOrder newE);

    /**
     * 取消发货
     *
     * @param order
     * @param remark
     * @return
     */
    int cancleDelivery(OmsOrder order, String remark);

    /**
     * 确认收货
     *
     * @param id
     * @return
     */
    Object confimDelivery(Long id);

    /**
     * 余额支付
     *
     * @param order
     * @return
     */
    OmsOrder blancePay(OmsOrder order);

    OmsOrder blancePay(PayParam payParam);

    /**
     * 团购商品订单预览
     *
     * @param orderParam
     * @return
     */
    Object preGroupActivityOrder(OrderParam orderParam);

    /**
     * 申请退款
     *
     * @param id
     * @return
     */
    Object applyRefund(Long id);

    /**
     * 订单评论
     *
     * @param orderId
     * @param items
     * @return
     */
    Object orderComment(Long orderId, String items);

    CommonResult generateStoreOrder(OrderParam orderParam);

    /**
     * 订单退货申请
     * @param items
     * @return
     */
    Object applyRe(ApplyRefundVo items);


    Object quitGroup(Long id);

    /**
     * autoDeliveryOrder
     *
     * @return
     */
    CommonResult autoDeliveryOrder();

    CommonResult autoCommentOrder();

    CommonResult autoSucessOrder();
}
