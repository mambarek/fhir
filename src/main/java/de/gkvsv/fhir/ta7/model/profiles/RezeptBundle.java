package de.gkvsv.fhir.ta7.model.profiles;

import ca.uhn.fhir.model.api.annotation.Description;
import ca.uhn.fhir.model.api.annotation.ResourceDef;
import de.gkvsv.fhir.ta7.model.extensions.Abgabedaten;
import de.gkvsv.fhir.ta7.model.extensions.Abrechungsdaten;
import de.gkvsv.fhir.ta7.model.extensions.Quittungsdaten;
import de.gkvsv.fhir.ta7.model.extensions.Verordnungsdaten;
import java.util.UUID;
import org.hl7.fhir.r4.model.Bundle;

/**
 * Dieses Bundle verbindet die eRezept Verordnungsdaten, Quittungsdaten, Abgabedaten und Abrechnungsdaten
 * created by mmbarek on 06.11.2021.
 */
@ResourceDef(profile = "https://fhir.gkvsv.de/StructureDefinition/GKVSV_PR_TA7_RezeptBundle|1.1.0")
@Description("Dieses Bundle verbindet die eRezept Verordnungsdaten, Quittungsdaten, Abgabedaten und Abrechnungsdaten")
public class RezeptBundle extends Bundle {

    /**
     * Die Verordnungsdaten (Resource im Bundle) sind als binary 64 zu hinterlegen
     */
    private Verordnungsdaten verordnungsdaten;

    /**
     * Die Quittungsdaten (Resource im Bundle) sind als binary 64 zu hinterlegen
     */
    private Quittungsdaten quittungsdaten;

    /**
     * Die Abgabedaten (Resource im Bundle) sind als binary 64 zu hinterlegen
     */
    private Abgabedaten abgabedaten;

    private Abrechungsdaten abrechungsdaten;

    /**
     * type is foxed to collection
     */
    public RezeptBundle() {
        setType(BundleType.COLLECTION);
        setId(UUID.randomUUID().toString());
        init();
    }

    private void init() {
        verordnungsdaten = new Verordnungsdaten();
        quittungsdaten = new Quittungsdaten();
        abgabedaten = new Abgabedaten();
        abrechungsdaten = new Abrechungsdaten();
        addEntry(verordnungsdaten);
        addEntry(quittungsdaten);
        addEntry(abgabedaten);
        addEntry(abrechungsdaten);
    }

    public Verordnungsdaten getVerordnungsdaten() {
        return verordnungsdaten;
    }

    public Quittungsdaten getQuittungsdaten() {
        return quittungsdaten;
    }

    public Abgabedaten getAbgabedaten() {
        return abgabedaten;
    }

    public Abrechungsdaten getAbrechungsdaten() {
        return abrechungsdaten;
    }

}
