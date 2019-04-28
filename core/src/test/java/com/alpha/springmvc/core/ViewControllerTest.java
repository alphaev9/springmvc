package com.alpha.springmvc.core;

import com.alpha.springmvc.core.config.AppConfig;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig(AppConfig.class)
class ViewControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void thymeleaf() throws Exception {
        mockMvc.perform(get("/view/thymeleaf"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("thymeleaf test")))
                .andDo(print());
    }

    @Test
    void freemarker() throws Exception {
        mockMvc.perform(get("/view/freemarker"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("freemarker test")))
                .andDo(print());
    }

    @Test
    void excel() throws Exception {
        mockMvc.perform(get("/view/excel"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void json() throws Exception {
        mockMvc.perform(get("/view/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.backlog.title").value("test"))
                .andExpect(jsonPath("$.backlog.address.number").value(44))
                .andExpect(jsonPath("$.backlog.cooperators[0].name").value("alpha"));
    }
}