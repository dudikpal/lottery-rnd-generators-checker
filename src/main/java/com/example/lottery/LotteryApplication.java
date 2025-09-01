package com.example.lottery;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class LotteryApplication {

	public static void main(String[] args) {
		log.info("Starting LotteryApplication...");
		SpringApplication.run(LotteryApplication.class, args);
		log.info("LotteryApplication started successfully.");
	}

	@Bean
	public ModelMapper modelMapper() {
		log.debug("ModelMapper bean initialized");
		return new ModelMapper();
	}

}
