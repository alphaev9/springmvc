package com.alpha.springmvc.core;

import com.alpha.springmvc.domain.Address;
import com.alpha.springmvc.domain.Backlog;
import com.alpha.springmvc.domain.BacklogState;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@Controller
@RequestMapping("returnValue")
public class ReturnValueController {

    private static final Logger log = LogManager.getLogger(ReturnValueController.class);

    /**
     * whatever the method logic is,after return,there are two things to be done.
     * one is the result of method execution should be collected into a special component which is called model.
     * the other is rendering a view based on model,here view rendering actually means the process how to produce the response.
     */
    @GetMapping("modelAndView")
    public ModelAndView returnModelAndView() {
        ModelAndView mv = new ModelAndView();
        Address address = new Address();
        address.setStreet("gaoguang");
        address.setNumber(44);
        mv.addObject("address", address);
        InternalResourceView view = new InternalResourceView();
        view.setUrl("/test.jsp");
        mv.setView(view);
        return mv;
    }

    /**
     * if method return a view,who is rendered is obvious.
     */

    @GetMapping("view")
    public View returnView() {
        return new RedirectView("/redirect");
    }

    /**
     * unlike the previous case,given return is a model,question comes up,where are you find a view to render?
     * unveiling the mask,you can see there is a special component named RequestToViewNameTranslator serving the question mentioned above.
     * in summary,as long as the handler doesn't give a definite view,whether a view or view name,RequestToViewNameTranslator will serve you.
     * after logic view name is resolved,ViewResolver will interfere,which is in charge of finding or producing real view.
     */
    @GetMapping("model")
    public Model returnModel(Model model) {
        Address address = new Address();
        address.setStreet("gaoguang");
        address.setNumber(44);
        model.addAttribute("address", address);
        return model;
    }

    /**
     * note there is special case,when method return type is String,the return value will be taken as view name,so RequestToViewNameTranslator will be bypassed.
     */
    @GetMapping("string")
    public String returnString() {
        return "viewResolver";
    }

    /**
     * when the handler return void,similar with above case,the logic view name is gonna be resolved to request url.
     */
    @GetMapping("void")
    public void returnVoid() {
    }

    /**
     * in general,the handler of this type fully control the response through method parameter
     */
    @GetMapping("handcraft")
    public void returnVoid(HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.write("handcraft response");
    }


    /**
     * if method return type is others except Model,ModelMap,Map,String,ModelAndView etc,the return value would be added into implicit Model whether using @ModelAttribute or not.
     */
    @GetMapping("objectInModel")
    @ModelAttribute("address")
    public Address returnObjectInModel() {
        return new Address();
    }

    /**
     * in some scenario,directly placing domain object in response body is good idea.
     */
    @GetMapping("objectInResponse")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.FOUND)
    public Backlog returnObjectInResponse() {
        Backlog backlog = new Backlog();
        backlog.setTitle("test");
        backlog.setDescription("it's backlog for test");
        backlog.setState(BacklogState.fresh);
        return backlog;
    }


}
