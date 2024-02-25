package com.eventmanagement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// this is config
// check
// now in feature
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
        .cors().and().csrf().disable()
        .authorizeRequests(authorizeRequests ->
            authorizeRequests
                .antMatchers("/attendee/**").permitAll()
                .anyRequest().authenticated()
        )
        .httpBasic(); 
    }
  

}

