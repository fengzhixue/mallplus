package com.zscat.mallplus.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zscat.mallplus.sys.entity.SysAdminLog;
import com.zscat.mallplus.vo.LogParam;
import com.zscat.mallplus.vo.LogStatisc;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-14
 */
public interface SysAdminLogMapper extends BaseMapper<SysAdminLog> {

    List<LogStatisc> getLogStatisc(LogParam entity);


}
