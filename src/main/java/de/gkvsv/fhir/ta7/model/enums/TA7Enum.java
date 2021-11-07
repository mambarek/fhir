package de.gkvsv.fhir.ta7.model.enums;

import org.hl7.fhir.exceptions.FHIRException;
import org.hl7.fhir.r4.model.EnumFactory;

/**
 * created by mmbarek on 06.11.2021.
 * Codes zur Technischen Anlage 7 (TA7)
 */
public class TA7Enum {

    public enum TA7 {
        SR,
        LSR,
        R;

        public static TA7 fromCode(String codeString) {
            if (codeString == null || "".equals(codeString))
                return null;

            switch (codeString) {
                case "SR": return SR;
                case "LSR": return LSR;
                case "R": return R;
            }

            throw new FHIRException("Unknown TA7 code '"+codeString+"'");
        }

        public String toCode() {
            switch (this) {
                case SR: return "SR";
                case LSR: return "LSR";
                case R: return "R";
                default: return "?";
            }
        }

        public String getSystem() {
            switch (this) {
                case SR: return "https://fhir.gkvsv.de/CodeSystem/GKVSV_CS_ERP_TA7";
                case LSR: return "https://fhir.gkvsv.de/CodeSystem/GKVSV_CS_ERP_TA7";
                case R: return "https://fhir.gkvsv.de/CodeSystem/GKVSV_CS_ERP_TA7";
                default: return "?";
            }
        }

        public String getDefinition() {
            switch (this) {
                case SR: return "Sammelrechnung";
                case LSR: return "Liste der Sammelrechnungen";
                case R: return "Rechnungen";
                default: return "?";
            }
        }

        public String getDisplay() {
            switch (this) {
                case SR: return "Sammelrechnung";
                case LSR: return "Liste der Sammelrechnungen";
                case R: return "Rechnungen";
                default: return "?";
            }
        }
    }

    public static class TA7EnumFactory implements EnumFactory<TA7> {

        @Override
        public TA7 fromCode(String codeString) throws IllegalArgumentException {
            return TA7.fromCode(codeString);
        }

        @Override
        public String toCode(TA7 code) {
            if (code == null) {
                return null;
            }
            return code.toCode();
        }

        @Override
        public String toSystem(TA7 code) {
            return code.getSystem();
        }
    }


}
