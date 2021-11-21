package de.gkvsv.fhir.ta7.model.profiles;

import ca.uhn.fhir.model.api.TemporalPrecisionEnum;
import ca.uhn.fhir.model.api.annotation.Child;
import ca.uhn.fhir.model.api.annotation.Description;
import ca.uhn.fhir.model.api.annotation.Extension;
import ca.uhn.fhir.model.api.annotation.ResourceDef;
import de.gkvsv.fhir.ta7.config.Configuration;
import de.gkvsv.fhir.ta7.model.enums.TA7Enum.TA7;
import java.util.Date;
import java.util.UUID;
import org.hl7.fhir.r4.model.Composition;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Reference;

/**
 * TA7 Sammelrechnung - Composition
 * created by mmbarek on 06.11.2021.
 */
@ResourceDef(profile = "https://fhir.gkvsv.de/StructureDefinition/GKVSV_PR_TA7_Sammelrechnung_Composition")
@Description("TA7 Sammelrechnung - Composition")
public class SammelrechnungComposition extends Composition {

    @Child(name = "empfaengerIK")
    @Extension(url = "https://fhir.gkvsv.de/StructureDefinition/GKVSV_EX_TA7_IK_Empfaenger")
    private Identifier empfaengerIK;

    @Child(name = "kostentraegerIK")
    @Extension(url = "https://fhir.gkvsv.de/StructureDefinition/GKVSV_EX_TA7_IK_Kostentraeger")
    private Identifier kostentraegerIK;

    private SectionComponent sammelrechungSection;
    private SectionComponent rechungenSection;


    public SammelrechnungComposition() {
        setId(UUID.randomUUID().toString());
        setStatus(CompositionStatus.FINAL);
        setType(TA7.SR);
        setTitle("elektronische Sammelrechnung");
    }

    public Identifier getEmpfaengerIK() {
        if(empfaengerIK == null) {
            empfaengerIK = new Identifier();
            empfaengerIK.setSystem("http://fhir.de/NamingSystem/arge-ik/iknr");
        }
        return empfaengerIK;
    }

    /**
     * Die IK des Empfängers muss 9 Zeichen lang und numerisch sein.
     * @param empfaengerIK
     * @return
     */
    public SammelrechnungComposition setEmpfaengerIK(String empfaengerIK) {
        this.getEmpfaengerIK().setValue(empfaengerIK);
        return this;
    }

    public Identifier getKostentraegerIK() {
        if(kostentraegerIK == null) {
            kostentraegerIK = new Identifier();
            kostentraegerIK.setSystem("http://fhir.de/NamingSystem/arge-ik/iknr");
        }
        return kostentraegerIK;
    }

    public SammelrechnungComposition setKostentraegerIK(String kostentraegerIK) {
        this.getKostentraegerIK().setValue(kostentraegerIK);
        return this;
    }

    private SammelrechnungComposition setType(TA7 ta7) {
        getType().getCodingFirstRep().setCode(ta7.toCode())
            .setSystem(ta7.getSystem())
            .setDisplay(ta7.getDisplay());
        return this;
    }

    public SammelrechnungComposition setAbrechnungszeitraum(Date date) {
        setDate(date);
        getDateElement().setPrecision(TemporalPrecisionEnum.DAY);
        return this;
    }

    /**
     * IK des Absenders
     * Die IK des Absenders muss 9 Zeichen lang und numerisch sein.
     * @param authorIK
     * @return
     */
    public SammelrechnungComposition setAbsenderIK(String authorIK) {
        getAuthorFirstRep().getIdentifier()
            .setValue(authorIK)
            .setSystem("http://fhir.de/NamingSystem/arge-ik/iknr");
        return this;
    }

    /**
     * Section für SammelRechnung. besitzt genau eine Sammelrechnung
     * @param sammelrechungId
     * @return
     */
    public SammelrechnungComposition addSammelrechnungSection(String sammelrechungId) {
        if(sammelrechungSection == null) {
            initSammelrechnungSection();
        }
        sammelrechungSection.getEntryFirstRep()
            .setReference(Configuration.URN_URL_PREFIX + sammelrechungId);
        return this;
    }

    private void initSammelrechnungSection() {
        sammelrechungSection = addSection();
        sammelrechungSection.getCode().getCodingFirstRep()
            .setCode(TA7.LSR.toCode())
            .setSystem(TA7.LSR.getSystem())
            .setDisplay(TA7.LSR.getDisplay());
    }

    public SammelrechnungComposition addRechnungToRechnungenSection(String rechungId) {
        if(rechungenSection == null) {
            initRechnungenSection();
        }
        Reference idRef = new Reference();
        idRef.setReference(Configuration.URN_URL_PREFIX + rechungId);
        rechungenSection.getEntry().add(idRef);
        return this;
    }

    private void initRechnungenSection() {
        rechungenSection = addSection();
        rechungenSection.getCode().getCodingFirstRep()
            .setCode(TA7.R.toCode())
            .setSystem(TA7.R.getSystem())
            .setDisplay(TA7.R.getDisplay());
    }
}
