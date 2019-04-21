package com.alpha.springmvc.core;

import com.alpha.springmvc.domain.Address;
import com.alpha.springmvc.domain.Cooperator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig(AppConfig.class)
class ModelControllerTest {
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    @Test
    void modelWhy() throws Exception {
        mockMvc.perform(get("/model/why"))
                .andExpect(status().isOk())
                .andExpect(request().attribute("test", hasProperty("street", equalTo("baoguang"))));
    }

    @Test
    void modelForFormObject() throws Exception {
        mockMvc.perform(get("/model/formObject")
                .param("street", "baoguang")
                .param("number", "44"))
                .andExpect(status().isOk())
                .andExpect(request().attribute("address", notNullValue()));
    }


    @Test
    void embeddedObject() throws Exception {
        LinkedMultiValueMap address = new LinkedMultiValueMap();
        address.add("address.street", "baoguang");
        address.add("address.number", "44");

        Address address1 = new Address();
        address1.setStreet("baoguang");
        address1.setNumber(44);
        mockMvc.perform(get("/model/embedded")
                .params(address))
                .andExpect(status().isOk())
                .andExpect(model().attribute("backlog", hasProperty("address", equalTo(address1))));
    }

    @Test
    void collectionFieldBinding() throws Exception {

        LinkedMultiValueMap cooperators = new LinkedMultiValueMap();
        cooperators.add("cooperators[0].name", "alpha");
        cooperators.add("cooperators[0].email", "alpha@sohu.com");
        cooperators.add("cooperators[1].name", "lisa");
        cooperators.add("cooperators[1].email", "lisa@163.com");
        Cooperator cooperator = new Cooperator("alpha", "alpha@sohu.com");

        mockMvc.perform(get("/model/collection")
                .params(cooperators))
                .andExpect(status().isOk())
                .andExpect(model().attribute("backlog", hasProperty("cooperators", hasItem(cooperator))));
    }

    @Test
    void sessionAttributes() throws Exception {
        mockMvc.perform(get("/model/session"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("alpha"))
                .andExpect(request().sessionAttribute("alpha", notNullValue()));
    }

    @Test
    void sessionAttributesRemove() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/model/session"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("alpha"))
                .andExpect(request().sessionAttribute("alpha", notNullValue()))
                .andReturn();
        HttpSession session = mvcResult.getRequest().getSession();


        mockMvc.perform(get("/model/sessionRemove")
                .session((MockHttpSession) session))
                .andExpect(status().isOk())
                .andExpect(request().sessionAttribute("alpha", nullValue()));
    }

    @Test
    void preModelAttribute() throws Exception {
        mockMvc.perform(get("/model/preModelAttribute"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("lisa"))
                .andExpect(request().sessionAttribute("lisa", notNullValue()));
    }

    @Test
    void sessionAttribute() throws Exception {
        mockMvc.perform(get("/model/sessionAttribute"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("lisa"))
                .andExpect(request().sessionAttribute("lisa", notNullValue()))
                .andExpect(request().sessionAttribute("surprise", is("why?")));
    }
}