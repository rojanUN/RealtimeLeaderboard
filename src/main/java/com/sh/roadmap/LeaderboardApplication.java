	package com.sh.roadmap;

	import org.springframework.boot.SpringApplication;
	import org.springframework.boot.autoconfigure.SpringBootApplication;
	import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

	@SpringBootApplication(scanBasePackages = "com.sh.roadmap")
	@EnableJpaAuditing(auditorAwareRef = "auditorAware")
	public class LeaderboardApplication {

		public static void main(String[] args) {
			SpringApplication.run(LeaderboardApplication.class, args);
		}

	}
