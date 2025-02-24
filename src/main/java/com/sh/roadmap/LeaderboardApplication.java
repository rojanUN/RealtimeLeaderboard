package com.sh.roadmap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.sh.roadmap")
public class LeaderboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeaderboardApplication.class, args);
	}

}
