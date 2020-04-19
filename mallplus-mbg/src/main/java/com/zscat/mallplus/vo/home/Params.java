package com.zscat.mallplus.vo.home;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Params implements Serializable {
    // 视频 video
    private String autoplay;

    //搜索
    private String keywords;
    private String style; // imgWindow

    // goods
    private String title;
    private Integer limit;

    private String type;
    private String display; // 1 slide 2 list
    private String lookMore;
    private Integer classifyId;

    private Integer brandId;
    private Integer column;

    // blank
    private String backgroundColor;
    private Integer height;

    // imgSlide
    private Integer duration;
    //imgWindow
    private String margin;

    //    articleClassify
    private Integer articleClassifyId;


    private List list;
}
