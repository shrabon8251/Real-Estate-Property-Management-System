package bd.edu.seu.repms.Config;

import bd.edu.seu.repms.Service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;


    // Password Encode করার জন্য
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }


    // Spring Security Configuration
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http

                // কোন URL access করা যাবে
                .authorizeHttpRequests(auth -> auth

                        // Login/Register সবাই access করতে পারবে
                        .requestMatchers(
                                "/login",
                                "/register",
                                "/css/**",
                                "/uploads/**"
                        ).permitAll()

                        // বাকি সব URL-এর জন্য Login লাগবে
                        .anyRequest().authenticated()
                )


                // Login Configuration
                .formLogin(form -> form

                        // আমাদের custom login page
                        .loginPage("/login")

                        // Login successful হলে
                        .defaultSuccessUrl(
                                "/property/list",
                                true
                        )

                        .permitAll()
                )


                // Logout Configuration
                .logout(logout -> logout

                        .logoutSuccessUrl("/login?logout")

                        .permitAll()
                );


        return http.build();
    }
}