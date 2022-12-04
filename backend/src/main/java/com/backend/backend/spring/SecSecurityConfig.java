package com.backend.backend.spring;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
// @ImportResource({ "classpath:webSecurityConfig.xml" })
//@EnableWebSecurity
@ComponentScan(basePackages = { "com.backend.backend.services" , "com.backend.backend.spring" })
@Configuration
public class SecSecurityConfig {

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }
}
