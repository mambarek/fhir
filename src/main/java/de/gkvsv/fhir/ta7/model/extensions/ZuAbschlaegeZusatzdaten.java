package de.gkvsv.fhir.ta7.model.extensions;

import ca.uhn.fhir.model.api.annotation.Block;
import ca.uhn.fhir.model.api.annotation.Child;
import ca.uhn.fhir.model.api.annotation.Extension;
import ca.uhn.fhir.util.ElementUtil;
import de.gkvsv.fhir.ta7.config.Configuration;
import de.gkvsv.fhir.ta7.model.enums.ZuAbschlagKeyEnum.ZuAbschlagKey;
import org.hl7.fhir.r4.model.BackboneElement;
import org.hl7.fhir.r4.model.CodeType;
import org.hl7.fhir.r4.model.CodeableConcept;
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
            zuAbschlagBetrag = new Money().setCurrency(Configuration.DEAFULT_CURRENCY);
        }
        return zuAbschlagBetrag;
    }


    /**
     * Preis-Format muss 1-12 Stellen inkl. Trenner, eventuelles Minusvorzeichen und 2
     * Nachkommastellen haben. (Regexp = "^(-\\d{1,8}|\\d{1,9})\\.\\d{2}$")
     * @param value
     */
    public ZuAbschlaegeZusatzdaten setZuAbschlagBetrag(double value) {
        this.getZuAbschlagBetrag().setValue(value);
        return this;
    }

    public ZuAbschlaegeZusatzdaten setZuAbschlagBetrag(Money zuAbschlagBetrag) {
        this.zuAbschlagBetrag = zuAbschlagBetrag;
        return this;
    }

    @Override
    public ZuAbschlaegeZusatzdaten copy() {
        ZuAbschlaegeZusatzdaten copy = new ZuAbschlaegeZusatzdaten();
        copy.setZuAbschlagCode(getZuAbschlagCode());
        copy.setZuAbschlagBetrag(getZuAbschlagBetrag());
        if(getZuAbschlagKennzeichen() != null) {
            copy.setZuAbschlagKennzeichen(getZuAbschlagKennzeichen().getValue());
        }
        return copy;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty()
            && ElementUtil.isEmpty(getZuAbschlagBetrag())
            && getZuAbschlagCode().isEmpty();
    }

    public CodeableConcept getZuAbschlagCode() {
        if (zuAbschlagCode == null) {
            zuAbschlagCode = new CodeableConcept();
            zuAbschlagCode.addCoding();
        }
        return zuAbschlagCode;
    }

    public ZuAbschlaegeZusatzdaten setZuAbschlagCode(CodeableConcept code) {
        this.zuAbschlagCode = code;
        return this;
    }

    public ZuAbschlaegeZusatzdaten setZuAbschlagCode(ZuAbschlagKey key) {
        getZuAbschlagCode().getCodingFirstRep()
            .setCode(key.getCode())
            .setSystem(key.getSystem());
        return this;
    }
}
