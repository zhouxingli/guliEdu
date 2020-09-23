package com.zxl.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DemoData {


    @ExcelProperty("学生编号")
    private Integer sno;
    @ExcelProperty("学生名称")
    private String sname;
}
