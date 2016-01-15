package hireartists;

import hireartists.producer.Producer;
import hireartists.producer.ProducerA;
import hireartists.producer.ProducerB;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by prayagupd
 * on 12/8/15.
 */
@Configuration
@Profile("productionConfig")
public class ApplicationConfig {
    @Bean
    public Producer producerA() {
        return new ProducerA();
    }

    @Bean
    public Producer producerB() {
        return new ProducerB();
    }
}
