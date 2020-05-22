package pl.kowalczuk.springmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class SpringMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMvcApplication.class, args);
    }

}
