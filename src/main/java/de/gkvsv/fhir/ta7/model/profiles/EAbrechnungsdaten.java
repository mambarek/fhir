package de.gkvsv.fhir.ta7.model.profiles;

import ca.uhn.fhir.model.api.annotation.Child;
import ca.uhn.fhir.model.api.annotation.Extension;
import ca.uhn.fhir.model.api.annotation.ResourceDef;
import ca.uhn.fhir.util.ElementUtil;
import de.gkvsv.fhir.ta7.model.enums.LeistungserbringerSitzEnum.LeistungserbringerSitz;
import de.gkvsv.fhir.ta7.model.enums.LeistungserbringertypEnum.Leistungserbringertyp;
import de.gkvsv.fhir.ta7.model.extensions.ZusatzDatenHerstellung;
import org.hl7.fhir.r4.model.BooleanType;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Invoice;

/**
 * created by mmbarek on 06.11.2021.
 */
@ResourceDef(profile = "https://fhir.gkvsv.de/StructureDefinition/GKVSV_PR_ERP_eAbrechnungsdaten|1.0.4")
public class EAbrechnungsdaten extends Invoice {

    public static String LINK = "https://fhir.gkvsv.de/StructureDefinition/GKVSV_PR_ERP_eAbrechnungsdaten";

    @Child(name = "irrlaeufer")
    @Extension(url="https://fhir.gkvsv.de/StructureDefinition/GKVSV_EX_ERP_Irrlaeufer")
    private BooleanType irrlaeufer = new BooleanType(false);

    @Child(name = "zusatzdatenHerstellung")
    @Extension(url = "http://fhir.gkvsv.de/StructureDefinition/GKVSV_EX_ERP_ZusatzdatenHerstellung")
    private ZusatzDatenHerstellung zusatzDatenHerstellung;

    /**
     * this is a reference to Invoice identifier
     * done throw this.addIdentifier(prescriptionId) in the getter method;
     */
    private Identifier prescriptionId;
    /**
     * this is a reference to Invoice identifier
     * done throw this.addIdentifier(belegnummer) in the getter method;
     */
    private Identifier belegnummer;

    @Override
    public boolean isEmpty() {
        return super.isEmpty() &&
            ElementUtil.isEmpty(irrlaeufer) &&
            ElementUtil.isEmpty(zusatzDatenHerstellung);
    }

    public BooleanType getIrrlaeufer() {
        if(irrlaeufer == null) {
            irrlaeufer = new BooleanType(false);
        }
        return irrlaeufer;
    }

    public EAbrechnungsdaten setIrrlaeufer(BooleanType irrlaeufer) {
        this.irrlaeufer = irrlaeufer;
        return this;
    }

    public EAbrechnungsdaten setIrrlaeufer(boolean aBoolean) {
        this.getIrrlaeufer().setValue(aBoolean);
        return this;
    }

    public ZusatzDatenHerstellung getZusatzDatenHerstellung() {
        return zusatzDatenHerstellung;
    }

    public EAbrechnungsdaten setZusatzDatenHerstellung(ZusatzDatenHerstellung zusatzDatenHerstellung) {
        this.zusatzDatenHerstellung = zusatzDatenHerstellung;
        return this;
    }

    public Identifier getPrescriptionId() {
        if(prescriptionId == null) {
            prescriptionId = new Identifier();
            prescriptionId.setSystem("https://gematik.de/fhir/NamingSystem/PrescriptionID");
            this.addIdentifier(prescriptionId);
        }
        return prescriptionId;
    }

    public EAbrechnungsdaten setPrescriptionId(String prescriptionId) {
        this.getPrescriptionId().setValue(prescriptionId);
        return this;
    }

    public Identifier getBelegnummer() {
        if(belegnummer == null) {
            belegnummer = new Identifier();
            belegnummer.setSystem("https://fhir.gkvsv.de/NamingSystem/GKVSV_NS_Belegnummer");
            this.addIdentifier(belegnummer);
        }
        return belegnummer;
    }

    public EAbrechnungsdaten setBelegnummer(String belegnummer) {
        this.getBelegnummer().setValue(belegnummer);
        return this;
    }

    public EAbrechnungsdaten setLeistungserbringerSitz(LeistungserbringerSitz sitz) {
        if(sitz == null) {
            return null;
        }
        String extUrl = "https://fhir.gkvsv.de/StructureDefinition/GKVSV_EX_ERP_LE_Sitz";
        org.hl7.fhir.r4.model.Extension sitzLE = getIssuer()
            .getExtensionByUrl(extUrl);

        if(sitzLE == null) {
            sitzLE = new org.hl7.fhir.r4.model.Extension();
            sitzLE.setUrl(extUrl);
            getIssuer().addExtension(sitzLE);
        }

        final Coding coding = new Coding();
        coding.setCode(sitz.toCode());
        coding.setSystem(sitz.getSystem());

        sitzLE.setValue(coding);
        return this;
    }

    public EAbrechnungsdaten setLeistungserbringerTyp(Leistungserbringertyp typ) {
        if(typ == null) {
            return this;
        }

        Coding coding = new Coding();
        coding.setSystem(typ.getSystem());
        coding.setCode(typ.toCode());

        CodeableConcept codeableConcept = new CodeableConcept();
        codeableConcept.addCoding(coding);

        this.getIssuer().getIdentifier().setType(codeableConcept);
        return this;
    }

    public EAbrechnungsdaten setApothekenIK(String ik) {
        this.getIssuer().getIdentifier().setSystem("http://fhir.de/NamingSystem/arge-ik/iknr");
        this.getIssuer().getIdentifier().setValue(ik);
        return this;
    }
}
