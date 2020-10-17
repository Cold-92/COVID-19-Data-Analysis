package org.cold92.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity // 启动SpringSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//打开权限验证
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 支持数据库存储用户数据
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 强散列哈希加密实现
     * @return
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // 禁止跨域csrf攻击防御
            .csrf().disable()
            // 认证
            .formLogin() // 开启表单登录模式
                .loginPage("/toLogin") // 用户未登录时, 访问任何资源都跳转到该路径
                .loginProcessingUrl("/login") // 表单中action的地址, 处理认证请求的路径
                .usernameParameter("username") // 默认为username
                .passwordParameter("password") // 默认为password
                .defaultSuccessUrl("/index") // 登录成功跳转接口
                .failureUrl("/toLogin") // 登录失败跳转页面
                .and() // 跳回配置文件父级上下文
            // 赋权
            .authorizeRequests() // 配置权限
                .antMatchers("/toRegister", "/register", "/toLogin", "/login", "/css/**", "/js/**")
                .permitAll() // 上面url所有拦截器不拦截
                .antMatchers("/read").hasAnyRole("admin", "user") // ROLE_admin和ROLE_user角色可以访问
                .antMatchers("/update").hasRole("admin") // ROLE_admin角色可以访问
                .anyRequest().authenticated(); // 上面两行url全部都拦截

        http
            // 注销
            .logout()
                .logoutUrl("/logout");
    }

    /**
     * 使用内存配置账户
     * @param auth
     * @throws Exception
     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password(bCryptPasswordEncoder().encode("123456"))
//                .roles("user")
//                .and()
//                .withUser("admin")
//                .password(bCryptPasswordEncoder().encode("123456"))
//                .roles("admin")
//                .and()
//                .passwordEncoder(bCryptPasswordEncoder()); // 指定密码加密方式
//    }

    /**
     * 使用数据库配置账户, 自动实现用户和密码验证
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}
