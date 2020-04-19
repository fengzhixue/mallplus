package com.zscat.mallplus.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.exception.ApiMallPlusException;
import com.zscat.mallplus.oms.entity.OmsCartItem;
import com.zscat.mallplus.oms.mapper.OmsCartItemMapper;
import com.zscat.mallplus.oms.service.IOmsCartItemService;
import com.zscat.mallplus.oms.vo.CartProduct;
import com.zscat.mallplus.oms.vo.CartPromotionItem;
import com.zscat.mallplus.pms.entity.PmsProduct;
import com.zscat.mallplus.pms.entity.PmsProductFullReduction;
import com.zscat.mallplus.pms.entity.PmsProductLadder;
import com.zscat.mallplus.pms.entity.PmsSkuStock;
import com.zscat.mallplus.pms.mapper.PmsProductMapper;
import com.zscat.mallplus.pms.service.IPmsSkuStockService;
import com.zscat.mallplus.pms.vo.PromotionProduct;
import com.zscat.mallplus.ums.entity.UmsMember;
import com.zscat.mallplus.ums.service.IUmsMemberService;
import com.zscat.mallplus.utils.ValidatorUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 购物车表 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-04-17
 */
@Service
public class OmsCartItemServiceImpl extends ServiceImpl<OmsCartItemMapper, OmsCartItem> implements IOmsCartItemService {
    @Resource
    private OmsCartItemMapper cartItemMapper;
    @Resource
    private IUmsMemberService memberService;
    @Resource
    private PmsProductMapper pmsProductMapper;
    @Autowired
    private IPmsSkuStockService pmsSkuStockService;

    @Override
    public OmsCartItem add(OmsCartItem cartItem) {
        UmsMember currentMember = memberService.getNewCurrentMember();
        cartItem.setMemberId(currentMember.getId());
        cartItem.setMemberNickname(currentMember.getNickname());
        cartItem.setDeleteStatus(0);
        PmsProduct pmsProduct = pmsProductMapper.selectById(cartItem.getProductId());
        if (org.apache.commons.lang.StringUtils.isBlank(cartItem.getProductPic())) {
            cartItem.setProductPic(pmsProduct.getPic());
        }
        cartItem.setProductBrand(pmsProduct.getBrandName());
        cartItem.setProductName(pmsProduct.getName());
        cartItem.setProductSn(pmsProduct.getProductSn());
        cartItem.setProductSubTitle(pmsProduct.getSubTitle());
        cartItem.setProductCategoryId(pmsProduct.getProductCategoryId());
        OmsCartItem existCartItem = getCartItem(cartItem);
        if (existCartItem == null) {
            cartItem.setCreateDate(new Date());
            cartItemMapper.insert(cartItem);
        } else {
            cartItem.setModifyDate(new Date());
            existCartItem.setQuantity(existCartItem.getQuantity() + cartItem.getQuantity());
            cartItemMapper.updateById(existCartItem);
            return existCartItem;
        }
        return cartItem;
    }

    /**
     * 根据会员id,商品id和规格获取购物车中商品
     */
    private OmsCartItem getCartItem(OmsCartItem cartItem) {
        OmsCartItem example = new OmsCartItem();
        example.setProductId(cartItem.getProductId());
        example.setDeleteStatus(0);

        if (!StringUtils.isEmpty(cartItem.getSp1())) {
            example.setSp1(cartItem.getSp1());
        }
        if (!StringUtils.isEmpty(cartItem.getSp2())) {
            example.setSp2(cartItem.getSp2());
        }
        if (!StringUtils.isEmpty(cartItem.getSp3())) {
            example.setSp3(cartItem.getSp3());
        }
        List<OmsCartItem> cartItemList = cartItemMapper.selectList(new QueryWrapper<>(example));
        if (!CollectionUtils.isEmpty(cartItemList)) {
            return cartItemList.get(0);
        }
        return null;
    }

    @Override
    public List<OmsCartItem> list(Long memberId, List<Long> ids) {

        OmsCartItem example = new OmsCartItem();
        example.setMemberId(memberId);
        example.setDeleteStatus(0);
        // example.
        if (ids != null && ids.size() > 0) {
            return cartItemMapper.selectList(new QueryWrapper<>(example).in("id", ids));
        }
        return cartItemMapper.selectList(new QueryWrapper<>(example));
    }

    @Override
    public OmsCartItem selectById(Long id) {
        return cartItemMapper.selectById(id);
    }

