package com.back.projectmanagermultitenantback.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity // dans une @Configuration
@Configuration
class MethodSecurityConfig {}