package com.zscat.mallplus.fenxiao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zscat.mallplus.fenxiao.entity.FenxiaoRecords;

import java.util.List;

/**
 * @author mallplus
 * @date 2019-12-17
 */
public interface FenxiaoRecordsMapper extends BaseMapper<FenxiaoRecords> {

    /**
     * 根据会员和类分组
     *
     * @return
     */
    List<FenxiaoRecords> listRecordsGroupByMemberId();
}
