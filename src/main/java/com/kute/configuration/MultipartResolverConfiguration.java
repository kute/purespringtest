package com.kute.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.IOException;

/**
 * created by kute on 2018/07/21 18:56
 */
@Configuration
public class MultipartResolverConfiguration {

    @Bean
    public MultipartResolver multipartResolver() throws IOException {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        resolver.setMaxUploadSize(2097152);
        resolver.setMaxInMemorySize(0);
        resolver.setUploadTempDir(new FileSystemResource("/tmp"));
        return resolver;
    }

}
