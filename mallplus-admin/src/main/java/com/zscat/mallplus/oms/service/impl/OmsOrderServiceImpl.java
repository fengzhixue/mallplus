package com.zscat.mallplus.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.enums.OrderStatus;
import com.zscat.mallplus.oms.entity.OmsOrder;
import com.zscat.mallplus.oms.entity.OmsOrderOperateHistory;
import com.zscat.mallplus.oms.mapper.OmsOrderMapper;
import com.zscat.mallplus.oms.mapper.OmsOrderOperateHistoryMapper;
import com.zscat.mallplus.oms.service.IOmsOrderOperateHistoryService;
import com.zscat.mallplus.oms.service.IOmsOrderService;
import com.zscat.mallplus.oms.vo.OmsMoneyInfoParam;
import com.zscat.mallplus.oms.vo.OmsOrderDeliveryParam;
import com.zscat.mallplus.oms.vo.OmsReceiverInfoParam;
import com.zscat.mallplus.pms.entity.PmsProduct;
import com.zscat.mallplus.pms.mapper.PmsProductMapper;
import com.zscat.mallplus.ums.entity.UmsMember;
import com.zscat.mallplus.ums.mapper.UmsMemberMapper;
import com.zscat.mallplus.utils.ValidatorUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-04-17
 */
@Service
public class OmsOrderServiceImpl extends ServiceImpl<OmsOrderMapper, OmsOrder> implements IOmsOrderService {

    @Resource
    private OmsOrderMapper orderMapper;
    @Resource
    private UmsMemberMapper memberMapper;
    @Resource
    private PmsProductMapper productMapper;
    @Resource
    private IOmsOrderOperateHistoryService orderOperateHistoryDao;
    @Resource
    private OmsOrderOperateHistoryMapper orderOperateHistoryMapper;

    @Override
    public int delivery(List<OmsOrderDeliveryParam> deliveryParamList) {
        //批量发货
        int count = orderMapper.delivery(deliveryParamList);
        if (count > 0) {
            //添加操作记录
            List<OmsOrderOperateHistory> operateHistoryList = deliveryParamList.stream()
                    .map(omsOrderDeliveryParam -> {
                        OmsOrderOperateHistory history = new OmsOrderOperateHistory();
                        history.setOrderId(omsOrderDeliveryParam.getOrderId());
                        history.setCreateTime(new Date());
                        history.setOperateMan("后台管理员");
                        history.setPreStatus(OrderStatus.TO_DELIVER.getValue());
                        history.setOrderStatus(OrderStatus.DELIVERED.getValue());
                        history.setNote("完成发货");
                        return history;
                    }).collect(Collectors.toList());
            orderOperateHistoryDao.saveBatch(operateHistoryList);
        }
        return count;
    }

    @Override
    public int singleDelivery(OmsOrderDeliveryParam deliveryParamList) {
        OmsOrder order = new OmsOrder();
        order.setId(deliveryParamList.getOrderId());
        order.setDeliverySn(deliveryParamList.getDeliverySn());
        order.setDeliveryCompany(deliveryParamList.getDeliveryCompany());
        order.setStatus(OrderStatus.DELIVERED.getValue());
        //批量发货
        int count = orderMapper.updateById(order);
        if (count > 0) {

            OmsOrderOperateHistory history = new OmsOrderOperateHistory();
            history.setOrderId(deliveryParamList.getOrderId());
            history.setCreateTime(new Date());
            history.setOperateMan("后台管理员");
            history.setPreStatus(OrderStatus.TO_DELIVER.getValue());
            history.setOrderStatus(OrderStatus.DELIVERED.getValue());
            history.setNote("完成发货");


            orderOperateHistoryDao.save(history);
        }
        return count;
    }

    @Override
    public Map orderDayStatic(String date) {
        Map list = orderMapper.orderDayStatic(date);
        return list;
    }

    @Override
    public Map orderMonthStatic(String date) {
        Map list = orderMapper.orderMonthStatic(date);
        return list;
    }

