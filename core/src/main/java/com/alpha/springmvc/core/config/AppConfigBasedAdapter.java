package com.alpha.springmvc.core.config;

import com.alpha.springmvc.core.conversion.AddressFormatter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.alpha.springmvc")
public class AppConfigBasedAdapter extends WebMvcConfigurationSupport {

    @Override
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        System.out.println("here..........");
//        RequestMappingHandlerAdapter adapter = super.requestMappingHandlerAdapter();
        RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();
        adapter.setIgnoreDefaultModelOnRedirect(true);
        return adapter;
    }

    @Override
    protected void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new AddressFormatter());
        super.addFormatters(registry);

    }


}
