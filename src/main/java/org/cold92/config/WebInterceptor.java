package org.cold92.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class WebInterceptor implements HandlerInterceptor {

    /**
     * 访问Servlet前自动执行
     * @param request
     * @param response
     * @param handler
     * @return 返回true就放行，返回false就拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        // 检测用户是否登录
        Object user = request.getSession().getAttribute("loginUser");
        // 放行
        if (user != null) {
            return true;
        }
        // 不放行
        request.getRequestDispatcher("/toLogin").forward(request, response);
        return false;
    }
}
