package com.cosmetics.module.user.entity;

import java.util.Date;

import com.cosmetics.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @since 2022-02-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="User对象", description="")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "用户级别, 0为管理员", example = "0")
    private Integer role;

    @ApiModelProperty(value = "性别: 1:男, 0:女", example = "0")
    private Integer gender;

    @ApiModelProperty(value = "网络名")
    private String nickname;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "身高", example = "180")
    private Integer height;

    @ApiModelProperty(value = "体重", example = "120")
    private Integer weight;

    @ApiModelProperty(value = "出生日期")
    private Date birthday;

    @ApiModelProperty(value = "创建时间")
    private Date createAt;

    @ApiModelProperty(value = "更新时间")
    private Date updateAt;


}
