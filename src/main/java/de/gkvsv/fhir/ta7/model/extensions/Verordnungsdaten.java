package de.gkvsv.fhir.ta7.model.extensions;

import ca.uhn.fhir.model.api.annotation.Block;
import de.gkvsv.fhir.ta7.model.profiles.ErxBinary;
import java.util.UUID;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;

/**
 * Verordnungsdatensatz
 * Der Verordnungsdatensatz (https://fhir.kbv.de/StructureDefinition/KBV_PR_ERP_Bundle) soll als
 * base64Binary im Profil erxBinary enthalten sein.
 * created by mmbarek on 17.11.2021.
 */
@Block
public class Verordnungsdaten extends BundleEntryComponent {

    /**
     * Der Verordnungsdatensatz (https://fhir.kbv.de/StructureDefinition/KBV_PR_ERP_Bundle) soll
     * als base64Binary im Profil erxBinary enthalten sein.
     */
    private ErxBinary erxBinary;

    public Verordnungsdaten() {
        init();
    }

    private void init() {
        getLinkFirstRep().setRelation("item");
        getLinkFirstRep().setUrl("https://fhir.kbv.de/StructureDefinition/KBV_PR_ERP_Bundle");

        erxBinary = new ErxBinary();

        setResource(erxBinary);
        getResource().setId(UUID.randomUUID().toString());
        setFullUrl("urn:uuid:" + getResource().getId());
    }

    public Verordnungsdaten setContentAsBase64(String base64String) {
        erxBinary.setContentAsBase64(base64String);
        return this;
    }

}
