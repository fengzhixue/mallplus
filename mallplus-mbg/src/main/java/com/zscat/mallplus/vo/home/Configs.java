package com.zscat.mallplus.vo.home;

import lombok.Data;

import java.io.Serializable;

@Data
public class Configs implements Serializable {
    private String version = "mallplus-1.1.1";
    private Integer about_article_id = 5;
    private Integer cate_style = 3;
    private Integer cate_type = 1;
    private String distribution_agreement = "分销商申请协议";
    private String distribution_notes = "       成为Jshop云商分销商后，可以获取佣金，用户只可被推荐一次，越早推荐越返利越多哦。";
    private String distribution_store = "1";
    private Integer ent_id = 10519;
    private Integer goods_stocks_warn = 5;
    private Integer image_max = 5;
    private Integer invoice_switch = 1;
    private Integer open_distribution = 1;
    private Integer point_switch = 1;
    private String[] recommend_keys = {"圆珠笔", "文件夹", "文件袋"};
    private String share_desc = "";
    private String share_image = "https=//demo.jihainet.com/static/poster/1/1-1b867dc28e4bd37e55eaf327b6722fea.jpg";
    private String share_title = "优质好店邀您共享";
    private String shop_default_image = "http=//demo.jihainet.com/static/uploads/images/d6/60/71/5cde803675475.png";
    private String shop_desc = "管易云";
    private String shop_logo = "http=//demo.jihainet.com/static/uploads/images/68/f6/1b/5cde803088e85.png";
    private String shop_mobile = "13146587722";
    private String shop_name = "mallplus多租户商城";
    private String show_inviter = "";
    private String statistics = "";
    private Integer store_switch = 1;
    private Integer tocash_money_low = 1;
    private Integer tocash_money_rate = 1;
}
