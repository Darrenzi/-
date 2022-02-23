package com.cosmetics.common.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;


@Data
public class PageRequest {
    /**
     * 当前页码
     */
    @Min(1)
    @ApiModelProperty(name = "num", value = "当前页码", required = true, example="1")
    private Integer num=1;
    /**
     * 每页数量
     */
    @ApiModelProperty(name = "size", value = "每页条数", required = true, example="10")
    private Integer size=10;

}
