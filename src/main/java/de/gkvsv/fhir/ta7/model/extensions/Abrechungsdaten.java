package de.gkvsv.fhir.ta7.model.extensions;

import ca.uhn.fhir.model.api.annotation.Block;
import de.gkvsv.fhir.ta7.model.profiles.EAbrechnungsdaten;
import de.gkvsv.fhir.ta7.model.profiles.ErxBinary;
import java.util.UUID;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;

/**
 * created by mmbarek on 17.11.2021.
 */
@Block
public class Abrechungsdaten extends BundleEntryComponent {

    private EAbrechnungsdaten eAbrechnungsdaten;

    public Abrechungsdaten() {
        init();
    }

    private void init() {
        getLinkFirstRep().setRelation("item");
        getLinkFirstRep().setUrl("https://fhir.gkvsv.de/StructureDefinition/GKVSV_PR_ERP_eAbrechnungsdaten");
    }

    public Abrechungsdaten seteAbrechnungsdaten(EAbrechnungsdaten eAbrechnungsdaten) {
        this.eAbrechnungsdaten = eAbrechnungsdaten;
        setResource(this.eAbrechnungsdaten);
        setFullUrl("urn:uuid:" + getResource().getId());
        return this;
    }
}
