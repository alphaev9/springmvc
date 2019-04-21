package com.alpha.springmvc.core.validator;

import com.alpha.springmvc.domain.Backlog;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Date;

public class BacklogValidator implements Validator {

    private AddressValidator addressValidator;

    public BacklogValidator(AddressValidator addressValidator) {
        this.addressValidator = addressValidator;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Backlog.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Backlog backlog = (Backlog) o;
        ValidationUtils.rejectIfEmpty(errors, "title", "title is empty");
        if (backlog.getDescription().length() > 10) {
            errors.rejectValue("description", "description is too long");
        }
        if (backlog.getDueTime().before(new Date())) {
            errors.rejectValue("dueTime", "backlog expired!");
        }
        errors.pushNestedPath("address");
        ValidationUtils.invokeValidator(addressValidator, backlog.getAddress(), errors);
    }

}
