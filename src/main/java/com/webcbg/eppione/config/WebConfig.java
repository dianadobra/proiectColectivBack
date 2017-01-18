package com.webcbg.eppione.config;

import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

import java.util.List;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by oltud on 5/3/2016.
 */
@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("POST", "GET", "PUT", "DELETE", "OPTIONS")
                ;
            }

//			@Override
//			public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//				converters.add(new MappingJackson2HttpMessageConverter());
//				converters.add(new ByteArrayHttpMessageConverter());
//			}
            
            
        };
    }

    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory multipartConfigFactory = new MultipartConfigFactory();
        multipartConfigFactory.setMaxFileSize("10MB");
        multipartConfigFactory.setMaxRequestSize("50MB");
        return multipartConfigFactory.createMultipartConfig();
    }
    
    @Bean
    public Jackson2ObjectMapperBuilder configureObjectMapper() {
        return new Jackson2ObjectMapperBuilder()
                .modulesToInstall(Hibernate4Module.class);
    }
}