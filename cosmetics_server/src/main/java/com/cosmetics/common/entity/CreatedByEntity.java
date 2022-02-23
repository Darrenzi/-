package com.cosmetics.common.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 包含创建用户字段的基类
 */
@Data
public class CreatedByEntity extends BaseEntity{
    /**
     * 创建人的信息
     */
    @NotNull
    @Min(1)
    @ApiModelProperty(notes = "用户添加数据时，必须填写用户的id", example = "1")
    protected Integer createBy;
}
