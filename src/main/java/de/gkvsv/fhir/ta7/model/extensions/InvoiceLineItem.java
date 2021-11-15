package de.gkvsv.fhir.ta7.model.extensions;

import ca.uhn.fhir.model.api.annotation.Binding;
import ca.uhn.fhir.model.api.annotation.Block;
import ca.uhn.fhir.model.api.annotation.Child;
import ca.uhn.fhir.model.api.annotation.Extension;
import ca.uhn.fhir.util.ElementUtil;
import de.gkvsv.fhir.ta7.config.Configuration;
import de.gkvsv.fhir.ta7.model.enums.ImportKennzeichenEnum.ImportKennzeichen;
import de.gkvsv.fhir.ta7.model.enums.PositionstypEnum.Positionstyp;
import de.gkvsv.fhir.ta7.model.enums.ZuAbschlagKeyEnum.ZuAbschlagKey;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Invoice.InvoiceLineItemComponent;
import org.hl7.fhir.r4.model.Invoice.InvoicePriceComponentType;
import org.hl7.fhir.r4.model.Money;

/**
 * created by mmbarek on 15.11.2021.
 */
@Block
public class InvoiceLineItem extends InvoiceLineItemComponent {

    /**
     * Kennzeichen Positionstyp
     * 0 = Nullposition;1 = übrige Position
     */
    @Child(name = "positionstyp", type = {CodeableConcept.class})
    @Extension(url = "https://fhir.gkvsv.de/StructureDefinition/GKVSV_EX_ERP_Positionstyp")
    private CodeableConcept positionstyp;

    /**
     * Kennzeichen zum Import
     */
    @Child(name = "import", type = {CodeableConcept.class})
    @Extension(url = "https://fhir.gkvsv.de/StructureDefinition/GKVSV_EX_ERP_Import")
    private CodeableConcept importKennzeichen;

    /**
     * Umsatzsteuer Betrag
     */
    @Child(name = "vatValue", type = {Money.class})
    @Extension(url = "https://fhir.gkvsv.de/StructureDefinition/GKVSV_EX_ERP_VAT_VALUE")
    private Money vatValue;

    /**
     *  the chargeItem is a fixed value
     */
    public InvoiceLineItem() {
        Coding c = new Coding();
        c.setCode("UNC").setSystem("http://terminology.hl7.org/CodeSystem/v3-NullFlavor");
        CodeableConcept cc = new CodeableConcept();
        cc.addCoding(c);
        this.setChargeItem(cc);
    }

    public CodeableConcept getPositionstyp() {
        if (positionstyp == null) {
            positionstyp = new CodeableConcept();
            positionstyp.addCoding();
        }
        return positionstyp;
    }

    public InvoiceLineItem setPositionstyp(CodeableConcept positionstyp) {
        this.positionstyp = positionstyp;
        return this;
    }

    public InvoiceLineItem setPositionstyp(Positionstyp positionstypCode) {
        this.getPositionstyp().getCodingFirstRep()
            .setCode(positionstypCode.toCode())
            .setSystem(positionstypCode.getSystem())
            .setDisplay(positionstypCode.getDefinition());
        return this;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty() && ElementUtil.isEmpty(this.getPositionstyp());
    }

    public Money getVatValue() {
        if (vatValue == null) {
            vatValue = new Money().setCurrency(Configuration.DEAFULT_CURRENCY);
        }
        return vatValue;
    }

    public InvoiceLineItem setVatValue(Money vatValue) {
        this.vatValue = vatValue;
        return this;
    }

    public InvoiceLineItem setVatValue(double vatValue) {
        this.getVatValue().setValue(vatValue);
        return this;
    }

    public CodeableConcept getImportKennzeichen() {
        if (importKennzeichen == null) {
            importKennzeichen = new CodeableConcept();
            importKennzeichen.addCoding();
        }
        return importKennzeichen;
    }

    public InvoiceLineItem setImportKennzeichen(CodeableConcept importKennzeichen) {
        this.importKennzeichen = importKennzeichen;
        return this;
    }

    public InvoiceLineItem setImportKennzeichen(ImportKennzeichen importKennzeichenCode) {
        this.getImportKennzeichen().getCodingFirstRep()
            .setCode(importKennzeichenCode.toCode())
            .setSystem(importKennzeichenCode.getSystem());
        return this;
    }

    public InvoiceLineItem setZuAbschlagKennzeichen(InvoicePriceComponentType typ) {
        this.getPriceComponentFirstRep().setType(typ);
        return this;
    }

    public InvoiceLineItem setZuAbschlagCode(ZuAbschlagKey key) {
        this.getPriceComponentFirstRep().getCode().getCodingFirstRep()
            .setSystem("https://fhir.gkvsv.de/CodeSystem/GKVSV_CS_ERP_ZuAbschlagKey")
            .setCode(key.toCode());
        return this;
    }

    public InvoiceLineItem setZuAbschlagBetrag(Money mony) {
        this.getPriceComponentFirstRep().setAmount(mony);
        return this;
    }

    public InvoiceLineItem setZuAbschlagBetrag(double betrag) {
        this.getPriceComponentFirstRep().getAmount()
            .setValue(betrag)
            .setCurrency(Configuration.DEAFULT_CURRENCY);
        return this;
    }
}
