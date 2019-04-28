package com.alpha.springmvc.core.config;

import com.alpha.springmvc.core.conversion.AddressConverter;
import com.alpha.springmvc.core.conversion.AddressFormatter;
import com.alpha.springmvc.core.messageConvert.CustomMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.alpha.springmvc")
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry formatterRegistry) {
        formatterRegistry.addConverter(new AddressConverter());
        formatterRegistry.addFormatter(new AddressFormatter());
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> list) {
        GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
        list.add(converter);
        CustomMessageConverter<Object> customMessageConverter = new CustomMessageConverter<>();
        list.add(customMessageConverter);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> list) {

    }

    @Override
    public Validator getValidator() {
        return null;
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer contentNegotiationConfigurer) {
//        contentNegotiationConfigurer.favorPathExtension(false);
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer asyncSupportConfigurer) {

    }

    @Override
    public void configurePathMatch(PathMatchConfigurer pathMatchConfigurer) {
//        pathMatchConfigurer.setUseSuffixPatternMatch(false);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> list) {

    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> list) {

    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> list) {

    }

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {

    }

    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        return null;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry viewControllerRegistry) {

    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry viewResolverRegistry) {

//        viewResolverRegistry.jsp("/WEB-INF/", ".jsp");
//        viewResolverRegistry.beanName();

        InternalResourceViewResolver jspResolver = new InternalResourceViewResolver();
        jspResolver.setOrder(100);
        jspResolver.setPrefix("/WEB-INF");
        jspResolver.setSuffix(".jsp");
        viewResolverRegistry.viewResolver(jspResolver);

        BeanNameViewResolver beanNameViewResolver = new BeanNameViewResolver();
        beanNameViewResolver.setOrder(0);

        viewResolverRegistry.viewResolver(beanNameViewResolver);


        viewResolverRegistry.viewResolver(thymeleafViewResolver(templateEngine(templateResolver())));

        UrlBasedViewResolverRegistration freeMarker = viewResolverRegistry.freeMarker();
        freeMarker.prefix("/");
        freeMarker.suffix(".ftl");
        freeMarker.viewNames("freemarker/*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer defaultServletHandlerConfigurer) {
        defaultServletHandlerConfigurer.enable();

    }

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {

    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer configExtenalDataSource() {
        PropertySourcesPlaceholderConfigurer placeholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        ClassPathResource resource = new ClassPathResource("app.properties");
        placeholderConfigurer.setLocation(resource);
        return placeholderConfigurer;
    }

    @Bean("returnValue/model")
    public View BeanNameView() {
        return new InternalResourceView("/beanName.jsp");
    }

    //    @Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();
        adapter.setIgnoreDefaultModelOnRedirect(true);
        return adapter;
    }

    @Bean
    public ViewResolver thymeleafViewResolver(SpringTemplateEngine templateEngine) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine);
        resolver.setViewNames(new String[]{"thymeleaf/*"});
        return resolver;
    }


    @Bean
    public SpringTemplateEngine templateEngine(TemplateResolver templateResolver) {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver);
        return engine;
    }

    @Bean
    public TemplateResolver templateResolver() {
        TemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix("/template/");
        resolver.setSuffix(".html");
        resolver.setCacheable(false);
        resolver.setTemplateMode("HTML5");
        return resolver;
    }

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPath("/template/");
        configurer.setDefaultEncoding("UTF-8");
        return configurer;
    }

    @Bean("backlogJson")
    public MappingJackson2JsonView jsonView() {
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        jsonView.setModelKey("backlog");
        return jsonView;
    }

}
