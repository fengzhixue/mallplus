package com.zscat.mallplus.pms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zscat.mallplus.utils.BaseEntity;
import com.zscat.mallplus.utils.ValidatorUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 存储产品参数信息的表
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Data
@TableName("pms_product_attribute_value")
public class PmsProductAttributeValue extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("product_id")
    private Long productId;

    @TableField("product_attribute_id")
    private Long productAttributeId;

    /**
     * 手动添加规格或参数的值，参数单值，规格有多个时以逗号隔开
     */
    private String value;
    private String name;
    // 1 规格 2 参数
    private Integer type;
    @TableField(exist = false)
    private List pics;

    public List getPics() {
        if (ValidatorUtils.notEmpty(value)) {
            this.pics = Arrays.asList(value.split(","));
        }
        return pics;
    }

    public void setPics(List pics) {
        if (ValidatorUtils.notEmpty(value)) {
            this.pics = Arrays.asList(value.split(","));
        }
    }


}
