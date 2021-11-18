package de.gkvsv.fhir.ta7.model.profiles;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.api.TemporalPrecisionEnum;
import ca.uhn.fhir.parser.IParser;
import de.gkvsv.fhir.ta7.model.enums.RechnungsartEnum.Rechnungsart;
import de.gkvsv.fhir.ta7.model.util.TA7Factory;
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
        bundle.setDateiname("APO45607")
            .setDateinummer("00007");

        // Rezeptbundle
        RezeptBundle rezeptBundle = new RezeptBundle();

        rezeptBundle.getVerordnungsdaten().setContentAsBase64("VmVyb3JkbnVuZ3NkYXRlbiBCZWlzcGllbCBFcnN0ZWxsdW5nIEJlaXNwaWVsIGVBYnJlY2hudW5nc2RhdGVu");
        rezeptBundle.getQuittungsdaten().setContentAsBase64("VmVyb3JkbnVuZ3NkYXRlbiBCZWlzcGllbCBFcnN0ZWxsdW5nIEJlaXNwaWVsIGVBYnJlY2hudW5nc2RhdGVu");
        rezeptBundle.getAbgabedaten().setContentAsBase64("ZUFiZ2FiZWRhdGVuIEJlaXNwaWVsIEVyc3RlbGx1bmcgQmVpc3BpZWwgZUFicmVjaG51bmdzZGF0ZW4=");

        final EAbrechnungsdaten eAbrechnungsdaten = TA7Factory.createEAbrechnungsdaten();
        rezeptBundle.getAbrechungsdaten().setEAbrechnungsdaten(eAbrechnungsdaten);

        // TA7Rechnung 1->n RezeptBundle,
        // code ta7Rechnung.addRefrenceRezeptBundle(rezeptBundle.getId())
        // TA7 Rechnung
        TA7Rechnung ta7Rechnung = new TA7Rechnung();
        ta7Rechnung.setAbrechnungszeitraum(new Date());
        ta7Rechnung.getAbrechnungszeitraum().setPrecision(TemporalPrecisionEnum.DAY);

        ta7Rechnung.addRefrenceRezeptBundle(rezeptBundle.getId())   // rezeptBundle refrence 1..n
            .setSammelrechnungsnummer("1234567890000000")
            .setRechnungsart(Rechnungsart.ABRECHN_UEBER_AZ_DER_APO_UND_ZAHLUNG_UEBER_APO_IK_DURCH_KRA)
            .setKostentraegerIK("123456789")
            .setRechnungsdatum(new Date());

        // SammelrechnungComposition 1->n Ta7Rechnung
        // Code addRechnungToRechnungenSection(ta7Rechnung.getId())
        // SammelrechnungComposition
        SammelrechnungComposition composition = new SammelrechnungComposition();
        composition.setEmpfaengerIK("123456789")
            .setKostentraegerIK("987654321")
            .setAbrechnungszeitraum(new Date())
            .setAbsenderIK("321456789")
            .addSammelrechnungSection("8b83ed44-2438-49aa-8d80-895c00ae9883")
            .addRechnungToRechnungenSection(ta7Rechnung.getId());

        // SammelrechnungList
        SammelrechnungList list = new SammelrechnungList();
        list.addReference("Bundle/" + bundle.getId());

        // build
        bundle.addSammelrechungComposition(composition);
        bundle.addSammelrechnungListCompostion(list);
        bundle.addTa7Rechnung(ta7Rechnung);
        bundle.addRezeptBundle(rezeptBundle);

        final String s = parser.encodeResourceToString(bundle);
        System.out.println(s);
    }
}
