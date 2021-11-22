package de.gkvsv.fhir.ta7.model.profiles;
// de.optica.erezept.datenlieferung.ta7.

import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import de.gkvsv.fhir.ta7.model.util.TA7Factory;
import de.gkvsv.fhir.ta7.validation.TA7FhirValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * created by mmbarek on 16.11.2021.
 */
class RezeptBundleTest {

    static final FhirContext fhirContext = FhirContext.forR4();
    static IParser parser;
    static TA7FhirValidator ta7FhirValidator;

    @BeforeAll
    static void setUp() {
        parser = fhirContext.newXmlParser();
        parser.setPrettyPrint(true);
        ta7FhirValidator = new TA7FhirValidator(fhirContext);
    }

    @Test
    void testCreate() {
        RezeptBundle rezeptBundle = new RezeptBundle();

        rezeptBundle.getVerordnungsdaten().setContentAsBase64("VmVyb3JkbnVuZ3NkYXRlbiBCZWlzcGllbCBFcnN0ZWxsdW5nIEJlaXNwaWVsIGVBYnJlY2hudW5nc2RhdGVu");
        rezeptBundle.getQuittungsdaten().setContentAsBase64("VmVyb3JkbnVuZ3NkYXRlbiBCZWlzcGllbCBFcnN0ZWxsdW5nIEJlaXNwaWVsIGVBYnJlY2hudW5nc2RhdGVu");
        rezeptBundle.getAbgabedaten().setContentAsBase64("ZUFiZ2FiZWRhdGVuIEJlaXNwaWVsIEVyc3RlbGx1bmcgQmVpc3BpZWwgZUFicmVjaG51bmdzZGF0ZW4=");

        final EAbrechnungsdaten eAbrechnungsdaten = TA7Factory.createEAbrechnungsdaten();
        rezeptBundle.getAbrechungsdaten().setEAbrechnungsdaten(eAbrechnungsdaten);

        final String s = parser.encodeResourceToString(rezeptBundle);
        System.out.println(s);

        final boolean validationResult = ta7FhirValidator.validateResource(rezeptBundle);
        assertTrue(validationResult);
    }
}
