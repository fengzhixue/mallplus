package com.zscat.mallplus.vo.home;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Pages implements Serializable {
    private Integer id;
    private String code;
    private String name;
    private String desc;
    private Integer layout;
    private Integer type;

    private List<PagesItems> items;

    public Pages() {
    }

    public Pages(Integer id, String code, String name, String desc, Integer layout, Integer type, List<PagesItems> pagesItemsList) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.desc = desc;
        this.layout = layout;
        this.type = type;
        this.items = pagesItemsList;
    }
}