    @Override
    public Object dayStatic(String date, Integer type) {
        List<OmsOrder> orders = orderMapper.listByDate(date, type);
        List<UmsMember> members = memberMapper.listByDate(date, type);
        List<PmsProduct> products = productMapper.listByDate(date, type);
        int nowOrderCount = 0; // 今日订单
        BigDecimal nowOrderPay = new BigDecimal(0); //今日销售总额
        for (OmsOrder order : orders) {
            if (order.getStatus() < 9) {
                nowOrderCount++;
                nowOrderPay = nowOrderPay.add(order.getPayAmount());
            }
        }
        int mallCount = 0; // 当日
        int femallount = 0; // 当日
        for (UmsMember member : members) {
            if (member.getGender() == null || member.getGender() == 1) {
                mallCount++;
            } else {
                femallount++;
            }
        }
        int onCount = 0;
        int offCount = 0;

        int noStock = 0;

        for (PmsProduct goods : products) {
            if (goods.getPublishStatus() == 1) { // 上架状态：0->下架；1->上架
                onCount++;
            }
            if (goods.getPublishStatus() == 0) { // 上架状态：0->下架；1->上架
                offCount++;
            }
            if (ValidatorUtils.empty(goods.getStock()) || goods.getStock() < 1) { // 上架状态：0->下架；1->上架
                noStock++;
            }
        }
        Map<String, Object> map = new HashMap();
        map.put("nowOrderCount", nowOrderCount);
        map.put("nowOrderPay", nowOrderPay);
        map.put("mallCount", mallCount);
        map.put("femallount", femallount);
        map.put("onCount", onCount);
        map.put("offCount", offCount);
        map.put("noStock", noStock);

        map.put("memberCount", memberMapper.selectCount(new QueryWrapper<>()));
        map.put("goodsCount", productMapper.selectCount(new QueryWrapper<>()));
        map.put("orderCount", orderMapper.selectCount(new QueryWrapper<>()));
        return map;

    }

    @Override
    public int close(List<Long> ids, String note) {
        OmsOrder record = new OmsOrder();
        record.setStatus(4);
        int count = orderMapper.update(record, new QueryWrapper<OmsOrder>().eq("delete_status", 0).in("id", ids));
        List<OmsOrderOperateHistory> historyList = ids.stream().map(orderId -> {
            OmsOrderOperateHistory history = new OmsOrderOperateHistory();
            history.setOrderId(orderId);
            history.setCreateTime(new Date());
            history.setOperateMan("后台管理员");
            history.setOrderStatus(4);
            history.setNote("订单关闭:" + note);
            return history;
        }).collect(Collectors.toList());
        orderOperateHistoryDao.saveBatch(historyList);
        return count;
    }

    @Override
    public int updateReceiverInfo(OmsReceiverInfoParam receiverInfoParam) {
        OmsOrder order = new OmsOrder();
        order.setId(receiverInfoParam.getOrderId());
        order.setReceiverName(receiverInfoParam.getReceiverName());
        order.setReceiverPhone(receiverInfoParam.getReceiverPhone());
        order.setReceiverPostCode(receiverInfoParam.getReceiverPostCode());
        order.setReceiverDetailAddress(receiverInfoParam.getReceiverDetailAddress());
        order.setReceiverProvince(receiverInfoParam.getReceiverProvince());
        order.setReceiverCity(receiverInfoParam.getReceiverCity());
        order.setReceiverRegion(receiverInfoParam.getReceiverRegion());
        order.setModifyTime(new Date());
        int count = orderMapper.updateById(order);
        //插入操作记录
        OmsOrderOperateHistory history = new OmsOrderOperateHistory();
        history.setOrderId(receiverInfoParam.getOrderId());
        history.setCreateTime(new Date());
        history.setOperateMan("后台管理员");
        history.setOrderStatus(receiverInfoParam.getStatus());
        history.setNote("修改收货人信息");
        orderOperateHistoryMapper.insert(history);
        return count;
    }

    @Override
    public int updateMoneyInfo(OmsMoneyInfoParam moneyInfoParam) {
        OmsOrder order = new OmsOrder();
        order.setId(moneyInfoParam.getOrderId());
        order.setFreightAmount(moneyInfoParam.getFreightAmount());
        order.setDiscountAmount(moneyInfoParam.getDiscountAmount());
        order.setModifyTime(new Date());
        int count = orderMapper.updateById(order);
        //插入操作记录
        OmsOrderOperateHistory history = new OmsOrderOperateHistory();
        history.setOrderId(moneyInfoParam.getOrderId());
        history.setCreateTime(new Date());
        history.setOperateMan("后台管理员");
        history.setOrderStatus(moneyInfoParam.getStatus());
        history.setNote("修改费用信息");
        orderOperateHistoryMapper.insert(history);
        return count;
    }

    @Override
    public int updateNote(Long id, String note, Integer status) {
        OmsOrder order = new OmsOrder();
        order.setId(id);
        order.setNote(note);
        order.setModifyTime(new Date());
        int count = orderMapper.updateById(order);
        OmsOrderOperateHistory history = new OmsOrderOperateHistory();
        history.setOrderId(id);
        history.setCreateTime(new Date());
        history.setOperateMan("后台管理员");
        history.setOrderStatus(status);
        history.setNote("修改备注信息：" + note);
        orderOperateHistoryMapper.insert(history);
        return count;
    }
}
