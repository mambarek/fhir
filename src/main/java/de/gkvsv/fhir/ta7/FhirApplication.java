package de.gkvsv.fhir.ta7;

import ca.uhn.fhir.context.FhirContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FhirApplication {

    public static void main(String[] args) {
        SpringApplication.run(FhirApplication.class, args);
    }

    @Bean
    public FhirContext getFhirContext() {
        return FhirContext.forR4();
    }

}
