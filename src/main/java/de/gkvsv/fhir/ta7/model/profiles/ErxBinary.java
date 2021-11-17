package de.gkvsv.fhir.ta7.model.profiles;

import ca.uhn.fhir.model.api.annotation.ResourceDef;
import org.hl7.fhir.r4.model.Binary;

/**
 * created by mmbarek on 17.11.2021.
 */
@ResourceDef(profile = "https://gematik.de/fhir/StructureDefinition/ErxBinary")
public class ErxBinary extends Binary {

    public ErxBinary() {
        setContentType("application/pkcs7-mime");
    }
}
