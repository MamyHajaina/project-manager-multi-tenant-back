// com.back.multitenantback.Services.JwtService
package com.back.projectmanagermultitenantback.Services;

import com.back.projectmanagermultitenantback.dto.OrganizationMemberDto;
import com.back.projectmanagermultitenantback.dto.UserDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.*;

@Service
public class JwtService {

    @Value("${app.jwt.secret:change-me-super-secret-256bits}")
    private String secret; // soit Base64, soit texte brut (utf-8). Recommandé: Base64 32 octets.

    @Value("${app.jwt.ttl-seconds:12}")
    private int ttlSeconds;

    private Key key;
    private JwtParser parser;

    @PostConstruct
    void init() {
        byte[] keyBytes = resolveKeyBytes(secret);

        // Vérifie longueur >= 32 octets (256 bits)
        if (keyBytes.length < 32) {
            throw new IllegalStateException(
                    "app.jwt.secret is too short: need >= 32 bytes (256 bits). " +
                            "Provide a Base64 32-byte key, e.g. `openssl rand -base64 32`."
            );
        }

        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.parser = Jwts.parserBuilder().setSigningKey(this.key).build();
    }

    private static byte[] resolveKeyBytes(String s) {
        if (s == null || s.isBlank() || s.startsWith("change-me")) {
            // DEV fallback: génère une clé HS256 et loggue la Base64 pour configurer ta prod.
            Key tmp = Keys.secretKeyFor(SignatureAlgorithm.HS256);
            System.out.println("DEV JWT SECRET (Base64) — set app.jwt.secret to this value:");
            System.out.println(Encoders.BASE64.encode(tmp.getEncoded()));
            return tmp.getEncoded();
        }
        // Essaye d'abord Base64 (recommandé)
        try {
            byte[] decoded = Decoders.BASE64.decode(s);
            if (decoded.length >= 32) return decoded;
            // si Base64 décodé mais <32, on continue pour tenter utf-8 (donnera probablement aussi <32)
        } catch (Exception ignored) {}
        // Sinon considère comme utf-8 brut
        return s.getBytes(StandardCharsets.UTF_8);
    }

    public Jws<Claims> parse(String token) {
        return parser.parseClaimsJws(token);
    }

    public String generateToken(UserDto user) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(ttlSeconds);

        // role = String unique dans ton UserDto
        String rawRole = user.getRole().toString();
        List<String> roles = (rawRole == null || rawRole.isBlank())
                ? List.of()
                : List.of(rawRole.startsWith("ROLE_") ? rawRole : "ROLE_" + rawRole.toUpperCase());

        // orgIds via OrganizationMemberDto.organization.id
        List<Long> orgIds = Optional.ofNullable(user.getOrganization())
                .orElseGet(List::of)
                .stream()
                .map(OrganizationMemberDto::getOrganization)
                .filter(Objects::nonNull)
                .map(org -> org.getId())
                .filter(Objects::nonNull)
                .toList();

        Map<String, Object> claims = new HashMap<>();
        claims.put("uid", user.getId());
        claims.put("email", user.getEmail());
        claims.put("roles", roles);
        claims.put("orgs", orgIds);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(exp))
                .addClaims(claims)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String toSetCookieHeader(String token) {
        com.back.projectmanagermultitenantback.security.TokenCookieUtil
                .buildAccessCookie(token, this.ttlSeconds, true);
        Cookie c = new Cookie("JWT_ACCESS_TOKEN", token);
        StringBuilder sb = new StringBuilder();
        sb.append(c.getName()).append("=").append(c.getValue()).append("; Path=").append(c.getPath());
        if (c.getMaxAge() >= 0) sb.append("; Max-Age=").append(c.getMaxAge());
        if (c.isHttpOnly()) sb.append("; HttpOnly");
        if (c.getSecure()) sb.append("; Secure");
        sb.append("; SameSite=Lax");
        return sb.toString();
    }
}
