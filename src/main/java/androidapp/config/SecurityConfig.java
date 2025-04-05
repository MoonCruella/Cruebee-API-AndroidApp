package androidapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration

// DON'T use the default luong of spring security => USE this config for it
@EnableWebSecurity
public class SecurityConfig  {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    private static final String[] WHITE_LIST_URL = { "/api/v1/auth/**", "/v2/api-docs", "/v3/api-docs",
            "/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
            "/configuration/security", "/swagger-ui/**", "/webjars/**", "/swagger-ui.html", "/api/auth/**",
            "/api/test/**", "/authenticate",
            "register","login","loginn","verify-account",
            "categories/**","products/**","promotions/**","/upload/**","/download/**","/payment/**",
            "regenerate-otp","forget-password","verify-otp","reset-password",
            "/actuator/swagger-ui/**","/actuator/openapi"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        return http
                // Disable CSRF => it's mean that user can use all method(GET,PUT,POST,DELETE,...)
                .csrf(customizer-> customizer.disable())

                // Thiet lap uy quyen doi voi tat ca request -> 403 Forbidden
                .authorizeHttpRequests(request->request

                        // Ngoại trừ 2 url này không cần xac thuc
                        .requestMatchers(WHITE_LIST_URL).permitAll()
                        .anyRequest().authenticated())

                // Set up form login as the default of Spring Security
                .formLogin(Customizer.withDefaults())

                //Set up for API on POSTMAN
                .httpBasic(Customizer.withDefaults())

                // Set up for SessionID will renew after change(the default is not, after login, the SessionID will be one and only)
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authenticationProvider(authenticationProvider())
                // Mot trong nhung Filter de authenticate User is UserPasswordAuthen. Add filtet jwtFilter truoc UPA filter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();



    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder(12 ));
        authProvider.setUserDetailsService(userDetailsService);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
