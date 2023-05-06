package ru.ulstu.is.sbapp.Configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.ulstu.is.sbapp.Configuration.Jwt.JwtFilter;
import ru.ulstu.is.sbapp.User.controller.UserController;
import ru.ulstu.is.sbapp.User.model.UserRole;
import ru.ulstu.is.sbapp.User.service.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        securedEnabled = true
)
public class SecurityConfiguration {
    private final Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);
    public static final String SPA_URL_MASK = "/{path:[^\\.]*}";
    private final UserService userService;
    private final JwtFilter jwtFilter;

    public SecurityConfiguration(UserService userService) {
        this.userService = userService;
        this.jwtFilter = new JwtFilter(userService);
        createAdminOnStartup();
    }

    private void createAdminOnStartup() {
        final String admin = "admin";
        if (userService.findByLogin(admin) == null) {
            log.info("Admin user successfully created");
            userService.addUser(admin, "admin@gmail.com", admin, admin, UserRole.ADMIN);
        }
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/", SPA_URL_MASK).permitAll()
                .requestMatchers(HttpMethod.POST, UserController.URL_LOGIN).permitAll()
                .requestMatchers(HttpMethod.POST, UserController.URL_SIGN_UP).permitAll()
                .requestMatchers(HttpMethod.POST, UserController.URL_WHO_AM_I).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .anonymous();
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(HttpMethod.OPTIONS, "/**")
                .requestMatchers("/*.js")
                .requestMatchers("/*.html")
                .requestMatchers("/*.css")
                .requestMatchers("/assets/**")
                .requestMatchers("/favicon.ico")
                .requestMatchers("/.js", "/.css")
                .requestMatchers("/swagger-ui/index.html")
                .requestMatchers("/webjars/**")
                .requestMatchers("/swagger-resources/**")
                .requestMatchers("/v3/api-docs/**")
                .requestMatchers("/h2-console/**");
    }
}