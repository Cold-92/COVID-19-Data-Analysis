package org.cold92.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

@Configuration
public class WebConfig {

    /**
     * 将手动配置国际化器配置进入Spring
     * @return
     */
    @Bean
    public LocaleResolver localeResolver() {
        return new WebLocaleResolver();
    }
}
