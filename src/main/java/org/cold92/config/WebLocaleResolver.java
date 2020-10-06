package org.cold92.config;

import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * 不依赖浏览器配置手动切换语言实现国际化功能
 */
public class WebLocaleResolver implements LocaleResolver {
    /**
     * 核心逻辑
     * @param httpServletRequest
     * @return
     */
    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        String language = httpServletRequest.getParameter("lan");
        Locale locale;
        // 如果没有特殊参数传入, 就使用默认语言配置
        if (language == null) {
            locale = Locale.getDefault();
        } else { // 如果有, 就构建目标语言
            String[] lanSplit = language.split("_");
            locale = new Locale(lanSplit[0], lanSplit[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
