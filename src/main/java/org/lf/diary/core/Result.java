package org.lf.diary.core;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 统一API响应结果封装
 */
@ApiModel(value = "Result", description = "API响应结果")
public class Result {

    @ApiModelProperty(value = "状态码")
    private int code;

    @ApiModelProperty(value = "响应结果描述")
    private String message;

    @ApiModelProperty(value = "响应数据")
    private Object data;

    public Result setCode(ResultCode resultCode) {
        this.code = resultCode.getCode();
        return this;
    }

    public int getCode() {
        return code;
    }

    public Result setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
