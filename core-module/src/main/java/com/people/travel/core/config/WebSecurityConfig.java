package com.people.travel.core.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.COOKIES;

@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin").password(encoder.encode("1111")).roles("ADMIN", "USER", "SYS").build());
        manager.createUser(User.withUsername("user").password(encoder.encode("1111")).roles("USER").build());
        manager.createUser(User.withUsername("sys").password(encoder.encode("1111")).roles("SYS", "USER").build());

        return manager;
    }
    @Bean
    public WebSecurityCustomizer ignoringCustomizer() {
        return (web) -> web.ignoring()
                .antMatchers(
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/webjars/**",
                        "/favicon.*",
                        "/*/icon-*",
                        "/fonts/**",
                        "/scss/**",
                        "/evo-calendar/**"
                        ,"/error"
                        );
    }

    // 세션 생명 주기 이벤트 감지, security 최신 상태 유지
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher(){
        return new HttpSessionEventPublisher();

    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/")
                        .permitAll()
                        .antMatchers("/travel-register","/travel-schedule")
                        .hasRole("USER")
                        .anyRequest()
                        .authenticated()

                )
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .usernameParameter("userId")
                .passwordParameter("password")
                .and()
                /*
                 * Clear-Site-Data 헤더를 사용하여 로그아웃 시 JSESSIONID 쿠키를 명시적으로 삭제, Clear-Site-Date 지원 하는 모든 컨테이너에서 작동
                 * deleteCookies 사용한 쿠키 삭제는 모든 서블릿 컨테이너에서 작동하는 것을 보장하지 않음
                 */
                .logout((logout) -> logout
                        .addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(COOKIES))))

                /*
                 *동시 세션 제어, 세션 고정 공격 방지 설정, 전략 로그인 마다 새로운 세션 생성
                 * 종류 changeSessionId, none(권장 하지 않음)
                 */

                .sessionManagement(session -> session
                        .maximumSessions(1)// 사용자가 여러번 로그인 방지, 두번 째 로그인 시 첫번 째 로그인 무효화
                        .maxSessionsPreventsLogin(true)// 두번 째 로그인 방지
                        .and()
                        .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::newSession)
                        .invalidSessionUrl("/")
                );

        return http.build();
    }


}
