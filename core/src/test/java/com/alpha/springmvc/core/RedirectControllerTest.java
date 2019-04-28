package com.alpha.springmvc.core;

import com.alpha.springmvc.core.config.AppConfig;
import com.alpha.springmvc.core.config.AppConfigBasedAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.FlashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig(AppConfig.class)
class RedirectControllerTest {
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void throughQueryParameter() throws Exception {
        mockMvc.perform(get("/redirect/queryParameter"))
                .andExpect(redirectedUrl("redirect/test?age=40&hobby=reading&hobby=coding&hobby=sport"))
                .andDo(print());
    }

    @Test
    void throughPathVariable() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/redirect/pathVariable/40"))
                .andExpect(redirectedUrl("/redirect/test/40"))
                .andExpect(redirectedUrlTemplate("/redirect/test/{age}", 40))
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        String redirectedUrl = response.getRedirectedUrl();


        mockMvc.perform(get(redirectedUrl))
                .andExpect(status().isOk());
    }

    @Test
    void throughRedirectAttributes() throws Exception {
        mockMvc.perform(get("/redirect/redirectAttributes"))
                .andExpect(model().attributeDoesNotExist("age"))
                .andDo(print());

    }

    @Test
    void throughFlashAttribute() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/redirect/flashAttribute"))
                .andExpect(flash().attributeExists("name"))
                .andExpect(flash().attributeExists("address"))
                .andReturn();
        FlashMap flashMap = mvcResult.getFlashMap();
        mockMvc.perform(get("/redirect/test")
                .flashAttrs(flashMap))
                .andExpect(flash().attributeCount(0));
    }
}