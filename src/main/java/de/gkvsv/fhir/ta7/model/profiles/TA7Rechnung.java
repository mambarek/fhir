package de.gkvsv.fhir.ta7.model.profiles;

import ca.uhn.fhir.model.api.TemporalPrecisionEnum;
import ca.uhn.fhir.model.api.annotation.Block;
import ca.uhn.fhir.model.api.annotation.Child;
import ca.uhn.fhir.model.api.annotation.Description;
import ca.uhn.fhir.model.api.annotation.Extension;
import ca.uhn.fhir.model.api.annotation.ResourceDef;
import ca.uhn.fhir.util.ElementUtil;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
import org.hl7.fhir.r4.model.BackboneElement;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.Invoice;
import org.hl7.fhir.r4.model.Reference;

/**
 * created by mmbarek on 06.11.2021.
 */
@ResourceDef(profile = "https://fhir.gkvsv.de/StructureDefinition/GKVSV_PR_TA7_Rechnung|1.0.4")
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

    public TA7Rechnung() {
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
        this.abrechnungszeitraum = dateTimeType;
        return this;
    }

    public TA7Rechnung setAbrechnungszeitraum(LocalDateTime localDateTime) {
        final DateTimeType dateTimeType = new DateTimeType();
        dateTimeType.setValue(Timestamp.valueOf(localDateTime));
        this.abrechnungszeitraum = dateTimeType;
        return this;
    }

    public TA7Rechnung setRefrenceRezeptBundle(String reference) {
        if(referenceRezeptBundle == null) {
            referenceRezeptBundle = new ReferenceRezeptBundle();
        }
        referenceRezeptBundle.getLineItem().setReference(reference);
        return this;
    }

    @Block
    public class ReferenceRezeptBundle  extends BackboneElement {

        @Child(name = "lineItem")
        @Extension(url = "lineItem")
        private Reference lineItem;

        public Reference getLineItem() {
            if(lineItem == null){
                lineItem = new Reference();
            }
            return lineItem;
        }

        public void setLineItem(Reference lineItem) {
            this.lineItem = lineItem;
        }

        @Override
        public BackboneElement copy() {
            ReferenceRezeptBundle copy = new ReferenceRezeptBundle();
            copy.setLineItem(lineItem);
            return null;
        }

        @Override
        public boolean isEmpty() {
            return super.isEmpty() && ElementUtil.isEmpty(lineItem);
        }
    }
}
