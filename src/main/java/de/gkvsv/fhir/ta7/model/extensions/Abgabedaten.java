package de.gkvsv.fhir.ta7.model.extensions;

import ca.uhn.fhir.model.api.annotation.Block;
import de.gkvsv.fhir.ta7.model.profiles.ErxBinary;
import java.util.UUID;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;

/**
 * created by mmbarek on 17.11.2021.
 */
@Block
public class Abgabedaten extends BundleEntryComponent {

    private ErxBinary erxBinary;

    public Abgabedaten() {
        init();
    }

    private void init() {
        getLinkFirstRep().setRelation("item");
        getLinkFirstRep().setUrl("http://fhir.abda.de/eRezeptAbgabedaten/StructureDefinition/DAV-PR-ERP-AbgabedatenBundle");

        erxBinary = new ErxBinary();

        setResource(erxBinary);
        getResource().setId(UUID.randomUUID().toString());
        setFullUrl("urn:uuid:" + getResource().getId());
    }

    public Abgabedaten setContentAsBase64(String base64String) {
        erxBinary.setContentAsBase64(base64String);
        return this;
    }
}
