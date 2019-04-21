package com.alpha.springmvc.core;

import com.alpha.springmvc.core.validator.AddressValidator;
import com.alpha.springmvc.core.validator.BacklogValidator;
import com.alpha.springmvc.domain.Address;
import com.alpha.springmvc.domain.Backlog;
import com.alpha.springmvc.validation.Step1;
import com.alpha.springmvc.validation.Step2;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("validation")
public class ValidationController {
    private static final Logger log = LogManager.getLogger(ValidationController.class);

    @InitBinder
    public void config(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        dateFormat.setLenient(false);
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
//        webDataBinder.addValidators(new BacklogValidator(new AddressValidator()));
    }

    /**
     * in spring,there are tow validation mechanism,java bean validation and self-implementing validator.
     * if using java bean validation,including two specification of jsr-303 and jsr-349,a provider must exist in classpath,because spring don't give implementation.\
     * so,as starter demo,using spring self validator is good choice.
     */
    @RequestMapping("address")
    public void validateAddress(Address address, Errors errors) {
        AddressValidator validator = new AddressValidator();
        validator.validate(address, errors);
        errors.getAllErrors().forEach(e -> log.info(e.getCode()));
    }

    /**
     * nested object is common case,how does the validation logic present?
     * following re-use principle,you should encapsulate validation logic in a single module for every standalone domain object
     * helper class ValidationUtil give you api to invoke a validator in another.
     * in addition,programming style of validator usage can be replaced a more easy way,annotation-driven style.
     */
    @RequestMapping("backlog")
    public void validateBacklog(@Validated Backlog backlog, Errors errors) {
        errors.getAllErrors().forEach(e -> log.info(e.getCode()));
    }

    /**
     * notice @Valid looks be similar with @Validated,but actually it is from java bean validation specification,another validation mechanism mentioned above.
     * jsr-303 and it's update version jsr-349 provide many standard annotation representing various validation logic,which can suffice most of demands.
     * first,using @Valid replacing @Validated,it's just a tiny move.notice jsr-303 implementation provider has not been introduced.so,in natural,now validation still works on self validator mechanism of spring
     */
    @RequestMapping("jsr")
    public void validateBacklogByJSR(@Valid Backlog backlog, BindingResult bindingResult) {
        bindingResult.getAllErrors().forEach(e -> log.info(e.getDefaultMessage()));
    }

    /**
     * following java bean validation specification,you can still customize a validator.
     * for easy validation of using annotation-driven style,usually a annotation representing constraint is also necessary.
     */

    @RequestMapping("custom")
    public void customValidator(@Valid Backlog backlog, BindingResult bindingResult) {
        bindingResult.getAllErrors().forEach(e -> log.info(e.getDefaultMessage()));
    }

    @RequestMapping("step1")
    public void groupStep1Test(@Validated(Step1.class) Backlog backlog, BindingResult bindingResult) {
        bindingResult.getAllErrors().forEach(e -> log.info(e.getDefaultMessage()));
    }

    @RequestMapping("step2")
    public void groupStep2Test(@Validated(Step2.class) Backlog backlog, BindingResult bindingResult) {
        bindingResult.getAllErrors().forEach(e -> log.info(e.getDefaultMessage()));
    }

}
