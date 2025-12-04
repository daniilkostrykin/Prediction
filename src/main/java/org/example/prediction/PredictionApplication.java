
package org.example.prediction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class PredictionApplication {

    public static void main(String[] args) {
        SpringApplication.run(PredictionApplication.class, args);
    }
}