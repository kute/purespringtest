package com.kute.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;
import freemarker.template.utility.XmlEscape;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.xml.MarshallingView;

import java.util.*;

/**
 * created by kute on 2018/07/21 18:04
 * <p>
 * 视图解析器
 */
@Configuration
public class ViewResolverConfiguration {

    @Bean
    public ViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/template/");
        resolver.setSuffix("*.jsp");
        // support JSTL
        resolver.setViewClass(JstlView.class);
        return resolver;
    }

    /**
     *
     * @param contentNegotiationManager
     *        此 contentNegotiationManager bean是由  contentNegotiationManagerFactoryBean() 工厂产生
     * @return
     */
    @Bean
    public ContentNegotiatingViewResolver contentNegotiatingViewResolver(ContentNegotiationManager contentNegotiationManager) {
        ContentNegotiatingViewResolver contentNegotiatingViewResolver = new ContentNegotiatingViewResolver();

        contentNegotiatingViewResolver.setUseNotAcceptableStatusCode(true);
        contentNegotiatingViewResolver.setOrder(1);

        // set contentNegotiationManager
        contentNegotiatingViewResolver.setContentNegotiationManager(contentNegotiationManager);
//        contentNegotiatingViewResolver.setContentNegotiationManager(contentNegotiationManagerFactoryBean().getObject());

        // set viewResolvers
        List<ViewResolver> viewResolvers = new ArrayList<>();
        viewResolvers.add(internalResourceViewResolver());
        viewResolvers.add(freeMarkerViewResolver());
        contentNegotiatingViewResolver.setViewResolvers(viewResolvers);

        // set defaultViews
        List<View> defaultViews = new ArrayList<>();
        defaultViews.add(mappingJackson2JsonView());
        defaultViews.add(marshallingView());
        contentNegotiatingViewResolver.setDefaultViews(defaultViews);

        return contentNegotiatingViewResolver;
    }

    /**
     *
     * 创建  ContentNegotiationManager 方法：
     * 1. 通过配置 ContentNegotiationManagerFactoryBean 创建
     * 2. 继承 WebMvcConfigurationSupport 实现 configureContentNegotiation() 方法配置
     *
     * @return
     */
    @Bean(name = "mvcContentNegotiationManager")
    public ContentNegotiationManagerFactoryBean contentNegotiationManagerFactoryBean() {
        ContentNegotiationManagerFactoryBean factoryBean = new ContentNegotiationManagerFactoryBean();
        factoryBean.setFavorPathExtension(true);
        factoryBean.setIgnoreAcceptHeader(true);
        factoryBean.setDefaultContentType(MediaType.APPLICATION_JSON_UTF8);

        Properties properties = new Properties();
        properties.setProperty("html", MediaType.TEXT_HTML_VALUE);
        properties.setProperty("json", MediaType.APPLICATION_JSON_UTF8_VALUE);
        properties.setProperty("xml", MediaType.APPLICATION_XML_VALUE);
        properties.setProperty("pdf", MediaType.APPLICATION_PDF_VALUE);
        factoryBean.setMediaTypes(properties);

        return factoryBean;
    }

    @Bean
    public MarshallingView marshallingView() {
        MarshallingView view = new MarshallingView();
        view.setMarshaller(xStreamMarshaller());
        view.setModelKey("result");
        view.setContentType(MediaType.APPLICATION_XML_VALUE);
        return view;
    }

    @Bean
    public MappingJackson2JsonView mappingJackson2JsonView() {
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        view.setObjectMapper(objectMapper());
        view.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        view.setModelKeys(Sets.newHashSet("code", "message", "result", "data"));
        return view;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }

    @Bean
    public XStreamMarshaller xStreamMarshaller() {
        XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
        xStreamMarshaller.setAutodetectAnnotations(true);
        xStreamMarshaller.setStreamDriver(streamDriver());
        return xStreamMarshaller;
    }

    @Bean
    public HierarchicalStreamDriver streamDriver() {
        return new DomDriver("UTF-8");
    }

    @Bean
    public XmlEscape fmXmlEscape() {
        return new XmlEscape();
    }

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPath("/WEB-INF/template/");

        Properties settings = new Properties();
        settings.setProperty("template_update_delay", "0");
        settings.setProperty("default_encoding", "UTF-8");
        settings.setProperty("locale", "zh_cn");
        settings.setProperty("number_format", "0.##########");
        settings.setProperty("url_escaping_charset", "UTF-8");
        settings.setProperty("template_exception_handler", "com.kute.exception.FreemarkerTemplateExceptionHandler");
        configurer.setFreemarkerSettings(settings);

        Map<String, Object> variables = new HashMap<>(1);
        variables.put("xml_escape", fmXmlEscape());
        configurer.setFreemarkerVariables(variables);

        return configurer;
    }

    @Bean
    public FreeMarkerViewResolver freeMarkerViewResolver() {
        FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
        viewResolver.setCache(false);
        viewResolver.setPrefix("");
        viewResolver.setSuffix(".ftl");
        viewResolver.setExposeSpringMacroHelpers(true);
        viewResolver.setExposeRequestAttributes(true);
        viewResolver.setExposeSessionAttributes(true);
        viewResolver.setContentType("text/html;charset=UTF-8");
        return viewResolver;
    }

}
