package de.gkvsv.fhir.ta7.model.profiles;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * created by mmbarek on 17.11.2021.
 */
class SammelrechnungCompositionTest {

    FhirContext fhirContext = FhirContext.forR4();
    IParser parser = fhirContext.newXmlParser();

    @BeforeEach
    void setUp() {
        parser.setPrettyPrint(true);
    }

    @Test
    void testCreate() {
        SammelrechnungComposition composition = new SammelrechnungComposition();
        composition.setEmpfaengerIK("123456789")
            .setKostentraegerIK("987654321")
            .setAbrechnungszeitraum(new Date())
            .setAbsenderIK("321456789")
            .addSammelrechnungSection("8b83ed44-2438-49aa-8d80-895c00ae9883")
            .addRechnungenSection("a9423218-80b5-4e49-9b4e-89dcb3536bc2");

        // parse composition to xml
        final String s = parser.encodeResourceToString(composition);
        System.out.println(s);
    }
}
