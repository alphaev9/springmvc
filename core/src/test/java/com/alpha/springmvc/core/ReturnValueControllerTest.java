package com.alpha.springmvc.core;

import com.alpha.springmvc.core.config.AppConfig;
import com.alpha.springmvc.core.config.AppConfigBasedAdapter;
import com.alpha.springmvc.domain.Backlog;
import com.alpha.springmvc.domain.BacklogState;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig(classes = AppConfig.class)
class ReturnValueControllerTest {
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void returnModelAndView() throws Exception {
        mockMvc.perform(get("/returnValue/modelAndView"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("address"))
                .andExpect(forwardedUrl("/test.jsp"));
    }

    @Test
    void returnView() throws Exception {
        mockMvc.perform(get("/returnValue/view"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/redirect"));
    }

    @Test
    void returnModel() throws Exception {
        mockMvc.perform(get("/returnValue/model"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("address"))
                .andExpect(view().name("returnValue/model"))
                .andExpect(forwardedUrl("/WEB-INF/returnValue/model.jsp"));
    }

    /**
     * here,this test case switch from InternalResourceViewResolver to BeanNameViewResolver for outstanding the role of ViewResolver
     */
    @Test
    void returnString() throws Exception {
        mockMvc.perform(get("/returnValue/string"))
                .andExpect(status().isOk())
                .andExpect(view().name("viewResolver"))
                .andExpect(forwardedUrl("/beanName.jsp"));
    }

    @Test
    void returnVoid() throws Exception {
        mockMvc.perform(get("/returnValue/void"))
                .andExpect(status().isOk())
                .andExpect(view().name("returnValue/void"))
                .andDo(print());
    }

    /**
     * notice given relying forward mechanism to dispatch to a view,there are no actual rendering happening.
     * besides this,response would be identical with that in real servlet container.
     */
    @Test
    void returnVoidHandcraft() throws Exception {
        mockMvc.perform(get("/returnValue/handcraft"))
                .andExpect(status().isOk())
                .andExpect(content().string("handcraft response"))
                .andDo(print());
    }


    @Test
    void returnObjectInModel() throws Exception {
        mockMvc.perform(get("/returnValue/objectInModel"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("address"))
                .andExpect(view().name("returnValue/objectInModel"));
    }

    @Test
    void returnObjectInResponse() throws Exception {
        Backlog backlog = new Backlog();
        backlog.setTitle("test");
        backlog.setDescription("it's backlog for test");
        backlog.setState(BacklogState.fresh);
        Gson gson = new Gson();

        mockMvc.perform(get("/returnValue/objectInResponse"))
                .andExpect(status().isFound())
                .andExpect(content().json(gson.toJson(backlog)))
                .andDo(print());

        MediaType mediaType = new MediaType("application", "my-format");

        mockMvc.perform(get("/returnValue/objectInResponse")
                .accept(mediaType))
                .andExpect(status().isFound())
                .andExpect(content().json(gson.toJson(backlog)))
                .andDo(print());
    }
}