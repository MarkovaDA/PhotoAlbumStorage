package darya.markova.photostorage.config;

import darya.markova.photostorage.security.JwtAuthorizationTokenFilter;
import darya.markova.photostorage.security.JwtTokenUtil;
import darya.markova.photostorage.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtTokenUtil tokenUtil = new JwtTokenUtil();
        JwtAuthorizationTokenFilter filter = new JwtAuthorizationTokenFilter(customUserDetailsService, tokenUtil);
        http.cors().and().addFilterBefore(filter, BasicAuthenticationFilter.class)
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/user/signup").permitAll()
                //.antMatchers("/api/user/list").permitAll()
                .antMatchers("/api/photo/get").permitAll()
                .antMatchers("/api/signin").permitAll()
                .anyRequest().hasAuthority("ROLE_USER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return this.customUserDetailsService;
    }
}
