package com.alpha.springmvc.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig(classes = AppConfig.class, resourcePath = "/web/")
class ReturnValueControllerTest {
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    @Test
    void test() throws Exception {
        mockMvc.perform(get("/returnValue/nature"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void nature() throws Exception {
        mockMvc.perform(get("/returnValue/nature"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("address"))
                .andExpect(view().name("test"));
    }

    @Test
    void returnModel() throws Exception {
        mockMvc.perform(get("/returnValue/model"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("address"))
                .andExpect(view().name("returnValue/model"));

    }

    @Test
    void returnString() throws Exception {
        mockMvc.perform(get("/returnValue/string"))
                .andExpect(status().isOk())
                .andExpect(view().name("string"));
    }

    @Test
    void returnVoid() throws Exception {
       /* mockMvc.perform(get("/returnValue/test"))
                .andExpect(status().isOk())
                .andExpect(view().name("returnValue/void"))
                .andExpect(forwardedUrl("/returnValue/void.jsp"))
                .andExpect(content().string("hello"))
                .andDo(print());*/


        mockMvc.perform(get("/a.html"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void returnVoidHandcraft() throws Exception {
        mockMvc.perform(get("/returnValue/handcraft"))
                .andExpect(status().isOk())
                .andExpect(content().string("handcraft response"))
                .andDo(print());
    }
}