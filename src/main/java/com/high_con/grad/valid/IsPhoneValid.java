package com.high_con.grad.valid;

import com.alibaba.druid.util.StringUtils;
import com.high_con.grad.util.Valid_Util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsPhoneValid implements ConstraintValidator<IsPhone,String>{

    private boolean required = false;
    @Override
    public void initialize(IsPhone constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(required){
        return Valid_Util.isPhoneNum(value);
        }else{
            if(StringUtils.isEmpty(value)){
                return true;
            }else {
                return Valid_Util.isPhoneNum(value);
            }
        }


    }
}
