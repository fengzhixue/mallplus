package com.zscat.mallplus.pms.vo;


import com.zscat.mallplus.pms.entity.*;
import com.zscat.mallplus.sys.entity.SysStore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 创建和修改商品时使用的参数
 * https://github.com/shenzhuan/mallplus on 2018/4/28.
 */
@Data
public class GoodsDetailResult implements Serializable {
    @ApiModelProperty("商品阶梯价格设置")
    private List<PmsProductLadder> productLadderList;
    @ApiModelProperty("商品满减价格设置")
    private List<PmsProductFullReduction> productFullReductionList;
    @ApiModelProperty("商品会员价格设置")
    private List<PmsMemberPrice> memberPriceList;
    @ApiModelProperty("商品的sku库存信息")
    private List<PmsSkuStock> skuStockList;
    @ApiModelProperty("商品参数及自定义规格属性")
    private List<PmsProductAttributeValue> productAttributeValueList;
    private Object productAttributeNameValueList;
    @ApiModelProperty("专题和商品关系")
    private List<CmsSubjectProductRelation> subjectProductRelationList;
    @ApiModelProperty("优选专区和商品的关系")
    private List<CmsPrefrenceAreaProductRelation> prefrenceAreaProductRelationList;

    private List<PmsProductAttributeValue> productCanShuValueList;
    private List<PmsProduct> typeGoodsList;

    private PmsProduct goods;
    private SysStore storeInfo;
}
