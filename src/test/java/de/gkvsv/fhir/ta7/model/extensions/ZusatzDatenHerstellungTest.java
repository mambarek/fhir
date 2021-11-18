package de.gkvsv.fhir.ta7.model.extensions;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import de.gkvsv.fhir.ta7.config.Configuration;
import de.gkvsv.fhir.ta7.model.profiles.EAbrechnungsdaten;
import de.gkvsv.fhir.ta7.model.util.TA7Factory;
import java.util.List;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;
import org.hl7.fhir.r4.model.Bundle.BundleLinkComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * created by mmbarek on 12.11.2021.
 */
class ZusatzDatenHerstellungTest {

    FhirContext fhirContext = FhirContext.forR4();
    IParser parser = fhirContext.newXmlParser();

    @BeforeEach
    void setUp() {
        //fhirContext.registerCustomType(ZusatzDatenHerstellung.class);
        parser.setPrettyPrint(true);
    }


    @Test
    void testCreate() {
        EAbrechnungsdaten eAbrechnungsdaten = TA7Factory.createEAbrechnungsdaten();

        // Sammel Bundle erstellen
        Bundle bundle = new Bundle();

        // Entry für die eAbrechnung erstellen
        BundleEntryComponent entryComponent = new BundleEntryComponent();

        BundleLinkComponent link = new BundleLinkComponent();
        link.setRelation("item");
        link.setUrl(EAbrechnungsdaten.LINK);

        // fill entry
        entryComponent.setLink(List.of(link));
        entryComponent.setFullUrl(Configuration.URN_URL_PREFIX + eAbrechnungsdaten.getId());
        entryComponent.setResource(eAbrechnungsdaten);

        // add entry to bundle
        bundle.addEntry(entryComponent);

        // parse bundle to xml
        final String s = parser.encodeResourceToString(bundle);
        System.out.println(s);
    }
}
