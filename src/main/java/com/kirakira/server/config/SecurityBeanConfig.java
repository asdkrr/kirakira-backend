package com.kirakira.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 密码加密器。BCrypt 的特点：
 * - 每次加密同一串密码，结果都不同（自带随机盐）
 * - 只能"验证"，不能"解密"：encoder.matches(明文, 哈希)
 */
@Configuration
public class SecurityBeanConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
