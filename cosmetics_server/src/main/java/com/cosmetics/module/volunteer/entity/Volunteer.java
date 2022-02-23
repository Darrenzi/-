package com.cosmetics.module.volunteer.entity;

import java.time.LocalDateTime;
import java.util.Date;

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
 * @author Miranda
 * @since 2022-02-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="Volunteer对象", description="")
public class Volunteer extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "所属的方案id", example = "1")
    private Integer solutionId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别: 1:男, 0:女")
    private String sex;

    @ApiModelProperty(value = "年龄", example = "1")
    private Integer age;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "肤质")
    private String skin;

    @ApiModelProperty(value = "是否敏感肌")
    private String sensibility;

    @ApiModelProperty(value = "是否长斑")
    private String spot;

    @ApiModelProperty(value = "是否痤疮")
    private String acne;

    @ApiModelProperty(value = "其他说明")
    private String other;

    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty(value = "更新时间")
    private Date updateAt;


}
