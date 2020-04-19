package com.zscat.mallplus.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zscat.mallplus.bill.entity.BakGoods;
import com.zscat.mallplus.bill.mapper.BakBrandMapper;
import com.zscat.mallplus.bill.mapper.BakCategoryMapper;
import com.zscat.mallplus.bill.mapper.BakGoodsMapper;
import com.zscat.mallplus.component.OssAliyunUtil;
import com.zscat.mallplus.pms.entity.PmsProduct;
import com.zscat.mallplus.pms.mapper.PmsBrandMapper;
import com.zscat.mallplus.pms.mapper.PmsProductAttributeCategoryMapper;
import com.zscat.mallplus.pms.mapper.PmsProductCategoryMapper;
import com.zscat.mallplus.pms.service.IPmsProductService;
import com.zscat.mallplus.sys.entity.SysStore;
import com.zscat.mallplus.sys.entity.SysUser;
import com.zscat.mallplus.sys.mapper.SysStoreMapper;
import com.zscat.mallplus.sys.mapper.SysUserMapper;
import com.zscat.mallplus.sys.service.ISysStoreService;
import com.zscat.mallplus.utils.MatrixToImageWriter;
import com.zscat.mallplus.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-05-18
 */
@Service
public class SysStoreServiceImpl extends ServiceImpl<SysStoreMapper, SysStore> implements ISysStoreService {

    @Autowired
    OssAliyunUtil aliyunOSSUtil;
    @Resource
    private SysStoreMapper storeMapper;
    @Resource
    private SysUserMapper userMapper;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private BakCategoryMapper bakCategoryMapper;
    @Resource
    private BakGoodsMapper bakGoodsMapper;
    @Resource
    private IPmsProductService productService;
    @Resource
    private BakBrandMapper bakBrandMapper;
    @Resource
    private PmsProductCategoryMapper pmsProductCategoryMapper;
    @Resource
    private PmsProductAttributeCategoryMapper pmsProductAttributeCategoryMapper;
    @Resource
    private PmsBrandMapper pmsBrandMapper;

    @Transactional
    @Override
    public boolean saveStore(SysStore entity) {
       return true;
    }

    void createG(BakGoods gg, Integer storeId) {
        PmsProduct g = new PmsProduct();

        g.setName(gg.getName());
        g.setSubTitle(gg.getBrief());
        g.setDescription(gg.getBrief());
        g.setDetailHtml(gg.getDetail());
        g.setDetailMobileHtml(gg.getDetail());
        g.setDetailTitle(gg.getBrief());
        g.setDetailDesc(gg.getBrief());

        g.setPic(gg.getPicUrl());

        g.setAlbumPics(gg.getPicUrl());
        if (ValidatorUtils.notEmpty(gg.getCounterPrice())) {
            g.setPrice(gg.getCounterPrice());
        }
        if (ValidatorUtils.notEmpty(gg.getRetailPrice())) {
            g.setOriginalPrice(gg.getRetailPrice());
        }

        g.setSort(gg.getSortOrder());
        g.setSale(gg.getCategoryId());
        g.setStock(gg.getId());
        g.setLowStock(0);
        g.setPublishStatus(1);
        g.setGiftPoint(gg.getCategoryId());
        g.setGiftGrowth(gg.getCategoryId());
        g.setPromotionType(0);
        g.setVerifyStatus(1);
        g.setProductSn(gg.getGoodsSn());
        g.setQsType(1);
        g.setNewStatus(1);
        g.setCreateTime(new Date());

        g.setBrandId(gg.getBrandId().longValue());

        g.setProductCategoryId(gg.getCategoryId().longValue());

        g.setProductAttributeCategoryId(gg.getCategoryId().longValue());
        productService.save(g);
    }
}