    @Override
    public List<OmsCartItem> listPromotion(Long memberId, List<Long> ids) {
        List<OmsCartItem> cartItemList = list(memberId, ids);
        List<CartPromotionItem> cartPromotionItemList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(cartItemList)) {
            //   cartPromotionItemList = this.calcCartPromotion(cartItemList);
        }
        return cartItemList;
    }

    @Override
    public int updateQuantity(Long id, Long memberId, Integer quantity) {
        OmsCartItem cartItem = new OmsCartItem();
        cartItem.setQuantity(quantity);
        cartItem.setId(id);

        return cartItemMapper.updateById(cartItem);
    }

    @Override
    public int delete(Long memberId, List<Long> ids) {
        return cartItemMapper.deleteBatchIds(ids);
    }

    @Override
    public CartProduct getCartProduct(Long productId) {
        return pmsProductMapper.getCartProduct(productId);
    }

    @Override
    public int updateAttr(OmsCartItem cartItem) {
        //删除原购物车信息
        OmsCartItem updateCart = new OmsCartItem();
        updateCart.setId(cartItem.getId());
        updateCart.setModifyDate(new Date());
        updateCart.setDeleteStatus(1);
        cartItemMapper.updateById(updateCart);
        cartItem.setId(null);
        add(cartItem);
        return 1;
    }

    @Override
    public int clear(Long memberId) {
        return cartItemMapper.delete(new QueryWrapper<OmsCartItem>().eq("member_id", memberId));
    }

    @Override
    public OmsCartItem addCart(OmsCartItem cartItem) {
        UmsMember currentMember = memberService.getNewCurrentMember();
        cartItem.setMemberId(currentMember.getId());
        cartItem.setMemberNickname(currentMember.getNickname());
        cartItem.setDeleteStatus(0);
        PmsProduct pmsProduct = pmsProductMapper.selectById(cartItem.getProductId());
        if (org.apache.commons.lang.StringUtils.isBlank(cartItem.getProductPic())) {
            cartItem.setProductPic(pmsProduct.getPic());
        }
        cartItem.setProductBrand(pmsProduct.getBrandName());
        cartItem.setProductName(pmsProduct.getName());
        cartItem.setProductSn(pmsProduct.getProductSn());
        cartItem.setProductSubTitle(pmsProduct.getSubTitle());
        cartItem.setProductCategoryId(pmsProduct.getProductCategoryId());
        OmsCartItem existCartItem = getCartItem(cartItem);
        if (existCartItem == null) {
            cartItem.setCreateDate(new Date());
            cartItemMapper.insert(cartItem);
        } else {
            cartItem.setModifyDate(new Date());
            existCartItem.setQuantity(existCartItem.getQuantity() + cartItem.getQuantity());
            cartItemMapper.updateById(existCartItem);
            return existCartItem;
        }
        return cartItem;
    }

    @Override
    public List<OmsCartItem> calcCartPromotion(List<OmsCartItem> cartItemList) {
        if (cartItemList == null && cartItemList.size() < 1) {
            throw new ApiMallPlusException("订单已提交");
        }


        return cartItemList;
    }

    /**
     * 查询所有商品的优惠相关信息
     */
    private List<PromotionProduct> getPromotionProductList(List<OmsCartItem> cartItemList) {
        List<Long> productIdList = new ArrayList<>();
        for (OmsCartItem cartItem : cartItemList) {
            productIdList.add(cartItem.getProductId());
        }
        return pmsProductMapper.getPromotionProductList(productIdList);
    }

    /**
     * 以spu为单位对购物车中商品进行分组
     */
    private Map<Long, List<OmsCartItem>> groupCartItemBySpu(List<OmsCartItem> cartItemList) {
        Map<Long, List<OmsCartItem>> productCartMap = new TreeMap<>();
        for (OmsCartItem cartItem : cartItemList) {
            List<OmsCartItem> productCartItemList = productCartMap.get(cartItem.getProductId());
            if (productCartItemList == null) {
                productCartItemList = new ArrayList<>();
                productCartItemList.add(cartItem);
                productCartMap.put(cartItem.getProductId(), productCartItemList);
            } else {
                productCartItemList.add(cartItem);
            }
        }
        return productCartMap;
    }

    /**
     * 获取满减促销消息
     */
    private String getFullReductionPromotionMessage(PmsProductFullReduction fullReduction) {
        StringBuilder sb = new StringBuilder();
        sb.append("满减优惠：");
        sb.append("满");
        sb.append(fullReduction.getFullPrice());
        sb.append("元，");
        sb.append("减");
        sb.append(fullReduction.getReducePrice());
        sb.append("元");
        return sb.toString();
    }

    /**
     * 对没满足优惠条件的商品进行处理
     */
    private void handleNoReduce(List<CartPromotionItem> cartPromotionItemList, List<OmsCartItem> itemList, PromotionProduct promotionProduct) {
        for (OmsCartItem item : itemList) {
            CartPromotionItem cartPromotionItem = new CartPromotionItem();
            BeanUtils.copyProperties(item, cartPromotionItem);
            cartPromotionItem.setPromotionMessage("无优惠");
            cartPromotionItem.setReduceAmount(new BigDecimal(0));
            PmsSkuStock skuStock = getOriginalPrice(promotionProduct, item.getProductSkuId());
            if (ValidatorUtils.notEmpty(skuStock)) {
                cartPromotionItem.setRealStock(skuStock.getStock() - skuStock.getLockStock());
            } else {
                PmsProduct pmsProduct = pmsProductMapper.selectById(item.getProductId());
                cartPromotionItem.setRealStock(pmsProduct.getStock());
            }
            cartPromotionItem.setIntegration(promotionProduct.getGiftPoint());
            cartPromotionItem.setGrowth(promotionProduct.getGiftGrowth());
            cartPromotionItemList.add(cartPromotionItem);
        }
    }

    private PmsProductFullReduction getProductFullReduction(BigDecimal totalAmount, List<PmsProductFullReduction> fullReductionList) {
        //按条件从高到低排序
        fullReductionList.sort(new Comparator<PmsProductFullReduction>() {
            @Override
            public int compare(PmsProductFullReduction o1, PmsProductFullReduction o2) {
                return o2.getFullPrice().subtract(o1.getFullPrice()).intValue();
            }
        });
        for (PmsProductFullReduction fullReduction : fullReductionList) {
            if (totalAmount.subtract(fullReduction.getFullPrice()).intValue() >= 0) {
                return fullReduction;
            }
        }
        return null;
    }

    /**
     * 获取打折优惠的促销信息
     */
    private String getLadderPromotionMessage(PmsProductLadder ladder) {
        StringBuilder sb = new StringBuilder();
        sb.append("打折优惠：");
        sb.append("满");
        sb.append(ladder.getCount());
        sb.append("件，");
        sb.append("打");
        sb.append(ladder.getDiscount().multiply(new BigDecimal(10)));
        sb.append("折");
        return sb.toString();
    }

    /**
     * 根据购买商品数量获取满足条件的打折优惠策略
     */
    private PmsProductLadder getProductLadder(int count, List<PmsProductLadder> productLadderList) {
        //按数量从大到小排序
        productLadderList.sort(new Comparator<PmsProductLadder>() {
            @Override
            public int compare(PmsProductLadder o1, PmsProductLadder o2) {
                return o2.getCount() - o1.getCount();
            }
        });
        for (PmsProductLadder productLadder : productLadderList) {
            if (count >= productLadder.getCount()) {
                return productLadder;
            }
        }
        return null;
    }

    /**
     * 获取购物车中指定商品的数量
     */
    private int getCartItemCount(List<OmsCartItem> itemList) {
        int count = 0;
        for (OmsCartItem item : itemList) {
            count += item.getQuantity();
        }
        return count;
    }

    /**
     * 获取购物车中指定商品的总价
     */
    private BigDecimal getCartItemAmount(List<OmsCartItem> itemList, List<PromotionProduct> promotionProductList) {
        BigDecimal amount = new BigDecimal(0);
        for (OmsCartItem item : itemList) {
            //计算出商品原价
            PromotionProduct promotionProduct = getPromotionProductById(item.getProductId(), promotionProductList);
            PmsSkuStock skuStock = getOriginalPrice(promotionProduct, item.getProductSkuId());
            amount = amount.add(skuStock.getPrice().multiply(new BigDecimal(item.getQuantity())));
        }
        return amount;
    }

    /**
     * 获取商品的原价
     */
    private PmsSkuStock getOriginalPrice(PromotionProduct promotionProduct, Long productSkuId) {
        for (PmsSkuStock skuStock : promotionProduct.getSkuStockList()) {
            if (productSkuId.equals(skuStock.getId())) {
                return skuStock;
            }
        }
        return null;
    }

    /**
     * 根据商品id获取商品的促销信息
     */
    private PromotionProduct getPromotionProductById(Long productId, List<PromotionProduct> promotionProductList) {
        for (PromotionProduct promotionProduct : promotionProductList) {
            if (productId.equals(promotionProduct.getId())) {
                return promotionProduct;
            }
        }
        return null;
    }

    @Override
    public Integer countCart(Long id) {
        return cartItemMapper.countCart(id);
    }

    @Override
    public Map<String, List<OmsCartItem>> listStoreCart(Long id) {
        List<OmsCartItem> list = cartItemMapper.selectList(new QueryWrapper<OmsCartItem>().eq("member_id", id));
        for (OmsCartItem item : list) {
            if (ValidatorUtils.notEmpty(item.getProductSkuId())) {
                item.setSkuStock(pmsSkuStockService.getById(item.getProductSkuId()));
            } else {
                item.setProduct(pmsProductMapper.selectById(item.getProductId()));
            }
        }
        Map<String, List<OmsCartItem>> map = list.stream().collect(Collectors.groupingBy(OmsCartItem::getStoreName));

        /*List<StoreCart> storeCartList = new ArrayList<>();
        for (Map.Entry<String, List<OmsCartItem>> entry : map.entrySet()) {
            StoreCart storeCart = new StoreCart();
            storeCart.setStoreName(entry.getKey());
            storeCart.setList(entry.getValue());
            storeCartList.add(storeCart);
        }*/

        return map;

    }
}
