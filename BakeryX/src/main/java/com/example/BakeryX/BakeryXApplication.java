package com.example.BakeryX;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class BakeryXApplication {

	public static void main(String[] args) {
		SpringApplication.run(BakeryXApplication.class, args);

		try {
			Runtime.getRuntime().exec("cmd /c start http://localhost:8080/login.html");
		} catch (IOException e) {
			System.err.println("Failed to open browser: " + e.getMessage());
		}


	}
}
