package com.zscat.mallplus;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExportGoods {
    @Excel(name = "去", width = 10)
    private String username;
    @Excel(name = "去去", width = 10)
    private String img;
    @Excel(name = "去去去", width = 10)
    private String detail;
    @Excel(name = "去去去去", width = 10)
    private String price;
    @Excel(name = "去去去去去", width = 10)
    private String originprice;
    @Excel(name = "去去去去去去", width = 10)
    private String detail1;


}

