package org.cold92.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity // 启动spring security
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 构建密码机，加密解密密码
     * 不配置会报警告
     * @return
     */
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * 配置认证管理器(配置用户信息)
     * AuthenticationManager 是用户身份的管理者, 是认证的入口,
     * 在认证用户身份信息的时候, 就回从中获取用户身份,和从http中拿的用户身份做对比
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("root")
                .password("123456")
                .roles("admin");
    }

    /**
     * 配置核心过滤器
     * ignoring() 方法用来忽略 Spring Security 对静态资源的控制
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**", "/image/**");
    }

    /**
     * 配置安全过滤器链，自定义安全访问策略
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() // 配置路径拦截，表明路径访问所对应的权限，角色，认证信息(允许使用HttpRequest拦截机制)
        .and() // 连接以上策略的连接器，用来组合安全策略。实际上就是"而且"的意思
        .formLogin() // 指定支持基于表单的身份验证。如果未指定FormLoginConfigurer#loginPage(String)，则将生成默认登录页面
        .permitAll() // 无条件允许访问
        .and()
        .csrf().disable();// 关闭 CSRF 支持
    }
}
