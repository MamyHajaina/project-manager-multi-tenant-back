package com.back.projectmanagermultitenantback.config;

import com.back.projectmanagermultitenantback.Services.JwtService;
import com.back.projectmanagermultitenantback.security.TokenCookieUtil;
import com.back.projectmanagermultitenantback.security.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String token = resolveToken(request);
        if (token == null) {
            chain.doFilter(request, response);
            return;
        }

        try {
            Claims claims = jwtService.parse(token).getBody();

            Long userId = toLong(claims.get("uid"));
            String email = Optional.ofNullable((String) claims.get("email")).orElse(claims.getSubject());

            List<String> roles = claimToStringList(claims.get("roles")); // ["ROLE_USER",...]
            var authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

            List<Long> orgIds = claimToLongList(claims.get("orgs"));     // [1,2,3]

            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserPrincipal principal = new UserPrincipal(userId, email, authorities, roles, orgIds);
                var authToken = new UsernamePasswordAuthenticationToken(principal, null, authorities);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (JwtException e) {
            // token invalide/expiré : on ne set pas le contexte; le EntryPoint gèrera
        }
        chain.doFilter(request, response);
    }

    // ... resolveToken, toLong et claimToStringList inchangés

    @SuppressWarnings("unchecked")
    private static List<Long> claimToLongList(Object claim) {
        if (claim == null) return List.of();
        if (claim instanceof List<?> list) {
            List<Long> out = new ArrayList<>(list.size());
            for (Object o : list) {
                if (o == null) continue;
                if (o instanceof Number n) { out.add(n.longValue()); continue; }
                if (o instanceof String s && !s.isBlank()) out.add(Long.parseLong(s.trim()));
            }
            return out;
        }
        // Ex: "1,2,3"
        return Arrays.stream(claim.toString().split(","))
                .map(String::trim).filter(s -> !s.isEmpty())
                .map(Long::parseLong)
                .toList();
    }

    private String resolveToken(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) return auth.substring(7);

        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if (TokenCookieUtil.ACCESS_COOKIE.equals(c.getName())) {
                    return c.getValue();
                }
            }
        }
        return null;
    }

    private static Long toLong(Object raw) {
        if (raw == null) return null;
        if (raw instanceof Long l) return l;
        if (raw instanceof Integer i) return i.longValue();
        if (raw instanceof String s) return Long.parseLong(s);
        throw new IllegalArgumentException("Unsupported uid type: " + raw.getClass());
    }

    @SuppressWarnings("unchecked")
    private static List<String> claimToStringList(Object claim) {
        if (claim == null) return List.of();
        if (claim instanceof List<?> list) return list.stream().map(Object::toString).collect(Collectors.toList());
        return Arrays.stream(claim.toString().split(",")).map(String::trim).filter(s -> !s.isEmpty()).toList();
    }
}
