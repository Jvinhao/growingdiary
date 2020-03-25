package org.lf.diary.config;

import org.lf.diary.interceptor.MissAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * @Author: Jvinh
 * @DateTime: 2020/1/24 15:26
 * @Description: TODO
 */
@Configuration
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/**").permitAll()
        .and().csrf().disable();

        http.logout().logoutSuccessUrl("/").invalidateHttpSession(true).deleteCookies("token");
        http.headers().frameOptions().disable();

        http.exceptionHandling().accessDeniedHandler(getAccessDeniedHandler());
    }

    @Bean
    public AccessDeniedHandler getAccessDeniedHandler() {
        return new MissAccessDeniedHandler();
    }
}
