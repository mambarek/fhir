package de.gkvsv.fhir.ta7.validation;

import static org.junit.jupiter.api.Assertions.*;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * created by mmbarek on 18.11.2021.
 */
class TA7FhirValidatorTest {

    FhirContext fhirContext = FhirContext.forR4();
    TA7FhirValidator ta7FhirValidator;

    @BeforeEach
    void setUp() {
        ta7FhirValidator = new TA7FhirValidator(fhirContext);
    }

    @Test
    void testCreateAndInitValidator() {
        assertNotNull(ta7FhirValidator);
    }

    @Test
    void validateRealBundle() throws IOException {
        final IParser parser = fhirContext.newXmlParser();
        Path path = Paths.get("src/test/resources/ref/TK_Split_Teil1.xml");
        final String resourceXML = Files.readString(path, StandardCharsets.UTF_8);
        final IBaseResource resource = parser.parseResource(resourceXML);
        assertNotNull(resource);

        final boolean validationResult = ta7FhirValidator.validateResource(resource);
        assertTrue(validationResult);
    }

    @Test
    void validateRealTa7Bundle() throws IOException {
        final IParser parser = fhirContext.newXmlParser();
        Path path = Paths.get("src/test/resources/ref/ta7Bundle.xml");
        final String resourceXML = Files.readString(path, StandardCharsets.UTF_8);
        final IBaseResource resource = parser.parseResource(resourceXML);
        assertNotNull(resource);

        final boolean validationResult = ta7FhirValidator.validateResource(resource);
        assertTrue(validationResult);
    }

    @Test
    void validateRealInvoice() throws IOException {
        final IParser parser = fhirContext.newXmlParser();
        Path path = Paths.get("src/test/resources/ref/ta7Rechnung.xml");
        final String resourceXML = Files.readString(path, StandardCharsets.UTF_8);
        final IBaseResource resource = parser.parseResource(resourceXML);
        assertNotNull(resource);

        final boolean validationResult = ta7FhirValidator.validateResource(resource);
        assertTrue(validationResult);
    }
}
