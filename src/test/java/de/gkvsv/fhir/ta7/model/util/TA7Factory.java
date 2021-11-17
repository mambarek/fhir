package de.gkvsv.fhir.ta7.model.util;

import de.gkvsv.fhir.ta7.model.enums.ImportKennzeichenEnum.ImportKennzeichen;
import de.gkvsv.fhir.ta7.model.enums.LeistungserbringerSitzEnum.LeistungserbringerSitz;
import de.gkvsv.fhir.ta7.model.enums.LeistungserbringertypEnum.Leistungserbringertyp;
import de.gkvsv.fhir.ta7.model.enums.PositionstypEnum.Positionstyp;
import de.gkvsv.fhir.ta7.model.enums.ZuAbschlagKeyEnum.ZuAbschlagKey;
import de.gkvsv.fhir.ta7.model.extensions.Abrechnungsposition;
import de.gkvsv.fhir.ta7.model.extensions.InvoiceLineItem;
import de.gkvsv.fhir.ta7.model.extensions.ZusatzDatenHerstellung;
import de.gkvsv.fhir.ta7.model.extensions.ZusatzDatenHerstellung.Einheit;
import de.gkvsv.fhir.ta7.model.profiles.EAbrechnungsdaten;
import org.hl7.fhir.r4.model.Invoice.InvoicePriceComponentType;
import org.hl7.fhir.r4.model.Money;

/**
 * created by mmbarek on 17.11.2021.
 */
public class TA7Factory {

    public static EAbrechnungsdaten createEAbrechnungsdaten () {
        // Rechnung eAbrechnungsdaten erstellen
        EAbrechnungsdaten eAbrechnungsdaten = new EAbrechnungsdaten();

        // ZusatzDatenHerstellung
        ZusatzDatenHerstellung zusatzDatenHerstellung = new ZusatzDatenHerstellung();
        zusatzDatenHerstellung.setZaehlerHerstellung(1);

        // eine Einheit erstellen
        Einheit einheit = new Einheit();
        einheit.setZaehlerEinheit(1);

        zusatzDatenHerstellung.getEinheiten().add(einheit);

        eAbrechnungsdaten.setZusatzDatenHerstellung(zusatzDatenHerstellung);
        eAbrechnungsdaten.setIrrlaeufer(true);

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
        eAbrechnungsdaten.setPrescriptionId("160.100.000.000.021.76");
        eAbrechnungsdaten.setBelegnummer("2105000000713456789");

        // issuer
        eAbrechnungsdaten.setLeistungserbringerSitz(LeistungserbringerSitz.AUSLAND);
        eAbrechnungsdaten.setLeistungserbringerTyp(Leistungserbringertyp.KRANKENHAUSAPOTHEKEN);
        eAbrechnungsdaten.setApothekenIK("90456789");

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

        eAbrechnungsdaten.addLineItem(lineItem);

        return eAbrechnungsdaten;
    }
}
