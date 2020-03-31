package com.shiro.boot.common.constraint;

import com.shiro.boot.common.annotation.CardValid;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * 验证器
 * 配合注解使用
 * 身份证参数校验 验证器
 *
 * @author jzl
 * @date 2020/3/30 11:36
 */
public class CardValidator implements ConstraintValidator<CardValid, Object> {

    public static final String REGEX_ID_CARD = "(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])";

    @Override
    public void initialize(CardValid constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null){
            return false;
        }
        return CardValidator.isCardValid((String) value);
    }

    public static boolean isCardValid(String card) {
        return Pattern.matches(REGEX_ID_CARD, card);
    }
}
