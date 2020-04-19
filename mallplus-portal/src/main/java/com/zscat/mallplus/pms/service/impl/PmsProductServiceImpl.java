package com.zscat.mallplus.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.cms.service.ICmsPrefrenceAreaProductRelationService;
import com.zscat.mallplus.cms.service.ICmsSubjectProductRelationService;
import com.zscat.mallplus.enums.ConstansValue;
import com.zscat.mallplus.pms.entity.*;
import com.zscat.mallplus.pms.mapper.*;
import com.zscat.mallplus.pms.service.*;
import com.zscat.mallplus.pms.vo.GoodsDetailResult;
import com.zscat.mallplus.pms.vo.PmsProductParam;
import com.zscat.mallplus.sms.entity.SmsHomeBrand;
import com.zscat.mallplus.sms.entity.SmsHomeNewProduct;
import com.zscat.mallplus.sms.entity.SmsHomeRecommendProduct;
import com.zscat.mallplus.sms.entity.SmsPaimaiLog;
import com.zscat.mallplus.sms.mapper.SmsGroupMapper;
import com.zscat.mallplus.sms.mapper.SmsGroupMemberMapper;
import com.zscat.mallplus.sms.mapper.SmsPaimaiLogMapper;
import com.zscat.mallplus.sms.service.ISmsHomeBrandService;
import com.zscat.mallplus.sms.service.ISmsHomeNewProductService;
import com.zscat.mallplus.sms.service.ISmsHomeRecommendProductService;
import com.zscat.mallplus.sys.mapper.SysStoreMapper;
import com.zscat.mallplus.ums.entity.UmsMember;
import com.zscat.mallplus.ums.service.IUmsMemberService;
import com.zscat.mallplus.ums.service.RedisService;
import com.zscat.mallplus.ums.service.impl.RedisUtil;
import com.zscat.mallplus.util.DateUtils;
import com.zscat.mallplus.util.JsonUtils;
import com.zscat.mallplus.utils.CommonResult;
import com.zscat.mallplus.utils.ValidatorUtils;
import com.zscat.mallplus.vo.Rediskey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Slf4j
@Service
public class PmsProductServiceImpl extends ServiceImpl<PmsProductMapper, PmsProduct> implements IPmsProductService {

    @Resource
    private IPmsBrandService brandService;
    @Resource
    private ISmsHomeBrandService homeBrandService;
    @Resource
    private ISmsHomeNewProductService homeNewProductService;
    @Resource
    private ISmsHomeRecommendProductService homeRecommendProductService;
    @Resource
    private PmsProductMapper productMapper;
    @Resource
    private IPmsMemberPriceService memberPriceDao;
    @Resource
    private PmsMemberPriceMapper memberPriceMapper;
    @Resource
    private IPmsProductLadderService productLadderDao;
    @Resource
    private PmsProductLadderMapper productLadderMapper;
    @Resource
    private IPmsProductFullReductionService productFullReductionDao;
    @Resource
    private PmsProductFullReductionMapper productFullReductionMapper;
    @Resource
    private IPmsSkuStockService skuStockDao;
    @Resource
    private PmsSkuStockMapper skuStockMapper;
    @Resource
    private IPmsProductAttributeValueService productAttributeValueDao;
    @Resource
    private PmsProductAttributeValueMapper productAttributeValueMapper;
    @Resource
    private ICmsSubjectProductRelationService subjectProductRelationDao;
    @Resource
    private CmsSubjectProductRelationMapper subjectProductRelationMapper;
    @Resource
    private ICmsPrefrenceAreaProductRelationService prefrenceAreaProductRelationDao;
    @Resource
    private CmsPrefrenceAreaProductRelationMapper prefrenceAreaProductRelationMapper;

    @Resource
    private PmsProductVertifyRecordMapper productVertifyRecordDao;

    @Resource
    private PmsProductVertifyRecordMapper productVertifyRecordMapper;

    @Resource
    private SmsGroupMapper groupMapper;
    @Resource
    private SmsGroupMemberMapper groupMemberMapper;
    @Resource
    private RedisService redisService;
    @Resource
    private SysStoreMapper storeMapper;
    @Resource
    private RedisUtil redisUtil;
    @Autowired
    private IPmsFavoriteService favoriteService;

