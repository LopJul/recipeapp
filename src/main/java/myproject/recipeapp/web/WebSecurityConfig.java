package myproject.recipeapp.web;
 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

 

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {
   
 
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

 
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
 
        http.authorizeHttpRequests(
                authorize -> authorize
                .requestMatchers("/login", "/login/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/css/**", "/images/**").permitAll()
                .anyRequest().authenticated())
                // Käyttää HTTP Basic -autentikointia oletusasetuksilla (Postman)
                        .httpBasic(Customizer.withDefaults())
                .headers(headers ->
                    headers.frameOptions(frameOptions -> frameOptions
                        .disable())) // h2console
                                   
                .formLogin(formlogin ->
                    formlogin.loginPage("/login")
                    .defaultSuccessUrl("/recipes", true)
                    .permitAll())
            .logout(logout -> logout.permitAll())
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**")
                .ignoringRequestMatchers("/api/**"));
        return http.build();

        
    }
 
}