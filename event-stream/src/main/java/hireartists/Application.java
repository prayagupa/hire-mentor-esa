package hireartists;

import hireartists.producer.Producer;
import hireartists.producer.ProducerA;
import hireartists.producer.ProducerB;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@SpringBootApplication
//// same as @Configuration @EnableAutoConfiguration @ComponentScan
public class Application {

    @Bean
    public Producer producerA() {
        return new ProducerA();
    }

    @Bean
    public Producer producerB() {
        return new ProducerB();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
