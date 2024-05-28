package ru.kors.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.concurrent.DelegatingSecurityContextCallable;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

@RestController
public class HelloController {
    private final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/bye")
    public String bye() throws Exception {
        Callable<String> task = () -> {
            var context = SecurityContextHolder.getContext();
            var auth = context.getAuthentication();
            return auth.getName();
        };

        var exec = new DelegatingSecurityContextExecutorService(Executors.newCachedThreadPool());

        try {
            return "Bye, " + exec.submit(task).get() + "!";
        } finally {
            exec.shutdown();
        }
    }

    @GetMapping("/hello")
    public String hello(Authentication auth) {
        return "Hello, " + auth.getName() + "!";
    }

    @GetMapping("/async")
    @Async
    public void async() {
        var context = SecurityContextHolder.getContext();
        var auth = context.getAuthentication();
        logger.info("Principal name={}, authorities={}", auth.getName(), auth.getAuthorities());
    }
}
