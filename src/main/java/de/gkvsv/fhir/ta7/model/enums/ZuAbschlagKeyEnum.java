package de.gkvsv.fhir.ta7.model.enums;

import de.gkvsv.fhir.ta7.exceptions.TA7Exception;
import org.hl7.fhir.r4.model.EnumFactory;

/**
 * created by mmbarek on 12.11.2021.
 */
public class ZuAbschlagKeyEnum {

    public enum ZuAbschlagKey implements IFhirEnum{

        /**
         * Apothekenabschlag nach § 130 SGB V
         */
        APOTHEKEN_ABSCHLAG("R001"),

        /**
         * Sonstiger Abschlag/Rabatt
         */
        SONSTIGER_ABSCHLAG_RABATT("R003"),

        /**
         * Gesetzlicher Herstellerabschlag nach § 130a Absatz 1 und Absatz 1a SGB V
         */
        GESETZLICHER_HERSTELLER_ABSCHLAG_P130a_A1_UND_A1a("R004"),

        /**
         * Gesetzlicher Herstellerabschlag nach § 130a Absatz 3a SGB V
         */
        GESETZLICHER_HERSTELLER_ABSCHLAG_P130a_A3a("R005"),

        /**
         * Gesetzlicher Herstellerabschlag nach § 130a Absatz 3b SGB V
         */
        GESETZLICHER_HERSTELLER_ABSCHLAG_P130a_A3b("R006"),

        /**
         * Rabattangabe der vom Kostenträger aufgrund von Verträgen nach § 130a Abs. 8 i.V.m. § 31
         * Abs. 2 SGB V zusätzlich übernommenen Mehrkosten
         */
        MEHRKOSTEN_RABATT_ANGABE("R007"),

        /**
         * Rabattangabe aufgrund von Verträgen nach § 130a Abs. 8 SGB V ohne Berücksichtigung der
         * Rabattangaben nach Schlüssel R007
         */
        RABATT_ANGABE_NACH_P130a_A8("R008"),

        /**
         * Gesetzlicher Herstellerabschlag nach § 130a Abs. 2 SGB V
         */
        GESETZLICHER_HERSTELLER_ABSCHLAG_P130a_A2("R009");

        private final String code;

        ZuAbschlagKey(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public static ZuAbschlagKey fromCode(String codeString) {
            if (codeString == null || "".equals(codeString)) {
                return null;
            }

            switch (codeString) {
                case "R001":
                    return APOTHEKEN_ABSCHLAG;
                case "R003":
                    return SONSTIGER_ABSCHLAG_RABATT;
                case "R004":
                    return GESETZLICHER_HERSTELLER_ABSCHLAG_P130a_A1_UND_A1a;
                case "R005":
                    return GESETZLICHER_HERSTELLER_ABSCHLAG_P130a_A3a;
                case "R006":
                    return GESETZLICHER_HERSTELLER_ABSCHLAG_P130a_A3b;
                case "R007":
                    return MEHRKOSTEN_RABATT_ANGABE;
                case "R008":
                    return RABATT_ANGABE_NACH_P130a_A8;
                case "R009":
                    return GESETZLICHER_HERSTELLER_ABSCHLAG_P130a_A2;
            }

            throw new TA7Exception("Unknown ZuAbschlagKey code '" + codeString + "'");
        }

        @Override
        public String toCode() {
            return getCode();
        }

        @Override
        public String getSystem() {
            return "https://fhir.gkvsv.de/CodeSystem/GKVSV_CS_ERP_ZuAbschlagKey";
        }

        @Override
        public String getDefinition() {
            switch (this) {
                case APOTHEKEN_ABSCHLAG:
                    return "Apothekenabschlag nach § 130 SGB V";
                case SONSTIGER_ABSCHLAG_RABATT:
                    return "Sonstiger Abschlag/Rabatt";
                case GESETZLICHER_HERSTELLER_ABSCHLAG_P130a_A1_UND_A1a:
                    return "Gesetzlicher Herstellerabschlag nach § 130a Absatz 1 und Absatz 1a SGB V";
                case GESETZLICHER_HERSTELLER_ABSCHLAG_P130a_A3a:
                    return "Gesetzlicher Herstellerabschlag nach § 130a Absatz 3a SGB V";
                case GESETZLICHER_HERSTELLER_ABSCHLAG_P130a_A3b:
                    return "Gesetzlicher Herstellerabschlag nach § 130a Absatz 3b SGB V";
                case MEHRKOSTEN_RABATT_ANGABE:
                    return "Rabattangabe der vom Kostenträger aufgrund von Verträgen nach § 130a Abs. 8 i.V.m. § 31 Abs. 2 SGB V zusätzlich übernommenen Mehrkosten";
                case RABATT_ANGABE_NACH_P130a_A8:
                    return "Rabattangabe aufgrund von Verträgen nach § 130a Abs. 8 SGB V ohne Berücksichtigung der Rabattangaben nach Schlüssel R007";
                case GESETZLICHER_HERSTELLER_ABSCHLAG_P130a_A2:
                    return "Gesetzlicher Herstellerabschlag nach § 130a Abs. 2 SGB V";
                default: return "?";
            }
        }

        @Override
        public String getDisplay() {
            return getDefinition();
        }
    }

    public static class ZuAbschlagKeyFactory implements EnumFactory<ZuAbschlagKey> {

        @Override
        public ZuAbschlagKey fromCode(String codeString) throws IllegalArgumentException {
            return ZuAbschlagKey.fromCode(codeString);
        }

        @Override
        public String toCode(ZuAbschlagKey code) {
            return code.toCode();
        }

        @Override
        public String toSystem(ZuAbschlagKey code) {
            return code.getSystem();
        }
    }
}
