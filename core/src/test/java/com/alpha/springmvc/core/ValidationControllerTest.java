package com.alpha.springmvc.core;

import com.alpha.springmvc.core.config.AppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringJUnitWebConfig(AppConfig.class)
class ValidationControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    @Test
    void validateAddress() throws Exception {
        LinkedMultiValueMap valueMap = new LinkedMultiValueMap();
        valueMap.add("street", "baoguang");
        valueMap.add("number", "-1");
        mockMvc.perform(get("/validation/address")
                .params(valueMap))
                .andExpect(status().isOk());
    }

    @Test
    void validateBacklog() throws Exception {
        LinkedMultiValueMap valueMap = new LinkedMultiValueMap();
        valueMap.add("title", "test");
        valueMap.add("description", "it's test");
        valueMap.add("dueTime", "2019-01-01");
        valueMap.add("address.street", "baoguang");
        valueMap.add("address.number", "-1");

        mockMvc.perform(get("/validation/backlog")
                .params(valueMap))
                .andExpect(status().isOk());
    }

    @Test
    void validateBacklogByJSR() throws Exception {
        LinkedMultiValueMap valueMap = new LinkedMultiValueMap();
        valueMap.add("title", "test");
        valueMap.add("description", "it");
        valueMap.add("dueTime", "2019-01-01");
        valueMap.add("address.street", "baoguang");
        valueMap.add("address.number", "-1");

        mockMvc.perform(get("/validation/jsr")
                .params(valueMap))
                .andExpect(status().isOk());
    }

    @Test
    void customValidator() throws Exception {
        LinkedMultiValueMap valueMap = new LinkedMultiValueMap();
        valueMap.add("title", "meeting-market");
        valueMap.add("state", "fresh");
        valueMap.add("cooperators[0].name", "alpha");
        valueMap.add("cooperators[0].email", "alpha@sohu");
        mockMvc.perform(get("/validation/custom")
                .params(valueMap))
                .andExpect(status().isOk());
    }


    @Test
    void groupStep1Test() throws Exception {
        mockMvc.perform(get("/validation/step1"))
                .andExpect(status().isOk());
    }

    @Test
    void groupStep2Test() throws Exception {
        mockMvc.perform(get("/validation/step2"))
                .andExpect(status().isOk());
    }
}