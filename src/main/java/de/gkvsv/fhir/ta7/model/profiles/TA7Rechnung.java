package de.gkvsv.fhir.ta7.model.profiles;

import ca.uhn.fhir.model.api.TemporalPrecisionEnum;
import ca.uhn.fhir.model.api.annotation.Block;
import ca.uhn.fhir.model.api.annotation.Child;
import ca.uhn.fhir.model.api.annotation.Description;
import ca.uhn.fhir.model.api.annotation.Extension;
import ca.uhn.fhir.model.api.annotation.ResourceDef;
import ca.uhn.fhir.util.ElementUtil;
import de.gkvsv.fhir.ta7.config.Configuration;
import de.gkvsv.fhir.ta7.model.enums.RechnungsartEnum.Rechnungsart;
import de.gkvsv.fhir.ta7.model.enums.RechnungsartEnum.RechnungsartFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.hl7.fhir.r4.model.BackboneElement;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.Enumeration;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Invoice;
import org.hl7.fhir.r4.model.Reference;

/**
 * Rechnung an den Kostenträger
 * created by mmbarek on 06.11.2021.
 */
@ResourceDef(profile = "https://fhir.gkvsv.de/StructureDefinition/GKVSV_PR_TA7_Rechnung")
@Description("Rechnung an den Kostenträger")
public class TA7Rechnung extends Invoice {

    public static String LINK = "https://fhir.gkvsv.de/StructureDefinition/GKVSV_PR_TA7_Rechnung";
    /**
     * letzte Tag des Abrechnungszeitraums
     */
    @Child(name = "abrechnungszeitraum", type = {DateTimeType.class}, min=1, summary = true)
    @Extension(url="https://fhir.gkvsv.de/StructureDefinition/GKVSV_EX_ERP_TA7_Abrechnungszeitraum")
    private DateTimeType abrechnungszeitraum;

    @Child(name = "referenceRezeptBundle")
    @Extension(url="https://fhir.gkvsv.de/StructureDefinition/GKVSV_EX_ERP_RezeptBundleReferenz")
    private ReferenceRezeptBundle referenceRezeptBundle;

    /**
     * Sammelrechnungsnummer des Zahlungsempfängers. Es muss sich um eine eindeutige
     * nachrichtentyp-übergreifende Rechnungsnummer handeln.
     * Die Sammelrechnungsnummer darf maximal 20 Zeichen lang sein.
     */
    private Identifier sammelrechnungsnummer;

    private Enumeration<Rechnungsart> rechnungsart;
    /**
     * status is fixed to issued
     */
    public TA7Rechnung() {
        setStatus(InvoiceStatus.ISSUED);
        setId(UUID.randomUUID().toString());
        referenceRezeptBundle = new ReferenceRezeptBundle();
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty() &&
            getAbrechnungszeitraum().isEmpty() &&
            ElementUtil.isEmpty(referenceRezeptBundle);
    }

    public DateTimeType getAbrechnungszeitraum() {
        return abrechnungszeitraum;
    }

    public TA7Rechnung setAbrechnungszeitraum(DateTimeType abrechnungszeitraum) {
        this.abrechnungszeitraum = abrechnungszeitraum;
        return this;
    }

    public TA7Rechnung setAbrechnungszeitraum(Date date) {
        final DateTimeType dateTimeType = new DateTimeType();
        dateTimeType.setValue(date);
        dateTimeType.setPrecision(TemporalPrecisionEnum.DAY);
        this.abrechnungszeitraum = dateTimeType;
        return this;
    }

    public TA7Rechnung addRefrenceRezeptBundle(String rezeptBundleReference) {
        referenceRezeptBundle.addRezeptBundleReference(rezeptBundleReference);
        return this;
    }

    public Identifier getSammelrechnungsnummer() {
        if(sammelrechnungsnummer == null) {
            sammelrechnungsnummer = new Identifier();
            sammelrechnungsnummer.setSystem("https://fhir.gkvsv.de/NamingSystem/GKVSV_NS_Sammelrechnungsnummer");
            this.addIdentifier(sammelrechnungsnummer);
        }
        return sammelrechnungsnummer;
    }

    public TA7Rechnung setSammelrechnungsnummer(String rechnungsNummer) {
        this.getSammelrechnungsnummer().setValue(rechnungsNummer);
        return this;
    }

    public Enumeration<Rechnungsart> getRechnungsart() {
        return rechnungsart;
    }

    public TA7Rechnung setRechnungsart(Rechnungsart value) {
        if (value == null) {
            rechnungsart = null;
            getType().getCodingFirstRep().setCode(null);
        } else {
            if (rechnungsart == null) {
                rechnungsart = new Enumeration<>(new RechnungsartFactory());
            }
            getType().getCodingFirstRep().setSystem("https://fhir.gkvsv.de/CodeSystem/GKVSV_CS_ERP_Rechnungsart");
            getType().getCodingFirstRep().setCode(value.toCode());
        }
        return this;
    }

    /**
     * IK des Kostenträgers
     * Die IK des Kostenträgers muss 9 Zeichen lang und numerisch sein.
     * @param ik
     * @return
     */
    public TA7Rechnung setKostentraegerIK(String ik) {
        this.getRecipient().getIdentifier().setSystem("http://fhir.de/NamingSystem/arge-ik/iknr");
        this.getRecipient().getIdentifier().setValue(ik);
        return this;
    }

    /**
     * Rechnungsdatum
     * @param date
     * @return
     */
    public TA7Rechnung setRechnungsdatum(Date date) {
        final DateTimeType dateTimeType = new DateTimeType();
        dateTimeType.setValue(date);
        dateTimeType.setPrecision(TemporalPrecisionEnum.DAY);
        setDateElement(dateTimeType);
        return this;
    }

    @Block
    public class ReferenceRezeptBundle  extends BackboneElement {

        @Child(name = "lineItem")
        @Extension(url = "lineItem")
        private List<Reference> lineItems;

        public List<Reference> getLineItems() {
            if(lineItems == null){
                lineItems = new ArrayList<>();
            }
            return lineItems;
        }

        public void setLineItems(List<Reference> lineItems) {
            this.lineItems = lineItems;
        }

        @Override
        public BackboneElement copy() {
            ReferenceRezeptBundle copy = new ReferenceRezeptBundle();
            copy.setLineItems(lineItems);
            return copy;
        }

        @Override
        public boolean isEmpty() {
            return super.isEmpty() && ElementUtil.isEmpty(lineItems);
        }

        public void addRezeptBundleReference(String reference) {
            Reference ref = new Reference();
            ref.setReference(Configuration.URN_URL_PREFIX + reference);
            getLineItems().add(ref);
        }
    }
}
