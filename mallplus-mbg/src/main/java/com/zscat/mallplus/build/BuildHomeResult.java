package com.zscat.mallplus.build;


import com.zscat.mallplus.build.entity.BuildAdv;
import com.zscat.mallplus.build.entity.BuildNotice;
import com.zscat.mallplus.build.entity.BuildingCommunity;
import com.zscat.mallplus.build.entity.BuildingFloor;
import com.zscat.mallplus.oms.vo.ActivityVo;
import com.zscat.mallplus.pms.entity.PmsSmallNaviconCategory;
import com.zscat.mallplus.sys.entity.SysStore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 首页内容返回信息封装
 * https://github.com/shenzhuan/mallplus on 2019/1/28.
 */
@Getter
@Setter
public class BuildHomeResult {
    List<PmsSmallNaviconCategory> navList;
    List<ActivityVo> activityList;
    //轮播广告
    List<BuildAdv> advertiseList;
    BuildingCommunity community;
    List<BuildingFloor> floorList;
    List<BuildingCommunity> communityList;
    //推荐品牌
    private List<SysStore> storeList;
    //推荐专题
    private List<BuildNotice> subjectList;

}
