package com.fs.demosocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Administrator
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //设置Spring Security对/和"/login"路径不拦截
                .antMatchers("/", "login").permitAll()
                .anyRequest().authenticated()
                //设置Spring Security的登陆页面访问路径为login
                .and().formLogin().loginPage("/login")
                //登陆成功后转向/chat路径
                .defaultSuccessUrl("/chat")
                .permitAll().and().logout()
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //配置两个用户,角色是user
        auth.inMemoryAuthentication()
                .withUser("james").password("james").roles("user")
                .and()
                .withUser("curry").password("curry").roles("user");
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置Spring Secutiry不拦截/resources/static/目录下的静态资源
        web.ignoring().antMatchers("/resources/static/**");
    }

}
