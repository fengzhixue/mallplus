package com.zscat.mallplus.vo.home;

import lombok.Data;

import java.io.Serializable;

@Data
public class PagesItems implements Serializable {
    private Integer id;
    private String widget_code;
    private String page_code;
    private Integer position_id;
    private Integer sort;

    private Params params;

    public PagesItems() {

    }

    public PagesItems(Integer id, String widget_code, String page_code, Integer position_id, Integer sort, Params params) {
        this.id = id;
        this.widget_code = widget_code;
        this.page_code = page_code;
        this.position_id = position_id;
        this.sort = sort;
        this.params = params;
    }
}
