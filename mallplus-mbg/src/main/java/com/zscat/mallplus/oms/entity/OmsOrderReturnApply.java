package com.zscat.mallplus.oms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zscat.mallplus.utils.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 订单退货申请
 * </p>
 *
 * @author zscat
 * @since 2019-04-17
 */
@Setter
@Getter
@TableName("oms_order_return_apply")
public class OmsOrderReturnApply extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单id
     */
    @TableField("order_id")
    private Long orderId;

    /**
     * 收货地址表id
     */
    @TableField("company_address_id")
    private Long companyAddressId;

    /**
     * 退货商品id
     */
    @TableField("product_id")
    private Long productId;

    /**
     * 订单编号
     */
    @TableField("order_sn")
    private String orderSn;

    /**
     * 申请时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 会员用户名
     */
    @TableField("member_username")
    private String memberUsername;

    /**
     * 退款金额
     */
    @TableField("return_amount")
    private BigDecimal returnAmount;

    /**
     * 退货人姓名
     */
    @TableField("return_name")
    private String returnName;

    /**
     * 退货人电话
     */
    @TableField("return_phone")
    private String returnPhone;

    /**
     * 申请状态：0->待处理；1->退货中；2->已完成；3->已拒绝
     */
    private Integer status;

    /**
     * 处理时间
     */
    @TableField("handle_time")
    private Date handleTime;

    /**
     * 商品图片
     */
    @TableField("product_pic")
    private String productPic;

    /**
     * 商品名称
     */
    @TableField("product_name")
    private String productName;

    /**
     * 商品品牌
     */
    @TableField("product_brand")
    private String productBrand;

    /**
     * 商品销售属性：颜色：红色；尺码：xl;
     */
    @TableField("product_attr")
    private String productAttr;

    /**
     * 退货数量
     */
    @TableField("product_count")
    private Integer productCount;

    /**
     * 商品单价
     */
    @TableField("product_price")
    private BigDecimal productPrice;

    /**
     * 商品实际支付单价
     */
    @TableField("product_real_price")
    private BigDecimal productRealPrice;

    /**
     * 原因
     */
    private String reason;

    /**
     * 描述
     */
    private String description;

    /**
     * 凭证图片，以逗号隔开
     */
    @TableField("proof_pics")
    private String proofPics;

    /**
     * 处理备注
     */
    @TableField("handle_note")
    private String handleNote;

    /**
     * 处理人员
     */
    @TableField("handle_man")
    private String handleMan;

    /**
     * 收货人
     */
    @TableField("receive_man")
    private String receiveMan;

    /**
     * 收货时间
     */
    @TableField("receive_time")
    private Date receiveTime;

    /**
     * 收货备注
     */
    @TableField("receive_note")
    private String receiveNote;

    //0换货 1退钱 2退货3 退钱退货
    private Integer type;

    @TableField(exist = false)
    private List<OmsOrderItem> orderItemList;
}