    @Resource
    private SmsPaimaiLogMapper paimaiLogMapper;
    @Autowired
    private IUmsMemberService memberService;


    @Override
    public PmsProduct getUpdateInfo(Long id) {
        return productMapper.selectById(id);
    }

    @Override
    public Object initGoodsRedis() {
        List<PmsProduct> list = productMapper.selectList(new QueryWrapper<>());
        for (PmsProduct goods : list) {
            PmsProductParam param = new PmsProductParam();
            param.setGoods(goods);

            List<PmsProductLadder> productLadderList = productLadderMapper.selectList(new QueryWrapper<PmsProductLadder>().eq("product_id", goods.getId()));

            List<PmsProductFullReduction> productFullReductionList = productFullReductionMapper.selectList(new QueryWrapper<PmsProductFullReduction>().eq("product_id", goods.getId()));

            List<PmsMemberPrice> memberPriceList = memberPriceMapper.selectList(new QueryWrapper<PmsMemberPrice>().eq("product_id", goods.getId()));

            List<PmsSkuStock> skuStockList = skuStockMapper.selectList(new QueryWrapper<PmsSkuStock>().eq("product_id", goods.getId()));

            List<PmsProductAttributeValue> productAttributeValueList = productAttributeValueMapper.selectList(new QueryWrapper<PmsProductAttributeValue>().eq("product_id", goods.getId()));

            List<CmsSubjectProductRelation> subjectProductRelationList = subjectProductRelationMapper.selectList(new QueryWrapper<CmsSubjectProductRelation>().eq("product_id", goods.getId()));

            List<CmsPrefrenceAreaProductRelation> prefrenceAreaProductRelationList = prefrenceAreaProductRelationMapper.selectList(new QueryWrapper<CmsPrefrenceAreaProductRelation>().eq("product_id", goods.getId()));

            param.setMemberPriceList(memberPriceList);
            param.setPrefrenceAreaProductRelationList(prefrenceAreaProductRelationList);
            param.setProductAttributeValueList(productAttributeValueList);
            param.setProductFullReductionList(productFullReductionList);
            param.setProductLadderList(productLadderList);
            param.setSkuStockList(skuStockList);
            param.setSubjectProductRelationList(subjectProductRelationList);
            redisService.set(String.format(Rediskey.GOODSDETAIL, goods.getId()), JsonUtils.objectToJson(param));
        }
        return 1;
    }

    @Override
    public GoodsDetailResult getGoodsRedisById(Long id) {
        PmsProduct goods = productMapper.selectById(id);

        if (goods==null || goods.getId()<1){
            return null;
        }
        GoodsDetailResult param = new GoodsDetailResult();
        param.setGoods(goods);

      /*  List<PmsProductLadder> productLadderList = productLadderMapper.selectList(new QueryWrapper<PmsProductLadder>().eq("product_id", goods.getId()));

        List<PmsProductFullReduction> productFullReductionList = productFullReductionMapper.selectList(new QueryWrapper<PmsProductFullReduction>().eq("product_id", goods.getId()));

        List<PmsMemberPrice> memberPriceList = memberPriceMapper.selectList(new QueryWrapper<PmsMemberPrice>().eq("product_id", goods.getId()));
  param.setMemberPriceList(memberPriceList);
        param.setProductFullReductionList(productFullReductionList);
        param.setProductLadderList(productLadderList);
*/
        List<PmsSkuStock> skuStockList = skuStockMapper.selectList(new QueryWrapper<PmsSkuStock>().eq("product_id", goods.getId()));
        param.setSkuStockList(skuStockList);
        List<PmsProductAttributeValue> productAttributeValueList = productAttributeValueMapper.selectList(new QueryWrapper<PmsProductAttributeValue>().eq("product_id", goods.getId()).eq("type", 1));
        param.setProductAttributeValueList(productAttributeValueList);

        // List<CmsSubjectProductRelation> subjectProductRelationList = subjectProductRelationMapper.selectList(new QueryWrapper<CmsSubjectProductRelation>().eq("product_id", goods.getId()));

        //   List<CmsPrefrenceAreaProductRelation> prefrenceAreaProductRelationList = prefrenceAreaProductRelationMapper.selectList(new QueryWrapper<CmsPrefrenceAreaProductRelation>().eq("product_id", goods.getId()));

        // List<PmsProductAttributeValue> productCanShuValueList = productAttributeValueMapper.selectList(new QueryWrapper<PmsProductAttributeValue>().eq("product_id", goods.getId()).eq("type", 2));
        // param.setProductCanShuValueList(productCanShuValueList);

        //  param.setPrefrenceAreaProductRelationList(prefrenceAreaProductRelationList);

        //param.setSubjectProductRelationList(subjectProductRelationList);

        //List<PmsProduct> typeGoodsList = productMapper.selectList(new QueryWrapper<PmsProduct>().eq("product_attribute_category_id", goods.getProductAttributeCategoryId()).select(ConstansValue.sampleGoodsList));
        //  param.setTypeGoodsList(typeGoodsList.subList(0, typeGoodsList.size() > 8 ? 8 : typeGoodsList.size()));
        redisService.set(String.format(Rediskey.GOODSDETAIL, goods.getId()), JsonUtils.objectToJson(param));

        return param;
    }

