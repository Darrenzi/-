package com.cosmetics.config;

import com.cosmetics.module.solution.service.impl.SolutionServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Darren
 * @create 2022/3/4 16:56
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //配置图片静态资源处理器
        registry.addResourceHandler("/static/**")
                .addResourceLocations("file:" + SolutionServiceImpl.SAVE_PATH);
    }
}
