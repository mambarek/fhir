package de.gkvsv.fhir.ta7.model.profiles;

import ca.uhn.fhir.model.api.annotation.Description;
import ca.uhn.fhir.model.api.annotation.ResourceDef;
import de.gkvsv.fhir.ta7.config.Configuration;
import java.util.Date;
import java.util.UUID;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.StringType;

/**
 * TA7 Sammelrechnung aus Datei - Bundle
 * 4 entries
 * - sammelrechnung_Composition (1..1)
 * - sammelrechnung_List (1..1)
 * - rechnung   (1..n)
 * - rezeptBundle   (1..n)
 * created by mmbarek on 06.11.2021.
 */
@ResourceDef(profile = "https://fhir.gkvsv.de/StructureDefinition/GKVSV_PR_TA7_Sammelrechnung_Bundle")
@Description("TA7 Sammelrechnung aus Datei - Bundle")
public class SammelrechungBundle extends Bundle {

    private BundleEntryComponent sammelrechungCompositionEntryComponent;
    private BundleEntryComponent sammelrechnungListEntryComponent;

    public SammelrechungBundle() {
        setId(UUID.randomUUID().toString());
        setType(BundleType.DOCUMENT);
        setTimestamp(new Date());
    }

    /**
     * Der Dateiname muss den Konventionen der TA7 entsprechen.
     * toString().matches('^(ARZ|APO|KKR|KRZ|SON)(FHR|FK[1-9])(\\d{2})(\\d{3}|.\\d{2})$')
     * @param dateiname
     * @return SammelrechungBundle
     */
    public SammelrechungBundle setDateiname(String dateiname) {
        getIdentifier()
            .setValue(dateiname)
            .setSystem("https://fhir.gkvsv.de/NamingSystem/GKVSV_NS_Dateiname");
        return this;
    }

    /**
     * fortlaufende Nummer innerhalb eines Jahres, beginnend mit 00001.
     * @param dateinummer
     * @return SammelrechungBundle
     */
    public SammelrechungBundle setDateinummer(String dateinummer) {
        getIdentifier().getExtensionFirstRep()
            .setValue(new StringType(dateinummer))
            .setUrl("https://fhir.gkvsv.de/StructureDefinition/GKVSV_EX_ERP_TA7_Dateinummer");
        return this;
    }

    /**
     * Mapping: 1->n
     * @param ta7Rechnung
     * @return SammelrechungBundle
     */
    public SammelrechungBundle addTa7Rechnung(TA7Rechnung ta7Rechnung) {
        addEntry().setResource(ta7Rechnung)
            .setFullUrl(Configuration.URN_URL_PREFIX + ta7Rechnung.getId());
        return this;
    }

    /**
     * Es existiert nur ein einziges Compostion. Mapping 1->1
     * @param composition
     * @return SammelrechungBundle
     */
    public SammelrechungBundle addSammelrechungComposition(SammelrechnungComposition composition) {
        if(sammelrechungCompositionEntryComponent == null) {
            sammelrechungCompositionEntryComponent = addEntry();
        }
        sammelrechungCompositionEntryComponent.setResource(composition)
            .setFullUrl(Configuration.URN_URL_PREFIX + composition.getId());
        return this;
    }

    /**
     * Es existiert nur eine rechnungsList. Mapping 1->1
     * @param list
     * @return SammelrechungBundle
     */
    public SammelrechungBundle addSammelrechnungListCompostion(SammelrechnungList list) {
        if(sammelrechnungListEntryComponent == null) {
            sammelrechnungListEntryComponent = addEntry();
        }
        list.addReference("Bandle/" + getId());
        sammelrechnungListEntryComponent.setResource(list)
            .setFullUrl(Configuration.URN_URL_PREFIX + list.getId());

        return this;
    }

    /**
     * Mapping 1->n
     * @param rezeptBundle
     * @return SammelrechungBundle
     */
    public SammelrechungBundle addRezeptBundle(RezeptBundle rezeptBundle) {
        addEntry().setResource(rezeptBundle)
            .setFullUrl(Configuration.URN_URL_PREFIX + rezeptBundle.getId());
        return this;
    }
}
