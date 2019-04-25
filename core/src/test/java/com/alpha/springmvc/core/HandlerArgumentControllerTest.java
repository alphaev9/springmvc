package com.alpha.springmvc.core;

import com.alpha.springmvc.domain.Address;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig(locations = "classpath:argument-config.xml")
class HandlerArgumentControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void getBacklogByTitleAndDueTime() throws Exception {
        mockMvc.perform(get("/argument/get")
                .param("title", "test")
                .param("dueTime", "2019-01-01"))
                .andExpect(status().isOk());
    }

    @Test
    void getCooperators() throws Exception {
        MultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        valueMap.add("cooperators", "alpha");
        valueMap.add("cooperators", "laskin");

        mockMvc.perform(post("/argument/addCooperators")
                .params(valueMap))
                .andExpect(status().isOk());
    }

    @Test
    void getBacklogByDueTime() throws Exception {
        mockMvc.perform(get("/argument/get/2019-01-01"))
                .andExpect(status().isOk());
    }

    @Test
    void getBacklogFromDb() throws Exception {
        mockMvc.perform(get("/argument/get/backlog"))
                .andExpect(status().isOk());
    }


    @Test
    void handleWithRequestHeader() throws Exception {
        mockMvc.perform(get("/argument/getHeader")
                .header("Accept-Encoding", "utf-8")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9"))
                .andExpect(status().isOk());
    }

    @Test
    void getCookieValue() throws Exception {
        Cookie cookie = new Cookie("JSESSIONID", "1sdsada231");
        mockMvc.perform(get("/argument/getCookieValue")
                .cookie(cookie))
                .andExpect(status().isOk());
    }

    @Test
    void getMatrixVariable() throws Exception {
        mockMvc.perform(get("/argument/matrix/backlog/1;title=test;description=it's test"))
                .andExpect(status().isOk());
    }

    @Test
    void getMultiPart() throws Exception {
        MockMultipartFile attachment = new MockMultipartFile("attachment", "test file content".getBytes());
        mockMvc.perform(multipart("/argument/attachment")
                .file(attachment))
                .andExpect(status().isOk());
    }


    @Test
    void addAddress() throws Exception {
        mockMvc.perform(post("/argument/addAddress")
                .param("street", "BaoGuang")
                .param("number", "44"))
                .andExpect(status().isOk());
    }

    @Test
    void addBacklogWithAddress() throws Exception {
        mockMvc.perform(post("/argument/addBacklogWithAddress")
                .param("title", "test_backlog")
                .param("address.street", "BaoGuang")
                .param("address.number", "44"))
                .andExpect(status().isOk());
    }

    @Test
    void addBacklogWithCooperators() throws Exception {
        mockMvc.perform(post("/argument/addBacklogWithCooperators")
                .param("cooperators[0].name", "alpha")
                .param("cooperators[0].email", "alpha@sohu.com")
                .param("cooperators[1].name", "lisa")
                .param("cooperators[1].email", "lisa@sohu.com"))
                .andExpect(status().isOk());
    }


    @Test
    void getRequestBody() throws Exception {
        Address address = new Address();
        address.setStreet("baoguang");
        address.setNumber(44);
        Gson gson = new Gson();
        String body = gson.toJson(address);
        mockMvc.perform(post("/argument/requestBody")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("getRequestBody"));
    }

    @Test
    void getRequestBodyByHttpEntity() throws Exception {
        Address address = new Address();
        address.setStreet("baoguang");
        address.setNumber(44);
        Gson gson = new Gson();
        String body = gson.toJson(address);
        mockMvc.perform(post("/argument/httpEntity")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("getRequestBody"));
    }

    @Test
    void customMessageConverter() throws Exception {
        Address address = new Address();
        address.setStreet("baoguang");
        address.setNumber(44);
        Gson gson = new Gson();
        String body = gson.toJson(address);
        System.out.println(body);
        MediaType mediaType = new MediaType("application", "my-format");
        mockMvc.perform(post("/argument/messageConverter")
                .contentType(mediaType)
                .content(body))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("customMessageConverter"));
    }
}