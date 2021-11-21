package de.gkvsv.fhir.ta7;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import de.gkvsv.fhir.ta7.validation.TA7FhirValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FhirApplicationTests {

    @Autowired
    TA7FhirValidator ta7FhirValidator;

    @Test
    void contextLoads() {
        assertNotNull(ta7FhirValidator);
    }

}
