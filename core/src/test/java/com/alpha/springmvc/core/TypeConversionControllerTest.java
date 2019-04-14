package com.alpha.springmvc.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig(classes = AppConfig.class)
class TypeConversionControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void useBuiltInPropertyEditor() throws Exception {
        mockMvc.perform(get("/conversion/useBuiltin")
                .param("age", "40"))
                .andExpect(status().isOk());
    }

    @Test
    void configBuiltInPropertyEditor() throws Exception {
        mockMvc.perform(get("/conversion/configBuiltin")
                .param("dueTime", "2019-01-01"))
                .andExpect(status().isOk());
    }

    @Test
    void customPropertyEditor() throws Exception {
        mockMvc.perform(get("/conversion/customEditor")
                .param("address", "baoguang;44"))
                .andExpect(status().isOk());
    }
}