package mk.ukim.finki.brainboost.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/login/**","/login" ,"/api/**","/","/login/**", "/home", "/assets/**", "/register/**","/css/**","/js/**","/img/**","/static/**","/styles/**", "/images/**").permitAll()
                        .requestMatchers("/all_courses/add-form/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated()

                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                        .successForwardUrl("/all_courses")
                        .failureUrl("/login?error=incorrect+username+or+password")
                        .defaultSuccessUrl("/all_courses")
                )
                .logout((logout) -> logout.permitAll()
                        .logoutUrl("/logout")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/login")
                );




        return http.build();
    }

}