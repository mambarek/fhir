package de.gkvsv.fhir.ta7.model.enums;

import de.gkvsv.fhir.ta7.exceptions.TA7Exception;
import org.hl7.fhir.r4.model.EnumFactory;

/**
 * created by mmbarek on 12.11.2021.
 */
public class LeistungserbringerSitzEnum {

    public enum LeistungserbringerSitz implements IFhirEnum {
        INLAND(1),
        AUSLAND(2);

        LeistungserbringerSitz(int code) {
            this.code = code;
        }

        private final int code;

        public static LeistungserbringerSitz fromCode(String codeString) {
            if (codeString == null || "".equals(codeString)) {
                return null;
            }

            switch (codeString) {
                case "1":
                    return INLAND;
                case "2":
                    return AUSLAND;
            }

            throw new TA7Exception("Unknown LeistungserbringerSitz code '" + codeString + "'");
        }

        @Override
        public String toCode() {
            return String.valueOf(this.code);
        }

        @Override
        public String getSystem() {
            return "https://fhir.gkvsv.de/CodeSystem/GKVSV_CS_ERP_Leistungserbringer_Sitz";
        }

        @Override
        public String getDefinition() {
            switch (this) {
                case INLAND: return "Leistungserbringer mit Sitz im Inland";
                case AUSLAND: return "Leistungserbringer mit Sitz im Ausland";
                default: return "?";
            }
        }

        @Override
        public String getDisplay() {
            return getDefinition();
        }

        public int getCode() {
            return code;
        }
    }

    public static class LeistungserbringerSitzFactory implements EnumFactory<LeistungserbringerSitz> {

        @Override
        public LeistungserbringerSitz fromCode(String codeString) throws IllegalArgumentException {
            return LeistungserbringerSitz.fromCode(codeString);
        }

        @Override
        public String toCode(LeistungserbringerSitz code) {
            return code.toCode();
        }

        @Override
        public String toSystem(LeistungserbringerSitz code) {
            return code.getSystem();
        }
    }
}
