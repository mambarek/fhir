package de.gkvsv.fhir.ta7.model.extensions;

import ca.uhn.fhir.model.api.annotation.Binding;
import ca.uhn.fhir.model.api.annotation.Block;
import ca.uhn.fhir.model.api.annotation.Child;
import ca.uhn.fhir.model.api.annotation.DatatypeDef;
import ca.uhn.fhir.model.api.annotation.Extension;
import ca.uhn.fhir.util.ElementUtil;
import de.gkvsv.fhir.ta7.model.enums.ImportKennzeichenEnum.ImportKennzeichen;
import de.gkvsv.fhir.ta7.model.enums.ImportKennzeichenEnum.ImportKennzeichenEnumFactory;
import de.gkvsv.fhir.ta7.model.enums.ZuAbschlagKennzeichen;
import de.gkvsv.fhir.ta7.model.enums.ZuAbschlagKeyEnum;
import de.gkvsv.fhir.ta7.model.enums.ZuAbschlagKeyEnum.ZuAbschlagKey;
import de.gkvsv.fhir.ta7.model.enums.ZuAbschlagKeyEnum.ZuAbschlagKeyFactory;
import java.util.Currency;
import java.util.EnumSet;
import org.hl7.fhir.instance.model.api.ICompositeType;
import org.hl7.fhir.r4.model.BackboneElement;
import org.hl7.fhir.r4.model.CodeType;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Enumeration;
import org.hl7.fhir.r4.model.Invoice;
import org.hl7.fhir.r4.model.Invoice.InvoicePriceComponentType;
import org.hl7.fhir.r4.model.Invoice.InvoicePriceComponentTypeEnumFactory;
import org.hl7.fhir.r4.model.Money;
import org.hl7.fhir.r4.model.Type;

/**
 * created by mmbarek on 12.11.2021.
 */
@Block
public class ZuAbschlaegeZusatzdaten extends BackboneElement {

    @Child(name = "zuAbschlagCode", type = {CodeableConcept.class})
    @Binding(valueSet = "https://fhir.gkvsv.de/CodeSystem/GKVSV_CS_ERP_ZuAbschlagKey")
    @Extension(url = "zuAbschlagCode")
    private Enumeration<ZuAbschlagKey> zuAbschlagCode;

    @Child(name = "zuAbschlagCode2", type = {CodeType.class})
    @Binding(valueSet = "https://fhir.gkvsv.de/CodeSystem/GKVSV_CS_ERP_ZuAbschlagKey")
    @Extension(url = "zuAbschlagCode2")
    private CodeableConcept zuAbschlagCode2;

    @Child(name = "zuAbschlagKennzeichen", type = {CodeType.class})
    @Extension(url = "zuAbschlagKennzeichen")
    private Enumeration<InvoicePriceComponentType> zuAbschlagKennzeichen;

    @Child(name = "zuAbschlagBetrag", type = {Money.class})
    @Extension(url = "zuAbschlagBetrag")
    private Money zuAbschlagBetrag;

    public Enumeration<ZuAbschlagKey> getZuAbschlagCode() {
        return zuAbschlagCode;
    }

    public ZuAbschlaegeZusatzdaten setZuAbschlagCode(ZuAbschlagKey value) {
        if(value == null) {
            zuAbschlagCode = null;
        } else {
            if(zuAbschlagCode == null) {
                zuAbschlagCode = new Enumeration<>(new ZuAbschlagKeyFactory());
            }

            zuAbschlagCode.setValue(value);
        }
        return this;
    }

    public Enumeration<InvoicePriceComponentType> getZuAbschlagKennzeichen() {
        return zuAbschlagKennzeichen;
    }

    public ZuAbschlaegeZusatzdaten setZuAbschlagKennzeichen(
        InvoicePriceComponentType value) {

        if(value == null) {
            zuAbschlagKennzeichen = null;
        } else {
            if(zuAbschlagKennzeichen == null) {
                zuAbschlagKennzeichen = new Enumeration<>(new InvoicePriceComponentTypeEnumFactory());
            }

            zuAbschlagKennzeichen.setValue(value);
        }
        return this;
    }

    public Money getZuAbschlagBetrag() {
        if(zuAbschlagBetrag == null) {
            zuAbschlagBetrag = new Money().setCurrency("Eur");
        }
        return zuAbschlagBetrag;
    }

    public void setZuAbschlagBetrag(Money zuAbschlagBetrag) {
        this.zuAbschlagBetrag = zuAbschlagBetrag;
    }

    @Override
    public ZuAbschlaegeZusatzdaten copy() {
        ZuAbschlaegeZusatzdaten copy = new ZuAbschlaegeZusatzdaten();
        return copy;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty() && ElementUtil.isEmpty(this.getZuAbschlagBetrag()) && this.getZuAbschlagCode().isEmpty();
    }

    public CodeableConcept getZuAbschlagCode2() {
        if(zuAbschlagCode2 == null) {
            zuAbschlagCode2 = new CodeableConcept();
            final Coding coding = zuAbschlagCode2.addCoding();
            coding.setSystem("https://fhir.gkvsv.de/CodeSystem/GKVSV_CS_ERP_Verwurf");
        }

        return zuAbschlagCode2;
    }

    public void setZuAbschlagCode2(CodeableConcept zuAbschlagCode2) {
        this.zuAbschlagCode2 = zuAbschlagCode2;
    }

    public void setZuAbschlagCode2(ZuAbschlagKey key) {
        this.getZuAbschlagCode2().getCoding().get(0).setCode(key.getCode());
    }
}
