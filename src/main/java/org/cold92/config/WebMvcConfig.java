package org.cold92.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private WebInterceptor interceptor;

    /**
     * 将拦截器注册到SpringMVC中
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截所有请求除了excludePathPatterns标注的这几个请求
        registry.addInterceptor(interceptor).addPathPatterns("/**")
                .excludePathPatterns("/toLogin", "/login", "/toRegister", "/register", "/*.css", "/*.js");
    }

    /**
     * 将手动配置国际化器配置进入SpringMVC
     * @return
     */
    @Bean
    public LocaleResolver localeResolver() {
        return new WebLocaleResolver();
    }
}
