package de.gkvsv.fhir.ta7.model.extensions;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import de.gkvsv.fhir.ta7.model.enums.ZuAbschlagKeyEnum.ZuAbschlagKey;
import de.gkvsv.fhir.ta7.model.enums.ZuAbschlagKeyEnum.ZuAbschlagKeyFactory;
import de.gkvsv.fhir.ta7.model.extensions.ZusatzDatenHerstellung.Einheit;
import de.gkvsv.fhir.ta7.model.profiles.EAbrechnungsdaten;
import java.util.UUID;
import org.checkerframework.checker.units.qual.A;
import org.hl7.fhir.r4.model.BooleanType;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Extension;
import org.hl7.fhir.r4.model.Invoice.InvoicePriceComponentType;
import org.hl7.fhir.r4.model.Invoice.InvoiceStatus;
import org.hl7.fhir.r4.model.Money;
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
        fhirContext.registerCustomType(ZusatzDatenHerstellung_Extension.class);
        parser.setPrettyPrint(true);
    }

    @Test
    void testCreate() {
        ZusatzDatenHerstellung zusatzDatenHerstellung = new ZusatzDatenHerstellung();
        zusatzDatenHerstellung.setZaehlerHerstellung(1);

        Einheit einheit = new Einheit();
        einheit.setZaehlerEinheit(1);

        zusatzDatenHerstellung.getEinheiten().add(einheit);

        // Rechnung hinzufügen
        String rechnungId = UUID.randomUUID().toString();
        EAbrechnungsdaten invoice = new EAbrechnungsdaten();
        invoice.setId(rechnungId);
        invoice.setStatus(InvoiceStatus.ISSUED);


        invoice.setZusatzDatenHerstellung(zusatzDatenHerstellung);
        invoice.setIrrlaeufer(true);


        Abrechnungsposition abrechnungsposition = new Abrechnungsposition();
        abrechnungsposition.setZaehlerAbrechnungsposition(1);

        abrechnungsposition.getZuAbschlaegeZusatzdaten().setZuAbschlagCode(ZuAbschlagKey.APOTHEKEN_ABSCHLAG);
        abrechnungsposition.getZuAbschlaegeZusatzdaten().setZuAbschlagBetrag(new Money().setValue(100.59));
        abrechnungsposition.getZuAbschlaegeZusatzdaten().setZuAbschlagKennzeichen(
            InvoicePriceComponentType.DEDUCTION);

        CodeableConcept codeableConcept = new CodeableConcept();
        //codeableConcept.setCoding(ZuAbschlagKey.values())
        abrechnungsposition.getZuAbschlaegeZusatzdaten().setZuAbschlagCode2(ZuAbschlagKey.GESETZLICHER_HERSTELLER_ABSCHLAG_P130a_A1_UND_A1a);
        //abrechnungsposition.getZuAbschlaegeZusatzdaten().getZuAbschlagCode2().setCoding(ZuAbschlagKeyFactory.getCodeableConcept().getCoding().get(0));
        einheit.getAbrechnungspositionen().add(abrechnungsposition);

        Abrechnungsposition pos2 = new Abrechnungsposition();
        pos2.setZaehlerAbrechnungsposition(2);

        pos2.getZuAbschlaegeZusatzdaten().setZuAbschlagCode(ZuAbschlagKey.GESETZLICHER_HERSTELLER_ABSCHLAG_P130a_A1_UND_A1a);
        pos2.getZuAbschlaegeZusatzdaten().setZuAbschlagBetrag(new Money().setValue(3102.57));
        pos2.getZuAbschlaegeZusatzdaten().setZuAbschlagKennzeichen(
            InvoicePriceComponentType.SURCHARGE);

        einheit.getAbrechnungspositionen().add(pos2);

        Bundle bundle = new Bundle();
        BundleEntryComponent entryComponent = new BundleEntryComponent();
        entryComponent.setResource(invoice);

        entryComponent.setFullUrl("urn:uuid:" + rechnungId);

        bundle.addEntry(entryComponent);
        //BundleEntryComponent entryComponent = new BundleEntryComponent();
        //entryComponent.addExtension(zusatzDatenHerstellung);
        //final BundleEntryComponent entryComponent1 = bundle.addEntry();
        //entryComponent1.addExtension(zusatzDatenHerstellung);
        final String s = parser.encodeResourceToString(bundle);
        System.out.println(s);
    }
}
