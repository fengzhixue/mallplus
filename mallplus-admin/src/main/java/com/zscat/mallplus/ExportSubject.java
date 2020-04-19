package com.zscat.mallplus;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExportSubject {
    @Excel(name = "去", width = 10)
    private String title;
    @Excel(name = "去去", width = 10)
    private String pic;
    @Excel(name = "去去去", width = 10)
    private String content;


}

