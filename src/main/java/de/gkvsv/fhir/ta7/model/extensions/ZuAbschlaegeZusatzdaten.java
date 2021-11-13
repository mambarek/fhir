package de.gkvsv.fhir.ta7.model.extensions;

import ca.uhn.fhir.model.api.annotation.Block;
import ca.uhn.fhir.model.api.annotation.Child;
import ca.uhn.fhir.model.api.annotation.Extension;
import ca.uhn.fhir.util.ElementUtil;
import de.gkvsv.fhir.ta7.model.enums.ZuAbschlagKeyEnum.ZuAbschlagKey;
import org.hl7.fhir.r4.model.BackboneElement;
import org.hl7.fhir.r4.model.CodeType;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Enumeration;
import org.hl7.fhir.r4.model.Invoice.InvoicePriceComponentType;
import org.hl7.fhir.r4.model.Invoice.InvoicePriceComponentTypeEnumFactory;
import org.hl7.fhir.r4.model.Money;

/**
 * created by mmbarek on 12.11.2021.
 */
@Block
public class ZuAbschlaegeZusatzdaten extends BackboneElement {

    @Child(name = "zuAbschlagCode", type = {CodeableConcept.class})
    @Extension(url = "zuAbschlagCode")
    private CodeableConcept zuAbschlagCode;

    @Child(name = "zuAbschlagKennzeichen", type = {CodeType.class})
    @Extension(url = "zuAbschlagKennzeichen")
    private Enumeration<InvoicePriceComponentType> zuAbschlagKennzeichen;

    @Child(name = "zuAbschlagBetrag", type = {Money.class})
    @Extension(url = "zuAbschlagBetrag")
    private Money zuAbschlagBetrag;

    public Enumeration<InvoicePriceComponentType> getZuAbschlagKennzeichen() {
        return zuAbschlagKennzeichen;
    }

    public ZuAbschlaegeZusatzdaten setZuAbschlagKennzeichen(InvoicePriceComponentType value) {
        if (value == null) {
            zuAbschlagKennzeichen = null;
        } else {
            if (zuAbschlagKennzeichen == null) {
                zuAbschlagKennzeichen = new Enumeration<>(
                    new InvoicePriceComponentTypeEnumFactory());
            }
            zuAbschlagKennzeichen.setValue(value);
        }
        return this;
    }

    public Money getZuAbschlagBetrag() {
        if (zuAbschlagBetrag == null) {
            zuAbschlagBetrag = new Money().setCurrency("EUR");
        }
        return zuAbschlagBetrag;
    }

    public void setZuAbschlagBetrag(long value) {
        this.getZuAbschlagBetrag().setValue(value);
    }

    public void setZuAbschlagBetrag(double value) {
        this.getZuAbschlagBetrag().setValue(value);
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
        return super.isEmpty() && ElementUtil.isEmpty(this.getZuAbschlagBetrag())
            && this.getZuAbschlagCode().isEmpty();
    }

    public CodeableConcept getZuAbschlagCode() {
        if (zuAbschlagCode == null) {
            zuAbschlagCode = new CodeableConcept();
            final Coding coding = zuAbschlagCode.addCoding();
            coding.setSystem("https://fhir.gkvsv.de/CodeSystem/GKVSV_CS_ERP_ZuAbschlagKey");
        }

        return zuAbschlagCode;
    }

    public void setZuAbschlagCode(CodeableConcept code) {
        this.zuAbschlagCode = code;
    }

    public void setZuAbschlagCode(ZuAbschlagKey key) {
        this.getZuAbschlagCode().getCoding().get(0).setCode(key.getCode());
    }
}
