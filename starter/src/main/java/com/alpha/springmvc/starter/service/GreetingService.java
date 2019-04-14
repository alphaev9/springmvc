package com.alpha.springmvc.starter.service;

import org.springframework.stereotype.Service;

@Service
public class GreetingService {
    public String greetingMessage(){
        return "hello,welcome to springmvc world!";
    }
}
