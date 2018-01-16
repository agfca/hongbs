package com.llrj.hongbs;

import com.llrj.hongbs.domain.Cons;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class FileConfigure
        extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /*配置外部静态资源*/
        registry.addResourceHandler("/word/**").addResourceLocations("file:"+ Cons.filePath);
        super.addResourceHandlers(registry);
    }

}