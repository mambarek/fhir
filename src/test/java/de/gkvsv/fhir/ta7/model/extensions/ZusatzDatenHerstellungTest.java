package de.gkvsv.fhir.ta7.model.extensions;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import de.gkvsv.fhir.ta7.model.enums.ImportKennzeichenEnum.ImportKennzeichen;
import de.gkvsv.fhir.ta7.model.enums.LeistungserbringerSitzEnum.LeistungserbringerSitz;
import de.gkvsv.fhir.ta7.model.enums.LeistungserbringertypEnum.Leistungserbringertyp;
import de.gkvsv.fhir.ta7.model.enums.PositionstypEnum.Positionstyp;
import de.gkvsv.fhir.ta7.model.enums.ZuAbschlagKeyEnum.ZuAbschlagKey;
import de.gkvsv.fhir.ta7.model.extensions.ZusatzDatenHerstellung.Einheit;
import de.gkvsv.fhir.ta7.model.profiles.EAbrechnungsdaten;
import java.util.List;
import java.util.UUID;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;
import org.hl7.fhir.r4.model.Bundle.BundleLinkComponent;
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
        //fhirContext.registerCustomType(ZusatzDatenHerstellung.class);
        parser.setPrettyPrint(true);
    }

    @Test
    void testCreate() {

        // Rechnung eAbrechnungsdaten erstellen
        String rechnungId = UUID.randomUUID().toString();
        EAbrechnungsdaten invoice = new EAbrechnungsdaten();
        invoice.setId(rechnungId);
        invoice.setStatus(InvoiceStatus.ISSUED);

        // ZusatzDatenHerstellung
        ZusatzDatenHerstellung zusatzDatenHerstellung = new ZusatzDatenHerstellung();
        zusatzDatenHerstellung.setZaehlerHerstellung(1);

        // eine Einheit erstellen
        Einheit einheit = new Einheit();
        einheit.setZaehlerEinheit(1);

        zusatzDatenHerstellung.getEinheiten().add(einheit);

        invoice.setZusatzDatenHerstellung(zusatzDatenHerstellung);
        invoice.setIrrlaeufer(true);

        // Abrechungsposition erstellen
        Abrechnungsposition abrechnungsposition = new Abrechnungsposition();
        abrechnungsposition.setZaehlerAbrechnungsposition(1);

        abrechnungsposition.getZuAbschlaegeZusatzdaten().setZuAbschlagCode(ZuAbschlagKey.APOTHEKEN_ABSCHLAG);
        abrechnungsposition.getZuAbschlaegeZusatzdaten().setZuAbschlagBetrag(new Money().setValue(100.59));
        abrechnungsposition.getZuAbschlaegeZusatzdaten().setZuAbschlagKennzeichen(
            InvoicePriceComponentType.DEDUCTION);

        einheit.getAbrechnungspositionen().add(abrechnungsposition);

        // Zweite Abrechnungsposition erstellen
        Abrechnungsposition pos2 = new Abrechnungsposition();
        pos2.setZaehlerAbrechnungsposition(2);

        pos2.getZuAbschlaegeZusatzdaten().setZuAbschlagCode(ZuAbschlagKey.GESETZLICHER_HERSTELLER_ABSCHLAG_P130a_A1_UND_A1a);
        pos2.getZuAbschlaegeZusatzdaten().setZuAbschlagBetrag(-3102.57);
        pos2.getZuAbschlaegeZusatzdaten().setZuAbschlagKennzeichen(
            InvoicePriceComponentType.SURCHARGE);

        // TODO einheit verstecken und addPosition zu eAbrechungsdaten hinzufügen
        einheit.getAbrechnungspositionen().add(pos2);

        // Identifier
        invoice.setPrescriptionId("160.100.000.000.021.76");
        invoice.setBelegnummer("2105000000713456789");

        // issuer
        invoice.setLeistungserbringerSitz(LeistungserbringerSitz.AUSLAND);
        invoice.setLeistungserbringerTyp(Leistungserbringertyp.KRANKENHAUSAPOTHEKEN);
        invoice.setApothekenIK("90456789");

        // Lineitem
        InvoiceLineItem lineItem = new InvoiceLineItem();
        lineItem.setPositionstyp(Positionstyp.UEBRIGE_POSITION);
        lineItem.setVatValue(56.89);
        lineItem.setImportKennzeichen(ImportKennzeichen.KEIN_BEZUGS_ARZNEI_MITTEL);
        lineItem.setSequence(1);
        // Zu- und Abschläge
        lineItem.setZuAbschlagKennzeichen(InvoicePriceComponentType.SURCHARGE);
        lineItem.setZuAbschlagCode(ZuAbschlagKey.GESETZLICHER_HERSTELLER_ABSCHLAG_P130a_A1_UND_A1a);
        lineItem.setZuAbschlagBetrag(-1.77);

        invoice.addLineItem(lineItem);

        // TODO bundle erben und eine Sammelrechnung erstell mit addRechnuung(). statt alles hier platt zu erledigen
        // Sammel Bundle erstellen
        Bundle bundle = new Bundle();

        // Entry für die eAbrechnung erstellen
        BundleEntryComponent entryComponent = new BundleEntryComponent();

        BundleLinkComponent link = new BundleLinkComponent();
        link.setRelation("item");
        link.setUrl("https://fhir.gkvsv.de/StructureDefinition/GKVSV_PR_ERP_eAbrechnungsdaten");

        // fill entry
        entryComponent.setLink(List.of(link));
        entryComponent.setFullUrl("urn:uuid:" + rechnungId);
        entryComponent.setResource(invoice);

        // add entry to bundle
        bundle.addEntry(entryComponent);

        // parse bundle to xml
        final String s = parser.encodeResourceToString(bundle);
        System.out.println(s);
    }
}
