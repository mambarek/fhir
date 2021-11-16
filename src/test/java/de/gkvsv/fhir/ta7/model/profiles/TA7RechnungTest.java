package de.gkvsv.fhir.ta7.model.profiles;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.api.TemporalPrecisionEnum;
import ca.uhn.fhir.parser.IParser;
import de.gkvsv.fhir.ta7.model.enums.RechnungsartEnum.Rechnungsart;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;
import org.hl7.fhir.r4.model.Bundle.BundleLinkComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * created by mmbarek on 16.11.2021.
 */
class TA7RechnungTest {

    FhirContext fhirContext = FhirContext.forR4();
    IParser parser = fhirContext.newXmlParser();

    @BeforeEach
    void setUp() {
        parser.setPrettyPrint(true);
    }

    @Test
    void testCreate() {
        String rechnungId = UUID.randomUUID().toString();
        TA7Rechnung ta7Rechnung = new TA7Rechnung();
        ta7Rechnung.setId(rechnungId);
        ta7Rechnung.setAbrechnungszeitraum(new Date());
        ta7Rechnung.getAbrechnungszeitraum().setPrecision(TemporalPrecisionEnum.DAY);

        ta7Rechnung.setRefrenceRezeptBundle(UUID.randomUUID().toString())
            .setSammelrechnungsnummer("1234567890000000")
            .setRechnungsart(Rechnungsart.ABRECHN_UEBER_AZ_DER_APO_UND_ZAHLUNG_UEBER_APO_IK_DURCH_KRA)
            .setKostentraegerIK("123456789")
            .setRechnungsdatum(new Date());

        // Sammel Bundle erstellen
        Bundle bundle = new Bundle();
        // Entry für die eAbrechnung erstellen
        BundleEntryComponent entryComponent = new BundleEntryComponent();

        BundleLinkComponent link = new BundleLinkComponent();
        link.setRelation("item");
        link.setUrl(TA7Rechnung.LINK);

        // fill entry
        entryComponent.setLink(List.of(link));

        entryComponent.setFullUrl("urn:uuid:" + rechnungId);
        entryComponent.setResource(ta7Rechnung);

        // add entry to bundle
        bundle.addEntry(entryComponent);

        // parse bundle to xml
        final String s = parser.encodeResourceToString(bundle);
        System.out.println(s);
    }
}
