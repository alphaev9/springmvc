package com.alpha.springmvc.validation;

import com.alpha.springmvc.domain.Address;
import com.alpha.springmvc.domain.Backlog;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MeetingValidator implements ConstraintValidator<MeetingBacklogConstraint, Backlog> {
    @Override
    public void initialize(MeetingBacklogConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(Backlog backlog, ConstraintValidatorContext constraintValidatorContext) {
        String title = backlog.getTitle();
        if (title.startsWith("meeting")) {
            Address address = backlog.getAddress();
            return address != null;
        }
        return true;
    }
}
