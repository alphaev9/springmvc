package com.alpha.springmvc.starter.controller;

import com.alpha.springmvc.starter.service.AuthenticationService;
import com.alpha.springmvc.starter.service.GreetingService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("demo")
public class StarterDemo {
    private static final Logger log = LogManager.getLogger(StarterDemo.class);
    @Autowired
    private GreetingService greetingService;
    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping("login")
    public String login() {
        boolean passed = authenticationService.authentication();
        if (passed) {
            log.info("authentication passed!");
            return "forward:produceMessage";
        }
        return "error";
    }


    @RequestMapping(value = "produceMessage")
    public ModelAndView hello(@RequestParam("user") String name) {
        log.info("request mapping is working....");
        String greetingMessage = greetingService.greetingMessage();
        ModelAndView mv = new ModelAndView();
        mv.addObject("user", name);
        mv.addObject("greetingMessage", greetingMessage);
        mv.setViewName("hello");
        return mv;
    }
}
