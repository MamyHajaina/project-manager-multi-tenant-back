// com.back.multitenantback.security.TokenCookieUtil
package com.back.projectmanagermultitenantback.security;

import jakarta.servlet.http.Cookie;

public final class TokenCookieUtil {
    private TokenCookieUtil() {}

    public static final String ACCESS_COOKIE = "ACCESS_TOKEN";

    public static Cookie buildAccessCookie(String token, int ttlSeconds, boolean secure) {
        Cookie c = new Cookie(ACCESS_COOKIE, token);
        c.setPath("/");
        c.setHttpOnly(true);
        c.setSecure(secure);            // true en prod (HTTPS)
        c.setMaxAge(ttlSeconds);        // même durée que le JWT
        c.setAttribute("SameSite", "Lax");
        return c;
    }

    public static Cookie deleteAccessCookie(boolean secure) {
        Cookie c = new Cookie(ACCESS_COOKIE, "");
        c.setPath("/");
        c.setHttpOnly(true);
        c.setSecure(secure);
        c.setMaxAge(0);
        c.setAttribute("SameSite", "Lax");
        return c;
    }
}
