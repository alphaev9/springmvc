package com.alpha.springmvc.core;

import com.alpha.springmvc.domain.Backlog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig(classes = AppConfig.class)
class RequestMappingControllerTest {
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void getBacklogById() throws Exception {
        mockMvc.perform(get("/mapping/get?id=2"))
                .andExpect(status().isOk());
    }


    @Test
    void getBacklogWithSingleAsterisk() throws Exception {
        mockMvc.perform(get("/mapping/get/id"))
                .andExpect(status().isOk());
    }

    @Test
    void getBacklogWithDoubleAsterisk() throws Exception {
        mockMvc.perform(get("/mapping/get/id/2"))
                .andExpect(status().isOk());
    }

    @Test
    void getBacklogWithPathVariable() throws Exception {
        mockMvc.perform(get("/mapping/get/backlog/2"))
                .andExpect(status().isOk());
    }

    @Test
    void addBacklog() throws Exception {
        mockMvc.perform(post("/mapping/add", new Backlog()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    void getBacklogByJson() throws Exception {
        mockMvc.perform(get("/mapping/get/backlog/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    void suffixMatch() throws Exception {
        mockMvc.perform(get("/mapping/suffix/backlog.xls"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/mapping/suffix/backlog.pdf"))
                .andExpect(status().isOk());
    }
}