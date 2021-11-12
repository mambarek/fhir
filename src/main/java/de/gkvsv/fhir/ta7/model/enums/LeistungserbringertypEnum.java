package de.gkvsv.fhir.ta7.model.enums;

import de.gkvsv.fhir.ta7.exceptions.TA7Exception;
import de.gkvsv.fhir.ta7.model.enums.LeistungserbringerSitzEnum.LeistungserbringerSitz;
import org.hl7.fhir.r4.model.EnumFactory;

/**
 * created by mmbarek on 12.11.2021.
 */
public class LeistungserbringertypEnum {

    public enum Leistungserbringertyp implements IFhirEnum {
        OEFFENTLICHE_APOTHEKEN("A"),
        KRANKENHAUSAPOTHEKEN("K"),
        SONSTIGE_LEISTUNGSERBRINGER("S");

        private final String code;

        Leistungserbringertyp(String code) {
            this.code = code;
        }

        @Override
        public String toCode() {
            return String.valueOf(this.code);
        }

        @Override
        public String getSystem() {
            return "https://fhir.gkvsv.de/CodeSystem/GKVSV_CS_ERP_Leistungserbringertyp";
        }

        @Override
        public String getDefinition() {
            switch (this) {
                case OEFFENTLICHE_APOTHEKEN: return "Öffentliche Apotheken";
                case KRANKENHAUSAPOTHEKEN: return "Krankenhausapotheken";
                case SONSTIGE_LEISTUNGSERBRINGER: return "Sonstige Leistungserbringer";
                default: return "?";
            }
        }

        @Override
        public String getDisplay() {
            return getDefinition();
        }

        public static Leistungserbringertyp fromCode(String codeString) {
            if (codeString == null || "".equals(codeString)) {
                return null;
            }

            switch (codeString) {
                case "A":
                    return OEFFENTLICHE_APOTHEKEN;
                case "K":
                    return KRANKENHAUSAPOTHEKEN;
                case "S":
                    return SONSTIGE_LEISTUNGSERBRINGER;
            }

            throw new TA7Exception("Unknown Leistungserbringertyp code '" + codeString + "'");
        }
    }

    public static class LeistungserbringertypFactory implements EnumFactory<Leistungserbringertyp> {

        @Override
        public Leistungserbringertyp fromCode(String codeString) throws IllegalArgumentException {
            return Leistungserbringertyp.fromCode(codeString);
        }

        @Override
        public String toCode(Leistungserbringertyp code) {
            return code.toCode();
        }

        @Override
        public String toSystem(Leistungserbringertyp code) {
            return code.getSystem();
        }
    }

}
