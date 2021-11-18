package de.gkvsv.fhir.ta7.model.profiles;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.api.TemporalPrecisionEnum;
import ca.uhn.fhir.parser.IParser;
import de.gkvsv.fhir.ta7.config.Configuration;
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
        TA7Rechnung ta7Rechnung = new TA7Rechnung();
        ta7Rechnung.setAbrechnungszeitraum(new Date());

        // wird mit eine echte Refrenz ersetzt
        String rezepBundleReference = UUID.randomUUID().toString();
        String rezepBundleReference2 = UUID.randomUUID().toString();

        ta7Rechnung.addRefrenceRezeptBundle(rezepBundleReference)
            .setSammelrechnungsnummer("1234567890000000")
            .setRechnungsart(Rechnungsart.ABRECHN_UEBER_AZ_DER_APO_UND_ZAHLUNG_UEBER_APO_IK_DURCH_KRA)
            .setKostentraegerIK("123456789")
            .setRechnungsdatum(new Date())
            .addRefrenceRezeptBundle(rezepBundleReference2);

        // Sammel Bundle erstellen
        Bundle bundle = new Bundle();
        // Entry für die eAbrechnung erstellen
        BundleEntryComponent entryComponent = new BundleEntryComponent();

        BundleLinkComponent link = new BundleLinkComponent();
        link.setRelation("item");
        link.setUrl(TA7Rechnung.LINK);

        // fill entry
        entryComponent.setLink(List.of(link));

        entryComponent.setFullUrl(Configuration.URN_URL_PREFIX + ta7Rechnung.getId());
        entryComponent.setResource(ta7Rechnung);

        // add entry to bundle
        bundle.addEntry(entryComponent);

        // parse bundle to xml
        final String s = parser.encodeResourceToString(bundle);
        System.out.println(s);
    }
}
