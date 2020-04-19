package com.zscat.mallplus.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zscat.mallplus.pms.entity.PmsBrand;
import com.zscat.mallplus.pms.entity.PmsProduct;
import com.zscat.mallplus.pms.vo.GoodsDetailResult;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
public interface IPmsProductService extends IService<PmsProduct> {


    /**
     * 查询商品详情
     *
     * @param id
     * @return
     */
    PmsProduct getUpdateInfo(Long id);

    /**
     * 初始化商品到redis
     *
     * @return
     */
    Object initGoodsRedis();

    /**
     * 获取商品详情 优先取redis
     *
     * @param id
     * @return
     */
    GoodsDetailResult getGoodsRedisById(Long id);

    GoodsDetailResult getGoodsRedisById1(Long id);

    /**
     * 获取推荐品牌
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<PmsBrand> getRecommendBrandList(int pageNum, int pageSize);

    /**
     * 获取最新商品
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<PmsProduct> getNewProductList(int pageNum, int pageSize);

    /**
     * 获取最热商品列表 按销量倒序
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<PmsProduct> getHotProductList(int pageNum, int pageSize);

    /**
     * 今日添加的商品
     *
     * @param id
     * @return
     */
    Integer countGoodsByToday(Long id);

    /**
     * 拍卖商品详情
     *
     * @param id
     * @return
     */
    Map<String, Object> queryPaiMaigoodsDetail(Long id);

    /**
     * 参与竞价
     *
     * @param goods
     * @return
     */
    Object updatePaiMai(PmsProduct goods);
}
