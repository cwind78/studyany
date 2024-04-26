package com.example.studyany.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
//    @Autowired
//    private DataSource dataSource;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((authorizeRequests) ->
                //아래 스크립트로 로그인 페이지가 나오지 않는다. 또한 요청되는 모든 url이 허용된다.
//               authorizeRequests.anyRequest().permitAll()
                //아래 스크립트는 로그인 페이지가 나온다.
                authorizeRequests
                        .requestMatchers("/api1").hasRole("user")
                        .requestMatchers("/api2").hasRole("admin")
                        .anyRequest().authenticated()
            )
            .formLogin((formLogin) ->
                formLogin
                    .usernameParameter("username")
                    .passwordParameter("password")
                        //로그인 하고 나서 이동할 url
                    .defaultSuccessUrl("/user/chk/id", true)
            )
            .logout() // 로그아웃 설정 추가
                .logoutUrl("/logout") // 로그아웃 URL 설정
                .logoutSuccessUrl("/login?logout") // 로그아웃 성공 후 리다이렉트할 URL 설정
                .invalidateHttpSession(true) // HttpSession을 무효화합니다.
                .deleteCookies("JSESSIONID"); // 로그아웃 시 쿠키 삭제;

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
//        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager();
//        userDetailsManager.setDataSource(dataSource);
//        // 사용자 테이블명과 컬럼명을 설정합니다.
//        userDetailsManager.setUsersByUsernameQuery(
//                "SELECT id, pwd, 1 as enabled FROM user_master WHERE id = ?");
//        userDetailsManager.setAuthoritiesByUsernameQuery(
//                "SELECT id, 'ROLE_USER' FROM user_master WHERE id = ?");
//        return userDetailsManager;
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser((User.withUsername("user1").password("1234").roles("user").build()));
        return manager;
    }

    @Bean
    //패스워드 암호화 해준다.
    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }
}
