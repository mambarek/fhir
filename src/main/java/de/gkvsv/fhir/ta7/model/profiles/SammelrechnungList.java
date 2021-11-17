package de.gkvsv.fhir.ta7.model.profiles;

import ca.uhn.fhir.model.api.annotation.Description;
import ca.uhn.fhir.model.api.annotation.ResourceDef;
import java.util.UUID;
import org.hl7.fhir.r4.model.ListResource;
/**
 * created by mmbarek on 06.11.2021.
 */
@ResourceDef(profile = "https://fhir.gkvsv.de/StructureDefinition/GKVSV_PR_TA7_Sammelrechnung_List|1.1.0")
@Description("Die Liste führt alle TA7_Sammelrechnung_Bundles auf.")
public class SammelrechnungList extends ListResource{

    public SammelrechnungList() {
        setId(UUID.randomUUID().toString());
        setStatus(ListStatus.CURRENT);
        setMode(ListMode.WORKING);
    }

    public SammelrechnungList addReference(String reference) {
        final ListEntryComponent listEntryComponent = addEntry();
        listEntryComponent.getItem().setReference(reference);
        return this;
    }
}
