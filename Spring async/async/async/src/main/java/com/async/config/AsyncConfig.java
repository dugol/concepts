package com.async.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "asyncExecutor")
    public Executor asyncExcecutor(){

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3); // Minimo de hilos que se van a utilizar
        executor.setMaxPoolSize(6); // Maximo de hilos
        executor.setQueueCapacity(100); // Cantidad de hilos que estan creados pero a la espera de ser procesados
        executor.setThreadNamePrefix("AsyncThread-"); // Nombre al hilo
        executor.initialize();
        return executor;
    }
}
