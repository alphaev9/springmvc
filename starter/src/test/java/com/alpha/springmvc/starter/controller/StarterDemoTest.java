package com.alpha.springmvc.starter.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig(locations = {"classpath:root-config.xml", "classpath:app-config.xml"})
class StarterDemoTest {
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void login() throws Exception {
        mockMvc.perform(get("/demo/login")
                .param("user", "alpha"))
                .andExpect(forwardedUrl("produceMessage"));
    }

    @Test
    void hello() throws Exception {
        mockMvc.perform(get("/demo/produceMessage")
                .param("user", "alpha"))
                .andExpect(status().isOk())
                .andDo(mvcResult -> System.out.println(mvcResult.getModelAndView()));
    }
}