package com.example.HospitaManagmentSystemDemo.config;

import com.example.HospitaManagmentSystemDemo.security.CurrentUserProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaConfig {

    private final CurrentUserProvider currentUserProvider;

    public JpaConfig(CurrentUserProvider currentUserProvider) {
        this.currentUserProvider = currentUserProvider;
    }

    @Bean
    public AuditorAware<Long> auditorProvider() {
        return currentUserProvider::getCurrentUserId;
    }
}
