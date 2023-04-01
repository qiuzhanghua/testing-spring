package org.springframework.samples.petclinic.sfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("base-test")
@Configuration
public class LaurelConfig {
    @Bean
    WordProducer laurelWordProducer() {
        return new LaurelWordProducer();
    }
}
