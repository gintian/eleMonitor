package com.bairock.eleMonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableAsync
public class EleMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(EleMonitorApplication.class, args);
	}
	
//	@Bean
//	public TaskExecutor getAsyncExecutor() {
//	    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//	    executor.setCorePoolSize(10);
//	    executor.setMaxPoolSize(1000);
//	    return executor;
//	}
	
//	@Bean
//    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//        return args -> {
//            System.out.println("Let's inspect the beans provided by Spring Boot:");
//            String[] beanNames = ctx.getBeanDefinitionNames();
//            Arrays.sort(beanNames);
//            for (String beanName : beanNames) {
//                System.out.println(beanName);
//            }
//        };
//    }
}
