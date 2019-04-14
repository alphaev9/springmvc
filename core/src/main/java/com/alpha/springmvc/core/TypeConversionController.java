package com.alpha.springmvc.core;

import com.alpha.springmvc.core.conversion.AddressEditor;
import com.alpha.springmvc.core.conversion.AddressFormatter;
import com.alpha.springmvc.domain.Address;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("conversion")
public class TypeConversionController {
    private static final Logger log = LogManager.getLogger(TypeConversionController.class);

    @InitBinder
    public void config(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        dateFormat.setLenient(false);
//        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
//        webDataBinder.registerCustomEditor(Address.class, new AddressEditor());
        webDataBinder.addCustomFormatter(new AddressFormatter());
    }

    /**
     * spring has a number of built-in PropertyEditor implementations to make life easy
     * for example,if conversion is from string to Integer,because existence of CustomNumberEditor,you have nothing to do
     */
    @GetMapping("useBuiltin")
    public void useBuiltInPropertyEditor(@RequestParam Integer age) {
        log.info(age);
    }

    /**
     * in some case,builtin PropertyEditor couldn't be directly used,but need config.
     * for example,when you want convert string to Date,configuring CustomDateEditor is easy way.
     */
    @GetMapping("configBuiltin")
    public void configBuiltInPropertyEditor(@RequestParam Date dueTime) {
        log.info(dueTime);
    }

    /**
     *
     */

    @GetMapping("customEditor")
    public void customPropertyEditor(@RequestParam Address address) {
        log.info(address.getStreet());
        log.info(address.getNumber());
    }


}
