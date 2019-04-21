package com.alpha.springmvc.core;

import com.alpha.springmvc.domain.Address;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("returnValue")
public class ReturnValueController {
    /**
     *
     */
    @GetMapping("nature")
    public void test() {

    }

    public ModelAndView nature() {
        ModelAndView mv = new ModelAndView();
        Address address = new Address();
        address.setStreet("gaoguang");
        address.setNumber(44);
        mv.addObject("address", address);
        mv.setViewName("test");
        return mv;
    }

    @GetMapping("model")
    public Model returnModel() {
        ExtendedModelMap modelMap = new ExtendedModelMap();
        Address address = new Address();
        address.setStreet("gaoguang");
        address.setNumber(44);
        modelMap.addAttribute("address", address);
        return modelMap;
    }

    @GetMapping("string")
    public String returnString() {
        return "string";
    }

    /**
     * when the handler return void,similar with above case,the logic view name is gonna be resolved to request url.
     */
    @GetMapping("test")
    public String returnVoid() {
        return "returnValue/void";
    }

    /**
     * in general,the handler of this type fully control the response through method parameter
     */
    @GetMapping("handcraft")
    public void returnVoid(HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.write("handcraft response");
    }

}
