package de.gkvsv.fhir.ta7.model.profiles;

import ca.uhn.fhir.model.api.annotation.Binding;
import ca.uhn.fhir.model.api.annotation.Child;
import ca.uhn.fhir.model.api.annotation.Extension;
import ca.uhn.fhir.model.api.annotation.ResourceDef;
import ca.uhn.fhir.util.ElementUtil;
import de.gkvsv.fhir.ta7.model.enums.ImportKennzeichenEnum.ImportKennzeichen;
import de.gkvsv.fhir.ta7.model.enums.ImportKennzeichenEnum.ImportKennzeichenEnumFactory;
import de.gkvsv.fhir.ta7.model.extensions.ZusatzDatenHerstellung;
import org.hl7.fhir.r4.model.BooleanType;
import org.hl7.fhir.r4.model.CodeType;
import org.hl7.fhir.r4.model.Enumeration;
import org.hl7.fhir.r4.model.Invoice;

/**
 * created by mmbarek on 06.11.2021.
 */
@ResourceDef(profile = "https://fhir.gkvsv.de/StructureDefinition/GKVSV_PR_ERP_eAbrechnungsdaten")
public class EAbrechnungsdaten extends Invoice {

    @Child(name = "irrlaeufer")
    @Extension(url="https://fhir.gkvsv.de/StructureDefinition/GKVSV_EX_ERP_Irrlaeufer")
    private BooleanType irrlaeufer = new BooleanType(false);

    @Child(name = "zusatzdatenHerstellung")
    @Extension(url = "http://fhir.gkvsv.de/StructureDefinition/GKVSV_EX_ERP_ZusatzdatenHerstellung")
    private ZusatzDatenHerstellung zusatzDatenHerstellung;

    @Child(name = "importKennzeichen", type = {CodeType.class})
    @Binding(valueSet = "https://fhir.gkvsv.de/CodeSystem/GKVSV_CS_ERP_Import")
    private Enumeration<ImportKennzeichen> importKennzeichen;

    public ImportKennzeichen getImportKennzeichen() {
        if(importKennzeichen == null) return null;
        return importKennzeichen.getValue();
    }

    public EAbrechnungsdaten setImportKennzeichen(ImportKennzeichen value) {
        if(value == null) {
            importKennzeichen = null;
        } else {
            if(importKennzeichen == null) {
                importKennzeichen = new Enumeration<>(new ImportKennzeichenEnumFactory());
            }

            importKennzeichen.setValue(value);
        }

        return this;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty() &&
            ElementUtil.isEmpty(irrlaeufer) &&
            ElementUtil.isEmpty(importKennzeichen) &&
            ElementUtil.isEmpty(zusatzDatenHerstellung);
    }

    public BooleanType getIrrlaeufer() {
        if(irrlaeufer == null) {
            irrlaeufer = new BooleanType(false);
        }
        return irrlaeufer;
    }

    public void setIrrlaeufer(BooleanType irrlaeufer) {
        if(irrlaeufer == null) {
            irrlaeufer = new BooleanType();
        }

        this.irrlaeufer = irrlaeufer;
    }

    public void setIrrlaeufer(boolean aBoolean) {
        if(irrlaeufer == null) {
            irrlaeufer = new BooleanType();
        }

        this.irrlaeufer.setValue(aBoolean);
    }

    public ZusatzDatenHerstellung getZusatzDatenHerstellung() {
        return zusatzDatenHerstellung;
    }

    public void setZusatzDatenHerstellung(
        ZusatzDatenHerstellung zusatzDatenHerstellung) {
        this.zusatzDatenHerstellung = zusatzDatenHerstellung;
    }


}
