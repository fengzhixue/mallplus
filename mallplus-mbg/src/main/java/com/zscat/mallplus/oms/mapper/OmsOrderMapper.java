package com.zscat.mallplus.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zscat.mallplus.oms.entity.OmsOrder;
import com.zscat.mallplus.oms.entity.OmsOrderItem;
import com.zscat.mallplus.oms.vo.OmsOrderDeliveryParam;
import com.zscat.mallplus.oms.vo.OmsOrderDetail;
import com.zscat.mallplus.oms.vo.OrderStstic;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-17
 */
public interface OmsOrderMapper extends BaseMapper<OmsOrder> {

    /**
     * 修改 pms_sku_stock表的锁定库存及真实库存
     */
    int updateSkuStock(@Param("itemList") List<OmsOrderItem> orderItemList);

    /**
     * 获取超时订单
     *
     * @param minute 超时时间（分）
     */
    List<OmsOrderDetail> getTimeOutOrders(@Param("minute") Integer minute);

    /**
     * 批量修改订单状态
     */
    int updateOrderStatus(@Param("ids") List<Long> ids, @Param("status") Integer status);

    /**
     * 解除取消订单的库存锁定
     */
    int releaseSkuStockLock(@Param("itemList") List<OmsOrderItem> orderItemList);

    /**
     * 批量发货
     */
    int delivery(@Param("list") List<OmsOrderDeliveryParam> deliveryParamList);

    /**
     * 根据会员分组订单
     *
     * @return
     */
    List<OrderStstic> listOrderGroupByMemberId();

    /**
     * 订单日统计
     *
     * @param date
     * @return
     */
    Map orderDayStatic(String date);

    /**
     * 订单 月统计
     *
     * @param date
     * @return
     */
    Map orderMonthStatic(String date);

    List<OmsOrder> listByDate(@Param("date") String date, @Param("type") Integer type);
}
