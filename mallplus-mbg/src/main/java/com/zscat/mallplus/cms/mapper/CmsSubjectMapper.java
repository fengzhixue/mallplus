package com.zscat.mallplus.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zscat.mallplus.cms.entity.CmsSubject;
import com.zscat.mallplus.vo.timeline.Timeline;
import com.zscat.mallplus.vo.timeline.TimelinePost;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 专题表 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-17
 */
public interface CmsSubjectMapper extends BaseMapper<CmsSubject> {

    int countByToday(Long memberId);

    List<TimelinePost> listTimelinePost(@Param("year") Integer year, @Param("month") Integer month);

    List<Timeline> listTimeline();

    List<Map> articleArchiveList();

    List<CmsSubject> loadArticleByArchive(@Param("createTime") String createTime);

}
