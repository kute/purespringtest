package com.kute.configuration;

import com.kute.servlet.HelloServlet;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;
import java.util.EnumSet;

/**
 * created by kute on 2018/07/20 16:47
 * <p>
 * <p>
 * 1. servlet3.0 容器在启动时会查找 ServletContainerInitializer 接口的实现类，找到交由实现类去配置servlet容器
 * 2. spring提供了 ServletContainerInitializer接口 的实现：SpringServletContainerInitializer，此类会查找
 * WebApplicationInitializer接口的实现类
 * 3. spring3.2引入了WebApplicationInitializer接口的实现类：AbstractAnnotationConfigDispatcherServletInitializer
 * 4. AbstractAnnotationConfigDispatcherServletInitializer这个类负责配置DispatcherServlet、初始化Spring MVC容器和Spring容器
 * ,创建了DispatcherServlet和ContextLoaderListener
 */
public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    public ApplicationInitializer() {
        super();
    }

    /**
     * 初始化spring根上下文：ContextLoaderListener，负责加载 其他bean
     *
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfiguration.class};
    }

    /**
     * 负责获取Spring MVC应用容器：DispatcherServlet，负责 加载 控制器，视图解析器以及处理器映射
     *
     * @return
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebMvcConfiguration.class};
    }

    /**
     * 负责指定需要由DispatcherServlet映射的路径
     *
     * @return
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    /**
     * 注册过滤器，只映射到 DispatcherServlet
     *
     * @return
     */
    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
//        DelegatingFilterProxy securityFilterChain = new DelegatingFilterProxy("springSecurityFilterChain");
        return new Filter[]{characterEncodingFilter};
//        return new Filter[]{characterEncodingFilter, securityFilterChain};
    }

    @Override
    protected void registerDispatcherServlet(ServletContext servletContext) {
        super.registerDispatcherServlet(servletContext);
    }

    /**
     * 当 registerDispatcherServlet 完成后进行额外的配置
     *
     * @param registration
     */
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setLoadOnStartup(1);
        registration.setInitParameter("defaultHtmlEscape", "true");
        registration.setInitParameter("spring.profiles.default", "test");

        // 文件上传:临时目录，文件大小，整个请求大小，全部写入临时目录
        registration.setMultipartConfig(new MultipartConfigElement("/tmp/location/of/upload/file", 2097152, 4194304, 0));
    }

    @Override
    protected WebApplicationContext createRootApplicationContext() {
        return super.createRootApplicationContext();
    }

    @Override
    protected WebApplicationContext createServletApplicationContext() {
        return super.createServletApplicationContext();
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);

        // 添加自定义 servlet
        ServletRegistration.Dynamic helloServlet = servletContext.addServlet("helloServlet", HelloServlet.class);
        helloServlet.setAsyncSupported(true);
        helloServlet.addMapping("/servlet/hello");

        // 添加 filter
        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
        encodingFilter.setAsyncSupported(true);
        encodingFilter.setInitParameter("encoding", "UTF-8");
        encodingFilter.setInitParameter("forceEncoding", "true");
        encodingFilter.addMappingForUrlPatterns(
                EnumSet.of(DispatcherType.REQUEST, DispatcherType.ASYNC, DispatcherType.FORWARD, DispatcherType.INCLUDE),
                false, "/*");

    }

    @Override
    protected String getServletName() {
        return super.getServletName();
    }

    @Override
    protected FrameworkServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
        return super.createDispatcherServlet(servletAppContext);
    }

    @Override
    protected ApplicationContextInitializer<?>[] getServletApplicationContextInitializers() {
        return super.getServletApplicationContextInitializers();
    }

    @Override
    protected FilterRegistration.Dynamic registerServletFilter(ServletContext servletContext, Filter filter) {
        return super.registerServletFilter(servletContext, filter);
    }

    @Override
    protected boolean isAsyncSupported() {
        return super.isAsyncSupported();
    }

    @Override
    protected void registerContextLoaderListener(ServletContext servletContext) {
        super.registerContextLoaderListener(servletContext);
    }

    @Override
    protected ApplicationContextInitializer<?>[] getRootApplicationContextInitializers() {
        return super.getRootApplicationContextInitializers();
    }
}
