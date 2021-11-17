package de.gkvsv.fhir.ta7.model.profiles;

import static org.junit.jupiter.api.Assertions.*;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import org.hl7.fhir.r4.model.Bundle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * created by mmbarek on 17.11.2021.
 */
class SammelrechnungListTest {

    FhirContext fhirContext = FhirContext.forR4();
    IParser parser = fhirContext.newXmlParser();

    @BeforeEach
    void setUp() {
        parser.setPrettyPrint(true);
    }

    @Test
    void testCreate() {
        SammelrechnungList list = new SammelrechnungList();
        list.addReference("Bundle/396c8baa-f2b5-4997-880d-02505aefa7db");

        final String s = parser.encodeResourceToString(list);
        System.out.println(s);
    }
}
