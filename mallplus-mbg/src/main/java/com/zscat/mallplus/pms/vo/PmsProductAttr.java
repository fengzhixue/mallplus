package com.zscat.mallplus.pms.vo;

import lombok.Data;

@Data
public class PmsProductAttr {
    private String name;
    private int select_type;
    private int input_type;
    private String input_list;
    private int sort;
    private int fiilter_type;
    private int search_type;
    private int related_status;
    private int hand_add_status;
    private int type;
}
