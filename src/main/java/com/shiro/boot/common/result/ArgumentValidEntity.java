package com.shiro.boot.common.result;

import com.baomidou.mybatisplus.annotation.TableId;
import com.shiro.boot.common.annotation.CardValid;
import com.shiro.boot.common.result.validgroup.Creat;
import com.shiro.boot.common.result.validgroup.Update;
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
public class ArgumentValidEntity implements Serializable {

    private static final long serialVersionUID = 2083790742831039058L;

    @NotNull(message = "用户ID不能为空", groups = Update.class)
//    @TableId
    private String userId;

    @NotNull(message = "用户名不能为空")
    private String username;

    @NotNull(message = "密码不能为空")
    private String password;

    @NotNull(message = "电话不能为空", groups = Creat.class)
    @NotEmpty
    private String phone;

    @CardValid(message = "身份证不对")
    @NotEmpty
    private String card;

}
