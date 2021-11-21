package de.gkvsv.fhir.ta7.model.profiles;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import de.gkvsv.fhir.ta7.model.enums.RechnungsartEnum.Rechnungsart;
import de.gkvsv.fhir.ta7.validation.TA7FhirValidator;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * created by mmbarek on 16.11.2021.
 */
class TA7RechnungTest {

    static FhirContext fhirContext = FhirContext.forR4();
    static IParser parser = fhirContext.newXmlParser();
    static TA7FhirValidator ta7FhirValidator;

    @BeforeAll
    static void setUp() {
        fhirContext.registerCustomType(TA7Rechnung.class);
        parser.setPrettyPrint(true);
        ta7FhirValidator = new TA7FhirValidator(fhirContext);
    }

    @Test
    void testCreate() {
        TA7Rechnung ta7Rechnung = new TA7Rechnung();
        ta7Rechnung.setAbrechnungszeitraum(new Date());

        // wird mit eine echte Refrenz ersetzt
        String rezepBundleReference = UUID.randomUUID().toString();
        String rezepBundleReference2 = UUID.randomUUID().toString();

        ta7Rechnung.addRefrenceRezeptBundle(rezepBundleReference)
            .setSammelrechnungsnummer("1234567890000000")
            .setRechnungsart(Rechnungsart.APO_ABRECHNUNG_UND_ZAHLUNG_UEBER_APO_IK_DURCH_KRA)
            .setKostentraegerIK("123456789")
            .setRechnungsdatum(new Date())
            .addRefrenceRezeptBundle(rezepBundleReference2);

        final String s = parser.encodeResourceToString(ta7Rechnung);
        //System.out.println(s);

        final boolean b = ta7FhirValidator.validateResource(ta7Rechnung);
        System.out.println("Validation was successful: " + b);
    }
}
