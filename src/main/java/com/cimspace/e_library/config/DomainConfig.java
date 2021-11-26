package com.cimspace.e_library.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("com.cimspace.e_library.domain")
@EnableJpaRepositories("com.cimspace.e_library.repos")
@EnableTransactionManagement
public class DomainConfig {
}