    @Override
    public GoodsDetailResult getGoodsRedisById1(Long id) {
        PmsProduct goods = productMapper.selectById(id);

        GoodsDetailResult param = new GoodsDetailResult();
        param.setGoods(goods);

        List<PmsSkuStock> skuStockList = skuStockMapper.selectList(new QueryWrapper<PmsSkuStock>().eq("product_id", goods.getId()));
        param.setSkuStockList(skuStockList);
        List<PmsProductAttributeValue> productAttributeValueList = productAttributeValueMapper.selectList(new QueryWrapper<PmsProductAttributeValue>().eq("product_id", goods.getId()).eq("type", 1));
        param.setProductAttributeNameValueList(getSpecificationVoList(productAttributeValueList));
        List<PmsProductAttributeValue> productCanShuValueList = productAttributeValueMapper.selectList(new QueryWrapper<PmsProductAttributeValue>().eq("product_id", goods.getId()).eq("type", 2));
        param.setProductCanShuValueList(productCanShuValueList);

        List<PmsProduct> typeGoodsList = productMapper.selectList(new QueryWrapper<PmsProduct>().eq("product_attribute_category_id", goods.getProductAttributeCategoryId()).select(ConstansValue.sampleGoodsList));
        param.setTypeGoodsList(typeGoodsList.subList(0, typeGoodsList.size() > 8 ? 8 : typeGoodsList.size()));
        redisService.set(String.format(Rediskey.GOODSDETAIL1, goods.getId()), JsonUtils.objectToJson(param));

        return param;
    }

    /**
     * [
     * {
     * name: '',
     * valueList: [ {}, {}]
     * },
     * {
     * name: '',
     * valueList: [ {}, {}]
     * }
     * ]
     *
     * @return
     */
    public Object getSpecificationVoList(List<PmsProductAttributeValue> goodsSpecificationList) {
        List<VO> specificationVoList = new ArrayList<>();
        for (PmsProductAttributeValue goodsSpecification : goodsSpecificationList) {
            String specification = goodsSpecification.getName();
            VO goodsSpecificationVo = new VO();
            goodsSpecificationVo.setName(specification);
            List<PmsProductAttributeValue> valueList = new ArrayList<>();
            for (String var : goodsSpecification.getValue().split(",")) {
                PmsProductAttributeValue newSpecification = new PmsProductAttributeValue();
                BeanUtils.copyProperties(goodsSpecification, newSpecification);
                newSpecification.setValue(var);
                valueList.add(newSpecification);
            }
            goodsSpecificationVo.setValueList(valueList);
            specificationVoList.add(goodsSpecificationVo);
        }
        return specificationVoList;
    }

    @Override
    public List<PmsBrand> getRecommendBrandList(int pageNum, int pageSize) {

        List<SmsHomeBrand> brands = homeBrandService.list(new QueryWrapper<>());
        if (brands != null && brands.size() > 0) {
            List<Long> ids = brands.stream()
                    .map(SmsHomeBrand::getBrandId)
                    .collect(Collectors.toList());
            if (ids != null && ids.size() > 0) {
                return (List<PmsBrand>) brandService.listByIds(ids);
            }
        }
        return new ArrayList<>();

    }

