package com.zscat.mallplus.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zscat.mallplus.ums.entity.UmsMember;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
public interface UmsMemberMapper extends BaseMapper<UmsMember> {

    List<UmsMember> listByDate(@Param("date") String date, @Param("type") Integer type);
}
