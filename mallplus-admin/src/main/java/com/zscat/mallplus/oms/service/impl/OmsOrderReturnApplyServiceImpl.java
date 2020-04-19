package com.zscat.mallplus.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.oms.entity.OmsOrderOperateHistory;
import com.zscat.mallplus.oms.entity.OmsOrderReturnApply;
import com.zscat.mallplus.oms.mapper.OmsOrderReturnApplyMapper;
import com.zscat.mallplus.oms.service.IOmsOrderOperateHistoryService;
import com.zscat.mallplus.oms.service.IOmsOrderReturnApplyService;
import com.zscat.mallplus.oms.vo.OmsUpdateStatusParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 订单退货申请 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-04-17
 */
@Service
public class OmsOrderReturnApplyServiceImpl extends ServiceImpl<OmsOrderReturnApplyMapper, OmsOrderReturnApply> implements IOmsOrderReturnApplyService {
    @Autowired
    private OmsOrderReturnApplyMapper returnApplyMapper;
    @Autowired
    private IOmsOrderOperateHistoryService orderOperateHistoryService;
    @Override
    public int updateStatus(Long id, OmsUpdateStatusParam statusParam) {
        Integer status = statusParam.getStatus();
        OmsOrderReturnApply returnApply = new OmsOrderReturnApply();
        OmsOrderOperateHistory history = new OmsOrderOperateHistory();
        if (status.equals(1)) {
            //确认退货
            history.setNote("确认退货");
            returnApply.setId(id);
            returnApply.setStatus(1);
           // returnApply.setReturnAmount(statusParam.getReturnAmount());
            //returnApply.setCompanyAddressId(statusParam.getCompanyAddressId());
            returnApply.setHandleTime(new Date());
            returnApply.setHandleMan(statusParam.getHandleMan());
            returnApply.setHandleNote(statusParam.getHandleNote());
        } else if (status.equals(2)) {
            //完成退货
            history.setNote("完成退货");
            returnApply.setId(id);
            returnApply.setStatus(2);
            returnApply.setReceiveTime(new Date());
            returnApply.setReceiveMan(statusParam.getReceiveMan());
            returnApply.setReceiveNote(statusParam.getReceiveNote());
        } else if (status.equals(3)) {
            //拒绝退货
            history.setNote("拒绝退货");
            returnApply.setId(id);
            returnApply.setStatus(3);
            returnApply.setHandleTime(new Date());
            returnApply.setHandleMan(statusParam.getHandleMan());
            returnApply.setHandleNote(statusParam.getHandleNote());
        } else {
            return 0;
        }

        history.setOrderId(statusParam.getOrderId());
        history.setCreateTime(new Date());
        history.setOperateMan("admin");

        orderOperateHistoryService.save(history);
        return returnApplyMapper.updateById(returnApply);
    }
}