    @Override
    public List<PmsProduct> getNewProductList(int pageNum, int pageSize) {

        List<SmsHomeNewProduct> brands = homeNewProductService.list(new QueryWrapper<>());
        if (brands != null && brands.size() > 0) {
            List<Long> ids = brands.stream()
                    .map(SmsHomeNewProduct::getProductId)
                    .collect(Collectors.toList());
            if (ids != null && ids.size() > 0) {
                return productMapper.selectList(new QueryWrapper<PmsProduct>().in("id", ids).select(ConstansValue.sampleGoodsList));
            }
        }
        return new ArrayList<>();
    }

    @Override
    public List<PmsProduct> getHotProductList(int pageNum, int pageSize) {
        List<SmsHomeRecommendProduct> brands = homeRecommendProductService.list(new QueryWrapper<>());
        if (brands != null && brands.size() > 0) {
            List<Long> ids = brands.stream()
                    .map(SmsHomeRecommendProduct::getProductId)
                    .collect(Collectors.toList());
            if (ids != null && ids.size() > 0) {
                return productMapper.selectList(new QueryWrapper<PmsProduct>().in("id", ids).select(ConstansValue.sampleGoodsList));
            }
        }
        return new ArrayList<>();
    }

    @Override
    public Integer countGoodsByToday(Long id) {
        return productMapper.countGoodsByToday(id);
    }

    @Override
    public Map<String, Object> queryPaiMaigoodsDetail(Long id) {
        Map<String, Object> map = new HashMap<>();
        PmsProduct goods = productMapper.selectById(id);
        List<SmsPaimaiLog> paimaiLogList = paimaiLogMapper.selectList(new QueryWrapper<SmsPaimaiLog>().eq("goods_id", id).orderByDesc("create_time"));
        map.put("paimaiLogList", paimaiLogList);
        UmsMember umsMember = memberService.getNewCurrentMember();
        map.put("favorite", false);
        if (umsMember != null && umsMember.getId() != null) {
            PmsFavorite query = new PmsFavorite();
            query.setObjId(goods.getId());
            query.setMemberId(umsMember.getId());
            query.setType(1);
            PmsFavorite findCollection = favoriteService.getOne(new QueryWrapper<>(query));
            if (findCollection != null) {
                map.put("favorite", true);
            }
        }
        //记录浏览量到redis,然后定时更新到数据库
        String key = Rediskey.GOODS_VIEWCOUNT_CODE + id;
        //找到redis中该篇文章的点赞数，如果不存在则向redis中添加一条
        Map<Object, Object> viewCountItem = redisUtil.hGetAll(Rediskey.GOODS_VIEWCOUNT_KEY);
        Integer viewCount = 0;
        if (!viewCountItem.isEmpty()) {
            if (viewCountItem.containsKey(key)) {
                viewCount = Integer.parseInt(viewCountItem.get(key).toString()) + 1;
                redisUtil.hPut(Rediskey.GOODS_VIEWCOUNT_KEY, key, viewCount + "");
            } else {
                redisUtil.hPut(Rediskey.GOODS_VIEWCOUNT_KEY, key, 1 + "");
            }
        } else {
            redisUtil.hPut(Rediskey.GOODS_VIEWCOUNT_KEY, key, 1 + "");
        }
        goods.setTimeSecound(ValidatorUtils.getTimeSecound(goods.getExpireTime()));
        map.put("goods", goods);
        return map;
    }

    @Transactional
    @Override
    public Object updatePaiMai(PmsProduct goods) {
        goods.setExpireTime(DateUtils.strToDate(DateUtils.addMins(goods.getExpireTime(), 5)));
        productMapper.updateById(goods);
        SmsPaimaiLog log = new SmsPaimaiLog();
        log.setCreateTime(new Date());
        log.setGoodsId(goods.getId());
        log.setMemberId(memberService.getNewCurrentMember().getId());
        log.setPrice(goods.getOriginalPrice());
        log.setPic(memberService.getNewCurrentMember().getIcon());
        paimaiLogMapper.insert(log);
        return new CommonResult().success();
    }

    private class VO {
        private String name;
        private List<PmsProductAttributeValue> valueList;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<PmsProductAttributeValue> getValueList() {
            return valueList;
        }

        public void setValueList(List<PmsProductAttributeValue> valueList) {
            this.valueList = valueList;
        }
    }
}
