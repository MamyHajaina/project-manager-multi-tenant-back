// com.back.multitenantback.config.SecurityConfig
package com.back.projectmanagermultitenantback.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1) CORS AVANT tout
                .cors(Customizer.withDefaults())
                // 2) API → stateless & pas de CSRF (tokens JWT)
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 3) Règles d'accès
                .authorizeHttpRequests(auth -> auth
                        // preflight
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // Swagger/OpenAPI public (ajuste selon tes chemins)
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        // endpoints publics (login/register si tu en as)
                        .requestMatchers("/auth/**").permitAll()
                        // le reste nécessite d'être authentifié
                        .anyRequest().authenticated()
                )
                // 4) Exceptions propres (401 JSON au lieu de redirection)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((req, res, e) -> {
                            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            res.setContentType("application/json");
                            res.getWriter().write("{\"error\":\"Unauthorized\"}");
                        })
                )
                // 5) Headers de sécu par défaut OK (X-Frame-Options, etc.)
                .headers(h -> h.defaultsDisabled().frameOptions(frame -> frame.deny()).xssProtection(Customizer.withDefaults()))
                // 6) Ton filtre JWT AVANT UsernamePasswordAuthFilter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        // Si tu dois envoyer des cookies HttpOnly, mets true et évite '*'
        cfg.setAllowCredentials(false); // pour Bearer token, false suffit

        // Origines autorisées : ton poste Linux + localhost
        cfg.setAllowedOriginPatterns(List.of(
                "http://192.168.88.21:4200",
                "http://localhost:4200",
                "https://project-manager-multi-tenant.netlify.app",
                "https://project-manager-mutli-tenant-front.onrender.com"
        ));
        // Méthodes autorisées
        cfg.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        // Headers entrants autorisés
        cfg.setAllowedHeaders(List.of(
                "Authorization",
                "Content-Type",
                "X-Requested-With",
                "Accept",
                "Origin"
        ));
        // Headers exposés côté navigateur (facultatif)
        cfg.setExposedHeaders(List.of(
                "Authorization",
                "Cache-Control",
                "Content-Type"
        ));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }
}
