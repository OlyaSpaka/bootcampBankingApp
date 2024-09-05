package org.bootcamp.bootcampapp.config;

import org.bootcamp.bootcampapp.service.AccountDataService;
import org.bootcamp.bootcampapp.service.Operations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public AccountDataService inputOutputClass() {
        return new AccountDataService();
    }

    @Bean
    public Operations operations() {
        return new Operations();
    }

}
