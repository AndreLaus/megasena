package info.andrefelipelaus.megasenabackend.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import info.andrefelipelaus.megasenabackend.controller.service.LoadFileServices;

/**
 * 
 * @author André Felipe Laus
 *
 * Classe principal para configurações
 *
 */

@Configuration
@ComponentScan(basePackageClasses = {LoadFileServices.class, JpaConfiguration.class})
@EnableAsync
public class ApplicationConfiguration {


	@Bean
	public Executor asyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(5);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("Megasena-");
		executor.initialize();
		return executor;
	}
	
	@Bean
	public LoadFileServices loadFileService() {
		return LoadFileServices.getInstance(); 
	}
}
