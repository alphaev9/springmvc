package com.alpha.springmvc.core.validator;

import com.alpha.springmvc.domain.Address;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class AddressValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Address.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "street", "must specify a street");
        Address address = (Address) o;
        if (address.getNumber() < 0) {
            errors.rejectValue("number", "negative number is illegal");
        }
    }
}
