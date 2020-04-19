package com.zscat.mallplus.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.sys.entity.SysAdminLog;
import com.zscat.mallplus.vo.LogParam;
import com.zscat.mallplus.vo.LogStatisc;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-04-14
 */
public interface ISysAdminLogService extends IService<SysAdminLog> {
    List<LogStatisc> selectPageExt(LogParam entity);
}
