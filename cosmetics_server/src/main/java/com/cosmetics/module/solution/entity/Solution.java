package com.cosmetics.module.solution.entity;

import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.cosmetics.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @since 2022-02-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Solution对象", description = "")
public class Solution extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "所属的测试需求id", example = "1")
    @TableField("test_Id")
    private Integer testId;

    @ApiModelProperty(value = "失败原因")
    @TableField("fail_reason")
    private String failReason;

    @ApiModelProperty(value = "测试方案内容")
    private String content;

    @ApiModelProperty(value = "测试报告内容")
    private String report;

    @ApiModelProperty(value = "状态", example = "1")
    private Integer status;

    @ApiModelProperty(value = "附件路径")
    private String accessory;

    @ApiModelProperty(value = "现场图片路径")
    private String photo;

    @TableField(exist = false)
    private Integer researcherId;

    @TableField(exist = false)
    private String researcher;

    @TableField(exist = false)
    private String testor;

    @TableField(exist = false)
    private Integer testorId;

    @TableField(exist = false)
    private String number;

    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty(value = "更新时间")
    private Date updateAt;



}
