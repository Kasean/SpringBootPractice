package com.kasean.spring.boot.homework.first;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("task1")
public class BaseConfiguration {

    @Bean
    public CommandLineRunnerImpl initCommandLineRunner(){
        return new CommandLineRunnerImpl();
    }
}
