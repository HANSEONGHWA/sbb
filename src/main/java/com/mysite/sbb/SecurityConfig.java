package com.mysite.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // 로그인 여부 판별
public class SecurityConfig {
    @Bean
    //인증 인가에 대한 설정(HttpSecurity http)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests().requestMatchers(
                // ("/**") 특정 경로 이하의 모든 것을 의미, 특정경로는 누구나 접근 가능(permitAll()), 그 외 경로는 인증 후 접근가능
                new AntPathRequestMatcher("/**")).permitAll()

                //스프링 시큐리티에 로그인 URL등록
                .and()
                    .formLogin()
                    .loginPage("/user/login")
                    .defaultSuccessUrl("/question/")

                //로그아웃
                .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                    .logoutSuccessUrl("/question/")
                    .invalidateHttpSession(true)
        ;
        return http.build();
    }

    //패스워드 인코더를 빈으로 등록, 암호를 인코딩하거나 인코딩된 암호와 사용자가 입력한 암호가 같은지 확인.
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}