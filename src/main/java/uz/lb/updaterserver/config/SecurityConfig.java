package uz.lb.updaterserver.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import uz.lb.updaterserver.components.JwtAuthenticationFilter;
import uz.lb.updaterserver.dto.ResultDTO;
import uz.lb.updaterserver.exception.AppForbiddenException;

import java.util.Arrays;


@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtTokenFilter;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    public static final String[] AUTH_WHITELIST = {

            // Swagger UI
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/swagger-ui",
            "/swagger-ui/index.html",
            "/swagger-resources/**",
            "/webjars/**",

            // OpenAPI docs
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/api-docs",
            "/api-docs/**",

            // Auth
            "/updater-server/auth",

            //registration
            "/user/reg"

    };

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        try {
            http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers(AUTH_WHITELIST).permitAll()
                                .anyRequest()
                                .authenticated();
                    })
                    .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)

                    .exceptionHandling(ex -> ex
                            .accessDeniedHandler((req, res, e) -> {
                                res.setStatus(HttpStatus.FORBIDDEN.value());
                                res.setContentType("application/json");
                                ResultDTO resultDTO = new ResultDTO().error("Sizda ushbu API uchun ruxsat yo'q");
                                res.getWriter().write(objectMapper.writeValueAsString(resultDTO));
                            })
                            .authenticationEntryPoint((req, res, e) -> {
                                res.setStatus(HttpStatus.UNAUTHORIZED.value());
                                res.setContentType("application/json");
                                ResultDTO resultDTO = new ResultDTO("SecurityConfig.securityFilterChain  -> exception", false, e.getMessage());
                                res.getWriter().write(objectMapper.writeValueAsString(resultDTO));
                            })
                    );


            http.csrf(AbstractHttpConfigurer::disable);

            http.cors(httpSecurityCorsConfigurer -> {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOriginPatterns(Arrays.asList("*"));
                configuration.setAllowedMethods(Arrays.asList("*"));
                configuration.setAllowedHeaders(Arrays.asList("*"));

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                httpSecurityCorsConfigurer.configurationSource(source);
            });

            return http.build();

        } catch (Exception e) {
            throw new AppForbiddenException("SecurityConfig.securityFilterChain(HttpSecurity http) => " + e.getMessage());
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

            @Override
            public String encode(CharSequence rawPassword) {
                return encoder.encode(rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encoder.matches(rawPassword, encodedPassword);
            }
        };
    }


}
