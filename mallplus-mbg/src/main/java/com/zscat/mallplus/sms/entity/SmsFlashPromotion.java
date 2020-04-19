package com.zscat.mallplus.sms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zscat.mallplus.utils.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 限时购表
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Data
@TableName("sms_flash_promotion")
public class SmsFlashPromotion extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String title;

    /**
     * 开始日期
     */
    @TableField("start_date")
    private Date startDate;

    /**
     * 结束日期
     */
    @TableField("end_date")
    private Date endDate;

    /**
     * 上下线状态
     */
    private Integer status;

    /**
     * 秒杀时间段名称
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 是否是首页秒杀活动展示 0：不是，1是
     */
    @TableField("isIndex")
    private Integer isIndex;

    @Override
    public String toString() {
        return "SmsFlashPromotion{" +
                ", id=" + id +
                ", title=" + title +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                ", createTime=" + createTime +
                ",isIndex=" + isIndex +
                "}";
    }
}
