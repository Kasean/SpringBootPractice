package com.kasean.spring.boot.homework.first;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

public class CommandLineRunnerImpl implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger("CommandLineRunner");

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Hello world!!!");
    }
}
