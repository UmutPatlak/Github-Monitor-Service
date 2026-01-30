package com.example.monitor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class GitHubMonitorApplication {
	public static void main(String[] args) {
		SpringApplication.run(GitHubMonitorApplication.class, args);
	}
}