package de.gkvsv.fhir.ta7.model.profiles;
// de.optica.erezept.datenlieferung.ta7.

import static org.junit.jupiter.api.Assertions.*;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import de.gkvsv.fhir.ta7.model.util.TA7Factory;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * created by mmbarek on 16.11.2021.
 */
class RezeptBundleTest {

    FhirContext fhirContext = FhirContext.forR4();
    IParser parser = fhirContext.newXmlParser();

    @BeforeEach
    void setUp() {
        parser.setPrettyPrint(true);
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
    }
}
