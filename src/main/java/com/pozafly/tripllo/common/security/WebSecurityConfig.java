package com.pozafly.tripllo.common.security;

import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    // 암호화에 필요한 PasswordEncoder 를 Bean 등록합니다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // authenticationManager를 Bean 등록합니다.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // 여기는 SWAGGER 의 CORS 설정임.
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ImmutableList.of("http://localhost:8080", "https://github.com/**"));
        configuration.setAllowedMethods(ImmutableList.of("HEAD","GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        // 토큰을 Authorization 이라는 이름으로 받겠다.
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() // rest api 만을 고려하여 기본 설정은 해제.
                .csrf().disable() // csrf 보안 토큰 disable처리.
                .formLogin().disable() //기본 로그인 페이지 없애기
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 역시 사용하지 않음.
                .and()
                .authorizeRequests() // 요청에 대한 사용권한 체크
//                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("profile").permitAll()

                // post 방식의 user create(회원가입)은 허용한다.
                .antMatchers(HttpMethod.POST, "/api/user").permitAll()
                // 회원가입 전 사용하고 싶은 회원 id를 validation 해볼 수 있는 api도 open
                .antMatchers("/api/user/valid/**").permitAll()
                // 로그인 오픈
                .antMatchers("/api/login/**").permitAll()
                .antMatchers("/api/logout").permitAll()
                .antMatchers("/api/email/*").permitAll()

                // 인증된 사용자만 가능하다(즉, 토큰을 준 사람만이 가능한 것임.)
                .antMatchers("/api/**").authenticated()
                // role이 ROLE_USER 인 역할만 통과
                .antMatchers("/api/**").hasRole("USER")
                .anyRequest().permitAll() // 그외 나머지 요청은 누구나 접근 가능
                .and()
                .cors()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
        // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다

                ;
    }
}