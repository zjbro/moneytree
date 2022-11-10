package vttp.caf.moneytree;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MoneytreeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneytreeApplication.class, args);
	}

}
