package com.example.restapi.config;

import com.example.restapi.jwt.JwtAuthenticationFilter;
import com.example.restapi.jwt.JwtAuthorizationFilter;
import com.example.restapi.repository.MemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsConfig corsConfig;
    private final MemberRepository memberRepository;

    public SecurityConfig(CorsConfig corsConfig, MemberRepository memberRepository) {
        this.corsConfig = corsConfig;
        this.memberRepository = memberRepository;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() { // 로그인 할 때 비밀번호 이걸로 검증함
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilter(corsConfig.corsFilter())
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .addFilter(new JwtAuthenticationFilter(authenticationManager())) // WebSecurityConfigurerAdapter 이 가지고 있는 authenticationManager() 을 인자로 넣어줌
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), memberRepository))
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/api/member/join").permitAll()
                .anyRequest().authenticated();
    }
}