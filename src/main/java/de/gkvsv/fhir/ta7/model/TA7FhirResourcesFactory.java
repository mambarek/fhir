package de.gkvsv.fhir.ta7.model;

import java.util.Date;
import java.util.UUID;
import org.hl7.fhir.r4.model.BooleanType;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.BundleType;
import org.hl7.fhir.r4.model.CanonicalType;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Extension;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Invoice;
import org.hl7.fhir.r4.model.Invoice.InvoiceStatus;
import org.hl7.fhir.r4.model.StringType;

/**
 * created by mmbarek on 07.11.2021.
 */
public class TA7FhirResourcesFactory {

    public Bundle createTA7Bundle(String dateiName, String dateiNummer) {
        Bundle ta7Bundle = new Bundle();
        ta7Bundle.setId(UUID.randomUUID().toString());
        ta7Bundle.getMeta().getProfile().add(new CanonicalType("https://fhir.gkvsv.de/StructureDefinition/GKVSV_PR_TA7_Sammelrechnung_Bundle|1.0.4"));
        // tags werden eingebunden nur wenn ein system gesetzt ist
        ta7Bundle.getMeta().addTag().setDisplay("Beispiel der TA7/eAbrechnungsdaten.").
            setSystem("https://fhir.gkvsv.de/StructureDefinition/GKVSV_PR_TA7_Sammelrechnung_Bundle|1.0.4");
        ta7Bundle.getMeta().addTag().setDisplay("ACHTUNG! Der fachlich korrekte Inhalt der Beispielinstanz kann nicht gewährleistet werden. Wir sind jederzeit dankbar für Hinweise auf Fehler oder für Verbesserungsvorschläge.").
            setSystem("https://fhir.gkvsv.de/StructureDefinition/GKVSV_PR_TA7_Sammelrechnung_Bundle|1.0.4");

        ta7Bundle.setType(BundleType.DOCUMENT);
        ta7Bundle.setTimestamp(new Date());

        // Dateinumeer
        Identifier identDatei = new Identifier();
        identDatei.setSystem("https://fhir.gkvsv.de/NamingSystem/GKVSV_NS_Dateiname");
        identDatei.setValue(dateiName);

        Extension extDateiNummer = new Extension();
        extDateiNummer.setUrl("https://fhir.gkvsv.de/StructureDefinition/GKVSV_EX_ERP_TA7_Dateinummer");
        extDateiNummer.setValue(new StringType(dateiNummer));

        identDatei.addExtension(extDateiNummer);
        ta7Bundle.setIdentifier(identDatei);

        return ta7Bundle;
    }

    public Invoice createRechnung(InvoiceStatus status, boolean isIrrlaeufer, String rezeptId, String belegNummer) {
        String rechnungId = UUID.randomUUID().toString();
        Invoice rechung = new Invoice();
        rechung.getMeta().getProfile().add(new CanonicalType("https://fhir.gkvsv.de/StructureDefinition/GKVSV_PR_ERP_eAbrechnungsdaten|1.0.4"));
        rechung.setId(rechnungId);
        rechung.setStatus(status);

        // irrlaeufer
        Extension irrlaeufer = new Extension();
        irrlaeufer.setUrl("https://fhir.gkvsv.de/StructureDefinition/GKVSV_EX_ERP_Irrlaeufer");
        irrlaeufer.setValue(new BooleanType(isIrrlaeufer));
        rechung.addExtension(irrlaeufer);

        // Prescription id
        Identifier presicriptionID = new Identifier();
        presicriptionID.setSystem("https://gematik.de/fhir/NamingSystem/PrescriptionID");
        presicriptionID.setValue(rezeptId);
        rechung.addIdentifier(presicriptionID);

        // Belegnummer
        Identifier belegnummer = new Identifier();
        belegnummer.setSystem("https://fhir.gkvsv.de/NamingSystem/GKVSV_NS_Belegnummer");
        belegnummer.setValue(belegNummer);
        rechung.addIdentifier(belegnummer);

        return rechung;
    }
}
