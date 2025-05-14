package androidapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class AndroidAppApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AndroidAppApiApplication.class, args);
	}
}
