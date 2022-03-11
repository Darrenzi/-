package com.cosmetics.module.test.entity;

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
@ApiModel(value="Test对象", description="")
public class Test extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "产品编号")
    private String number;

    @ApiModelProperty(value = "产品名称")
    private String name;

    @ApiModelProperty(value = "适合的年龄")
    private String age;

    @ApiModelProperty(value = "产品功效")
    private String effect;

    @ApiModelProperty(value = "状态", example = "1")
    private Integer status;

    @ApiModelProperty(value = "研发员id", example = "1")
    private Integer researcherId;

    @TableField(exist = false)
    private String researcher;

    @TableField(exist = false)
    private String testor;

    @ApiModelProperty(value = "测试员id", example = "1")
    private Integer testorId;

    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty(value = "更新时间")
    private Date updateAt;


}
