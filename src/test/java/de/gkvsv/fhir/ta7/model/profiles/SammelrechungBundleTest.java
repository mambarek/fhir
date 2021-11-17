package de.gkvsv.fhir.ta7.model.profiles;

import static org.junit.jupiter.api.Assertions.*;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.api.TemporalPrecisionEnum;
import ca.uhn.fhir.parser.IParser;
import de.gkvsv.fhir.ta7.model.enums.RechnungsartEnum.Rechnungsart;
import java.util.Date;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * created by mmbarek on 17.11.2021.
 */
class SammelrechungBundleTest {

    FhirContext fhirContext = FhirContext.forR4();
    IParser parser = fhirContext.newXmlParser();

    @BeforeEach
    void setUp() {
        parser.setPrettyPrint(true);
    }

    @Test
    void testCreate() {
        SammelrechungBundle bundle = new SammelrechungBundle();
        bundle.setDateiname("Datei1")
            .setDateinummer("10007");

        // TA7 Rechnung
        TA7Rechnung ta7Rechnung = new TA7Rechnung();
        ta7Rechnung.setAbrechnungszeitraum(new Date());
        ta7Rechnung.getAbrechnungszeitraum().setPrecision(TemporalPrecisionEnum.DAY);

        ta7Rechnung.setRefrenceRezeptBundle(UUID.randomUUID().toString())
            .setSammelrechnungsnummer("1234567890000000")
            .setRechnungsart(Rechnungsart.ABRECHN_UEBER_AZ_DER_APO_UND_ZAHLUNG_UEBER_APO_IK_DURCH_KRA)
            .setKostentraegerIK("123456789")
            .setRechnungsdatum(new Date());


        // SammelrechnungComposition
        SammelrechnungComposition composition = new SammelrechnungComposition();
        composition.setEmpfaengerIK("123456789")
            .setKostentraegerIK("987654321")
            .setAbrechnungszeitraum(new Date())
            .setAbsenderIK("321456789")
            .addSammelrechnungSection("8b83ed44-2438-49aa-8d80-895c00ae9883")
            .addRechnungenSection(ta7Rechnung.getId());


        // SammelrechnungList
        SammelrechnungList list = new SammelrechnungList();
        list.addReference("Bundle/" + bundle.getId());

        // build
        bundle.addSammelrechungComposition(composition);
        bundle.addSammelrechnungListCompostion(list);
        bundle.addTa7Rechnung(ta7Rechnung);

        final String s = parser.encodeResourceToString(bundle);
        System.out.println(s);
    }
}
