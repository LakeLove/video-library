package com.cashmovie.movielibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableJpaRepositories("com.cashmovie.movielibrary.repositories")
public class MovielibraryApplication {
	
		public static void main(String[] args) {
			SpringApplication.run(MovielibraryApplication.class, args);
		}
}