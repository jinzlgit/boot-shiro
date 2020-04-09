package com.shiro.boot.common.result;

import com.baomidou.mybatisplus.annotation.TableId;
import com.shiro.boot.common.annotation.CardValid;
import com.shiro.boot.common.result.validgroup.Creat;
import com.shiro.boot.common.result.validgroup.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.security.cert.CertificateExpiredException;

/**
 * 用来测试 参数校验 的实体类
 *
 * @author jzl
 * @date 2020/3/30 9:48
 */
@Data
@ApiModel(value = "用户实体类")
public class ArgumentValidEntity implements Serializable {

    private static final long serialVersionUID = 2083790742831039058L;

    @ApiModelProperty("用户唯一标识")
    @NotNull(message = "用户ID不能为空", groups = Update.class)
//    @TableId
    private String userId;

    @ApiModelProperty("用户名")
    @NotNull(message = "用户名不能为空")
    private String username;

    @ApiModelProperty("密码")
    @NotNull(message = "密码不能为空")
    private String password;

    @ApiModelProperty("电话")
    @NotNull(message = "电话不能为空", groups = Creat.class)
    @NotEmpty
    private String phone;

    @ApiModelProperty("身份证")
    @CardValid(message = "身份证不对")
    @NotEmpty
    private String card;

}
