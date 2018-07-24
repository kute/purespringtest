package com.kute.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.PathMatcher;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.method.support.CompositeUriComponentsContributor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter;
import org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.resource.ResourceUrlProvider;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Map;

/**
 * created by kute on 2018/07/20 16:49
 * <p>
 * WebMvcConfigurerAdapter 已过时，推荐 WebMvcConfigurationSupport，支持更多配置
 */
@Configuration //配置类
@EnableWebMvc //启用springmvc
@ComponentScans({
        // 配置扫描 controller注解
        @ComponentScan({"com.kute.controller"}) // 默认扫描与配置类相同的package
})
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    private static final Logger logger = LoggerFactory.getLogger(WebMvcConfiguration.class);

    @Autowired
    private ViewResolverConfiguration viewResolverConfiguration;

    public WebMvcConfiguration() {
        super();
    }

    /**
     * 设置顺序 后于  ApplicationContextAware，因为 先初始化spring上下文，再初始化 spring mvc容器
     *
     * @param applicationContext
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        super.setApplicationContext(applicationContext);
        logger.info("Web mvc support setApplicationContext:{}", applicationContext.getId());
    }

    /**
     * 设置顺序 后于 ServletContextAware
     * @param servletContext
     */
    @Override
    public void setServletContext(ServletContext servletContext) {
        super.setServletContext(servletContext);
        logger.info("Web mvc support setServletContext:SimpleName={},ServletContextName={}", servletContext.getClass().getSimpleName(), servletContext.getServletContextName());
    }

    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        return super.requestMappingHandlerMapping();
    }

    @Override
    protected RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
        return super.createRequestMappingHandlerMapping();
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
    }

    @Override
    protected PathMatchConfigurer getPathMatchConfigurer() {
        return super.getPathMatchConfigurer();
    }

    @Override
    protected void configurePathMatch(PathMatchConfigurer configurer) {
        super.configurePathMatch(configurer);
    }

    @Override
    public PathMatcher mvcPathMatcher() {
        return super.mvcPathMatcher();
    }

    @Override
    public UrlPathHelper mvcUrlPathHelper() {
        return super.mvcUrlPathHelper();
    }

    @Override
    public ContentNegotiationManager mvcContentNegotiationManager() {
        return super.mvcContentNegotiationManager();
//        ContentNegotiationManagerFactoryBean factoryBean = viewResolverConfiguration.contentNegotiationManagerFactoryBean();
//        return factoryBean.getObject();
    }

    @Override
    protected Map<String, MediaType> getDefaultMediaTypes() {
        return super.getDefaultMediaTypes();
    }

    /**
     * 配置 contentNegotiationManage
     * @param configurer
     */
    @Override
    protected void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        super.configureContentNegotiation(configurer);

        configurer.defaultContentType(MediaType.APPLICATION_JSON_UTF8)
                .ignoreAcceptHeader(false)
                // 是否通过后缀名来决定 request media type
                .favorPathExtension(true)
                .mediaType("json", MediaType.APPLICATION_JSON_UTF8)
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("html", MediaType.TEXT_HTML)
                .mediaType("png", MediaType.IMAGE_PNG);

    }

    @Override
    public HandlerMapping viewControllerHandlerMapping() {
        return super.viewControllerHandlerMapping();
    }

    @Override
    protected void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
    }

    @Override
    public BeanNameUrlHandlerMapping beanNameHandlerMapping() {
        return super.beanNameHandlerMapping();
    }

    @Override
    public HandlerMapping resourceHandlerMapping() {
        return super.resourceHandlerMapping();
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
    }

    @Override
    public ResourceUrlProvider mvcResourceUrlProvider() {
        return super.mvcResourceUrlProvider();
    }

    @Override
    public HandlerMapping defaultServletHandlerMapping() {
        return super.defaultServletHandlerMapping();
    }

    @Override
    protected void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        super.configureDefaultServletHandling(configurer);
    }

    @Override
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        return super.requestMappingHandlerAdapter();
    }

    @Override
    protected RequestMappingHandlerAdapter createRequestMappingHandlerAdapter() {
        return super.createRequestMappingHandlerAdapter();
    }

    @Override
    protected ConfigurableWebBindingInitializer getConfigurableWebBindingInitializer() {
        return super.getConfigurableWebBindingInitializer();
    }

    @Override
    protected MessageCodesResolver getMessageCodesResolver() {
        return super.getMessageCodesResolver();
    }

    @Override
    protected void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        super.configureAsyncSupport(configurer);
    }

    @Override
    public FormattingConversionService mvcConversionService() {
        return super.mvcConversionService();
    }

    @Override
    protected void addFormatters(FormatterRegistry registry) {
        super.addFormatters(registry);
    }

    @Override
    public Validator mvcValidator() {
        return super.mvcValidator();
    }

    @Override
    protected Validator getValidator() {
        return super.getValidator();
    }

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
    }

    @Override
    protected void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        super.addReturnValueHandlers(returnValueHandlers);
    }

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
    }

    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.extendMessageConverters(converters);
    }

    @Override
    public CompositeUriComponentsContributor mvcUriComponentsContributor() {
        return super.mvcUriComponentsContributor();
    }

    @Override
    public HttpRequestHandlerAdapter httpRequestHandlerAdapter() {
        return super.httpRequestHandlerAdapter();
    }

    @Override
    public SimpleControllerHandlerAdapter simpleControllerHandlerAdapter() {
        return super.simpleControllerHandlerAdapter();
    }

    @Override
    public HandlerExceptionResolver handlerExceptionResolver() {
        return super.handlerExceptionResolver();
    }

    @Override
    protected void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        super.configureHandlerExceptionResolvers(exceptionResolvers);
    }

    @Override
    protected void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        super.extendHandlerExceptionResolvers(exceptionResolvers);
    }

    @Override
    protected ExceptionHandlerExceptionResolver createExceptionHandlerExceptionResolver() {
        return super.createExceptionHandlerExceptionResolver();
    }

    @Override
    public ViewResolver mvcViewResolver() {
        return super.mvcViewResolver();
    }

    @Override
    protected void configureViewResolvers(ViewResolverRegistry registry) {
        super.configureViewResolvers(registry);
    }

    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        super.addCorsMappings(registry);
    }

    @Override
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        return super.mvcHandlerMappingIntrospector();
    }
}
