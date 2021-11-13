package de.gkvsv.fhir.ta7.model.profiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import de.gkvsv.fhir.ta7.model.enums.ImportKennzeichenEnum.ImportKennzeichen;
import java.util.Date;
import java.util.UUID;
import org.hl7.fhir.r4.model.BooleanType;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;
import org.hl7.fhir.r4.model.Bundle.BundleType;
import org.hl7.fhir.r4.model.CanonicalType;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Enumerations;
import org.hl7.fhir.r4.model.Extension;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Invoice;
import org.hl7.fhir.r4.model.Invoice.InvoiceStatus;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.StringType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * created by mmbarek on 07.11.2021.
 */
class EAbrechnungsdatenTest {

    static FhirContext fhirContext = FhirContext.forR4();
    static IParser parser;

    @BeforeAll
    public static void init() {
        fhirContext.registerCustomType(EAbrechnungsdaten.class);
        parser = fhirContext.newXmlParser();
        parser.setPrettyPrint(true);
    }

    @Test
    public void testEAbrechnungsdaten() {

        EAbrechnungsdaten eAbrechnungsdaten = new EAbrechnungsdaten();
        final String id = UUID.randomUUID().toString();
        eAbrechnungsdaten.setId(id);
        eAbrechnungsdaten.addIdentifier().setSystem("urn:mrns").setValue(id);

        //eAbrechnungsdaten.setIrrlaeufer(true);
        eAbrechnungsdaten.setImportKennzeichen(ImportKennzeichen.KEIN_BEZUGS_ARZNEI_MITTEL);



        final String s = parser.encodeResourceToString(eAbrechnungsdaten);
        System.out.println(s);
    }

    @Test
    public void testBundle() {

        // https://github.com/DAV-ABDA/eRezept-Beispiele/blob/main/TA7_Sammelrechnung_Bundle-proKasse/TK_Split_Teil1.xml
        //----------------- Begin Bundle Header ----------------------------------------------------
        Bundle ta7Bundle = new Bundle();
        ta7Bundle.setId(UUID.randomUUID().toString());
        ta7Bundle.getMeta().getProfile().add(new CanonicalType("https://fhir.gkvsv.de/StructureDefinition/GKVSV_PR_TA7_Sammelrechnung_Bundle|1.0.4"));
        ta7Bundle.getMeta().getTag().add(new Coding().setDisplay("xxxxxxxxxxxx"));
        // tags werden eingebunden nur wenn ein system gesetzt ist
        ta7Bundle.getMeta().addTag().setDisplay("Beispiel der TA7/eAbrechnungsdaten.").setSystem("sdfsd");
        ta7Bundle.getMeta().addTag().setDisplay("ACHTUNG! Der fachlich korrekte Inhalt der Beispielinstanz kann nicht gewährleistet werden. Wir sind jederzeit dankbar für Hinweise auf Fehler oder für Verbesserungsvorschläge.");

        ta7Bundle.setType(BundleType.DOCUMENT);
        ta7Bundle.setTimestamp(new Date());

        // Dateinumeer
        Identifier datei = new Identifier();
        datei.setSystem("https://fhir.gkvsv.de/NamingSystem/GKVSV_NS_Dateiname");
        datei.setValue("ARZFHR21001");

        Extension dateinummer = new Extension();
        dateinummer.setUrl("https://fhir.gkvsv.de/StructureDefinition/GKVSV_EX_ERP_TA7_Dateinummer");
        dateinummer.setValue(new StringType("00001"));

        datei.addExtension(dateinummer);
        ta7Bundle.setIdentifier(datei);
        //------------------- END Bundle Header ---------------------------------------------------

        // ------------------------------- BEGIN Rechnung -----------------------------------------
        // Rechnung hinzufügen
        String rechnungId = UUID.randomUUID().toString();
        Invoice invoice = new Invoice();
        invoice.getMeta().getProfile().add(new CanonicalType("https://fhir.gkvsv.de/StructureDefinition/GKVSV_PR_ERP_eAbrechnungsdaten|1.0.4"));
        invoice.setId(rechnungId);
        invoice.setStatus(InvoiceStatus.ISSUED);

        Extension irrlaeufer = new Extension();
        irrlaeufer.setUrl("https://fhir.gkvsv.de/StructureDefinition/GKVSV_EX_ERP_Irrlaeufer");
        irrlaeufer.setValue(new BooleanType("false"));

        invoice.addExtension(irrlaeufer);

        // Prescription id
        Identifier presicriptionID = new Identifier();
        presicriptionID.setSystem("https://gematik.de/fhir/NamingSystem/PrescriptionID");
        presicriptionID.setValue("160.100.000.000.021.76");
        invoice.addIdentifier(presicriptionID);

        // Belegnummer
        Identifier belegnummer = new Identifier();
        belegnummer.setSystem("https://fhir.gkvsv.de/NamingSystem/GKVSV_NS_Belegnummer");
        belegnummer.setValue("2105000001011234561");
        invoice.addIdentifier(belegnummer);

        BundleEntryComponent entryComponent = new BundleEntryComponent();
        entryComponent.setResource(invoice);

        entryComponent.setFullUrl("urn:uuid:" + rechnungId);

        ta7Bundle.addEntry(entryComponent);
        // --------------------------- END Rechnung ----------------------------------------------

        final String bundleString = parser.encodeResourceToString(ta7Bundle);

        System.out.println(bundleString);

    }

    @Test
    public void testParseResource() {
        String rawString = "<EAbrechnungsdaten xmlns=\"http://hl7.org/fhir\">\n"
            + "   <meta>\n"
            + "      <profile value=\"https://fhir.gkvsv.de/StructureDefinition/GKVSV_PR_ERP_eAbrechnungsdaten\"></profile>\n"
            + "   </meta>\n"
            + "   <importKennzeichen value=\"3\"></importKennzeichen>\n"
            + "</EAbrechnungsdaten>";

        final EAbrechnungsdaten eAbrechnungsdaten = parser.parseResource(EAbrechnungsdaten.class,
            rawString);

        assertNotNull(eAbrechnungsdaten);
    }


    @Test
    public void testEnum() {
        Patient patient = new Patient();

// You can set this code using a String if you want. Note that
// for "closed" valuesets (such as the one used for Patient.gender)
// you must use one of the strings defined by the FHIR specification.
// You must not define your own.
        //patient.getGenderElement().setValueAsString("male");

// HAPI also provides Java enumerated types which make it easier to
// deal with coded values. This code achieves the exact same result
// as the code above.
        patient.setGender(Enumerations.AdministrativeGender.MALE);

        final String s = parser.encodeResourceToString(patient);
        System.out.println(s);
    }

}
