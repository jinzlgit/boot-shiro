package com.shiro.boot.common.annotation;

import com.shiro.boot.common.constraint.CardValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.shiro.boot.common.annotation.CardValid.List;

/**
 * 自定义约束注解
 *      参数校验
 *          身份证校验
 *
 * 元注解：
 *      Target：指定此注解支持的元素类型，比如：FIELD（属性），METHOD（方法）
 *      Retention(RUNTIME)：指定此类型注解将在运行时通过反射方式可用
 *      Constraint：标记注解的类型为约束，指定注解所使用的的验证器（写验证逻辑的类）
 *      Repeatable(List.class)：指示注解可以在相同的位置重复多次，通常具有不同的配置。List包含注解类型
 *      Documented：用此注解会被包含在使用方的 JavaDoc 中
 *
 * @author jzl
 * @date 2020/3/30 10:49
 */
@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = CardValidator.class)
@Repeatable(List.class)
@Documented
public @interface CardValid {

    String message() default "身份证号码不合法";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    @Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    @interface List {

        CardValid[] value();
    }

}
