package com.xvpi.stugraman.beans;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "result", description = "后端返回结果")
public class Result {
    private Boolean status;
    private Object result;
    private int total=0;  // 新增字段
}