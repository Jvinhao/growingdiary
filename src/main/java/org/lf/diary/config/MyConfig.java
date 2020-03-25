package org.lf.diary.config;

import org.lf.diary.interceptor.ChatInterceptor;
import org.lf.diary.interceptor.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/15 15:24
 * @Description: TODO
 */

@Component
public class MyConfig implements WebMvcConfigurer {

    @Autowired
    private MyInterceptor myInterceptor;

    @Autowired
    private ChatInterceptor chatInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/css/**","/fonts/**","/images/**",
                        "/js/**","/audio/**","/img/**","/translations/**","/photos/**");
        registry.addInterceptor(chatInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/css/**","/fonts/**","/images/**",
                        "/js/**","/audio/**","/img/**","/translations/**","/photos/**");
    }
}
